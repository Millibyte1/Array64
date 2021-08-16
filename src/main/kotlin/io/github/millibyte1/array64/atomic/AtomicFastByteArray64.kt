package io.github.millibyte1.array64.atomic

import io.github.millibyte1.array64.FastByteArray64
import io.github.millibyte1.array64.MAX_SIZE
import io.github.millibyte1.array64.lastIndex
import io.github.millibyte1.array64.util.unsafe
import io.github.millibyte1.array64.util.byteOffset
import io.github.millibyte1.array64.util.compareAndSwapByte
import io.github.millibyte1.array64.util.getAndSetByte
import it.unimi.dsi.fastutil.BigArrays

private val base: Int = unsafe.arrayBaseOffset(ByteArray::class.java)
private val scale: Int = unsafe.arrayIndexScale(ByteArray::class.java)
private val shift: Int = 31 - Integer.numberOfLeadingZeros(scale)

/**
 * An extension of the FastByteArray64 class supporting atomic operations such as compareAndSet.
 * Loosely based off the Jetbrains JDK implementation of AtomicReferenceArray.
 */
class AtomicFastByteArray64 : FastByteArray64, AtomicArray64<Byte> {

    /**
     * Creates a new array of the specified [size], with all elements initialized to zero.
     * @throws IllegalArgumentException if [size] is not between 1 and [MAX_SIZE]
     */
    constructor(size: Long) : super(size)
    /**
     * Creates a new array of the specified [size], with all elements initialized according to the given [init] function.
     * @throws IllegalArgumentException if [size] is not between 1 and [MAX_SIZE]
     */
    constructor(size: Long, init: (Long) -> Byte) : super(size, init)
    /**
     * Creates a copy of the given array.
     * @param array the array in question
     */
    constructor(array: FastByteArray64) : this(array.array)
    /**
     * Creates a new array from the given FastUtil BigArray, either by copying its contents or simply wrapping it.
     * @param array the array in question
     * @param copy whether to copy (true) the array or directly use it as the internal array (false)
     */
    constructor(array: Array<ByteArray>, copy: Boolean = true) : super(array, copy)

    override fun copy(): AtomicFastByteArray64 = AtomicFastByteArray64(this)


    override operator fun get(index: Long): Byte {
        if(index >= this.size || index < 0) throw NoSuchElementException()
        val inner = this.array[BigArrays.segment(index)]
        val innerIndex = BigArrays.displacement(index)
        val offset = byteOffset(innerIndex, shift, base)
        return unsafe.getByteVolatile(inner, offset)
    }

    override operator fun set(index: Long, value: Byte) {
        if(index >= this.size || index < 0) throw NoSuchElementException()
        val inner = this.array[BigArrays.segment(index)]
        val innerIndex = BigArrays.displacement(index)
        val offset = byteOffset(innerIndex, shift, base)
        unsafe.putByteVolatile(inner, offset, value)
    }


    override fun getAndSet(index: Long, new: Byte): Byte {
        if(index >= this.size || index < 0) throw NoSuchElementException()
        val inner = this.array[BigArrays.segment(index)]
        val innerIndex = BigArrays.displacement(index)
        val offset = byteOffset(innerIndex, shift, base)
        return getAndSetByte(inner, offset, new)
    }


    override fun getAndSet(index: Long, transform: (Byte) -> Byte): Byte {
        if(index >= this.size || index < 0) throw NoSuchElementException()
        val inner = this.array[BigArrays.segment(index)]
        val innerIndex = BigArrays.displacement(index)
        val offset = byteOffset(innerIndex, shift, base)
        var old: Byte; var new: Byte
        do {
            old = unsafe.getByteVolatile(inner, offset)
            new = transform(old)
        }
        while(!compareAndSwapByte(inner, offset, old, new))
        return old
    }


    override fun setAndGet(index: Long, transform: (Byte) -> Byte): Byte {
        if(index >= this.size || index < 0) throw NoSuchElementException()
        val inner = this.array[BigArrays.segment(index)]
        val innerIndex = BigArrays.displacement(index)
        val offset = byteOffset(innerIndex, shift, base)
        var old: Byte; var new: Byte
        do {
            old = unsafe.getByteVolatile(inner, offset)
            new = transform(old)
        }
        while(!compareAndSwapByte(inner, offset, old, new))
        return new
    }

    override fun compareAndSet(index: Long, new: Byte, expected: Byte): Boolean {
        if(index >= this.size || index < 0) throw NoSuchElementException()
        val inner = this.array[BigArrays.segment(index)]
        val innerIndex = BigArrays.displacement(index)
        val offset = byteOffset(innerIndex, shift, base)
        return compareAndSwapByte(inner, offset, expected, new)
    }


    override fun compareAndSet(index: Long, new: Byte, predicate: (old: Byte, new: Byte) -> Boolean): Boolean {
        if(index >= this.size || index < 0) throw NoSuchElementException()
        val inner = this.array[BigArrays.segment(index)]
        val innerIndex = BigArrays.displacement(index)
        val offset = byteOffset(innerIndex, shift, base)
        val old = unsafe.getByteVolatile(inner, offset)
        return if(predicate(old, new)) compareAndSwapByte(inner, offset, old, new) else false
    }

    override fun lazySet(index: Long, value: Byte) {
        if(index >= this.size || index < 0) throw NoSuchElementException()
        val inner = this.array[BigArrays.segment(index)]
        val innerIndex = BigArrays.displacement(index)
        val offset = byteOffset(innerIndex, shift, base)
        unsafe.putByteVolatile(inner, offset, value)
    }

    override fun iterator(index: Long): AtomicByteArray64Iterator {
        if(index < 0 || index >= this.size) throw IllegalArgumentException("Invalid index provided.")
        return Iterator(this, index)
    }

    override operator fun iterator(): AtomicByteArray64Iterator = Iterator(this, 0)

    /**
     * A bidirectional iterator supporting atomic operations on the element at its position.
     * @constructor Constructs an iterator to the given [index] in the given [array].
     * @param array the array to iterate over
     * @param index the index to start at
     */
    private class Iterator (
        private val array: AtomicFastByteArray64,
        index: Long,
    ) : AtomicByteArray64Iterator() {

        override var index: Long = index
            private set
        private var outerIndex = BigArrays.segment(index)
        private var innerIndex = BigArrays.displacement(index)
        private var inner = array.array[outerIndex]
        private var offset: Long = byteOffset(innerIndex, shift, base)

        override fun hasNext(): Boolean = index < array.size
        override fun hasPrevious(): Boolean = index >= 0
        override fun nextByte(): Byte {
            val retval = unsafe.getByteVolatile(inner, offset)
            increaseIndices()
            return retval
        }
        override fun previousByte(): Byte {
            val retval = unsafe.getByteVolatile(inner, offset)
            decreaseIndices()
            return retval
        }
        // increases the current index and the cached inner array
        private fun increaseIndices() {
            if(innerIndex == BigArrays.SEGMENT_SIZE - 1) {
                innerIndex = 0
                outerIndex++
                if(index != array.lastIndex) inner = array.array[outerIndex]
            }
            else innerIndex++
            index++
            offset = byteOffset(innerIndex, shift, base)
        }
        // decreases the current index and the cached inner array
        private fun decreaseIndices() {
            if(innerIndex == 0) {
                innerIndex = BigArrays.SEGMENT_SIZE - 1
                outerIndex--
                if(index != 0L) inner = array.array[outerIndex]
            }
            else innerIndex--
            index--
            offset = byteOffset(innerIndex, shift, base)
        }

        override fun setByte(element: Byte) = unsafe.putByteVolatile(inner, offset, element)
        override fun getAndSetByte(new: Byte) = getAndSetByte(inner, offset, new)
        override fun getAndSetByte(transform: (Byte) -> Byte): Byte {
            var old: Byte; var new: Byte
            do {
                old = unsafe.getByteVolatile(inner, offset)
                new = transform(old)
            }
            while(!compareAndSwapByte(inner, offset, old, new))
            return old
        }
        override fun setAndGetByte(transform: (Byte) -> Byte): Byte {
            var old: Byte; var new: Byte
            do {
                old = unsafe.getByteVolatile(inner, offset)
                new = transform(old)
            }
            while(!compareAndSwapByte(inner, offset, old, new))
            return new
        }
        override fun compareAndSetByte(new: Byte, expected: Byte) = compareAndSwapByte(inner, offset, expected, new)
        override fun compareAndSetByte(new: Byte, predicate: (old: Byte, new: Byte) -> Boolean): Boolean {
            val old = unsafe.getByteVolatile(inner, offset)
            return if(predicate(old, new)) compareAndSwapByte(inner, offset, old, new) else false
        }
        override fun lazySetByte(value: Byte) = unsafe.putByteVolatile(inner, offset, value)
    }
}