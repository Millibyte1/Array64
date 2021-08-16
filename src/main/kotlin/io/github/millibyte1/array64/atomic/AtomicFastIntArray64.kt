package io.github.millibyte1.array64.atomic

import io.github.millibyte1.array64.FastIntArray64
import io.github.millibyte1.array64.MAX_SIZE
import io.github.millibyte1.array64.lastIndex
import io.github.millibyte1.array64.util.unsafe
import io.github.millibyte1.array64.util.byteOffset
import it.unimi.dsi.fastutil.BigArrays

private val base: Int = unsafe.arrayBaseOffset(IntArray::class.java)
private val scale: Int = unsafe.arrayIndexScale(IntArray::class.java)
private val shift: Int = 31 - Integer.numberOfLeadingZeros(scale)

/**
 * An extension of the FastIntArray64 class supporting atomic operations such as compareAndSet.
 * Loosely based off the Jetbrains JDK implementation of AtomicReferenceArray.
 */
class AtomicFastIntArray64 : FastIntArray64, AtomicArray64<Int> {

    /**
     * Creates a new array of the specified [size], with all elements initialized to zero.
     * @throws IllegalArgumentException if [size] is not between 1 and [MAX_SIZE]
     */
    constructor(size: Long) : super(size)
    /**
     * Creates a new array of the specified [size], with all elements initialized according to the given [init] function.
     * @throws IllegalArgumentException if [size] is not between 1 and [MAX_SIZE]
     */
    constructor(size: Long, init: (Long) -> Int) : super(size, init)
    /**
     * Creates a copy of the given array.
     * @param array the array in question
     */
    constructor(array: FastIntArray64) : this(array.array)
    /**
     * Creates a new array from the given FastUtil BigArray, either by copying its contents or simply wrapping it.
     * @param array the array in question
     * @param copy whether to copy (true) the array or directly use it as the internal array (false)
     */
    constructor(array: Array<IntArray>, copy: Boolean = true) : super(array, copy)

    override fun copy(): AtomicFastIntArray64 = AtomicFastIntArray64(this)

    
    override operator fun get(index: Long): Int {
        if(index >= this.size || index < 0) throw NoSuchElementException()
        val inner = this.array[BigArrays.segment(index)]
        val innerIndex = BigArrays.displacement(index)
        val offset = byteOffset(innerIndex, shift, base)
        return unsafe.getIntVolatile(inner, offset)
    }

    override operator fun set(index: Long, value: Int) {
        if(index >= this.size || index < 0) throw NoSuchElementException()
        val inner = this.array[BigArrays.segment(index)]
        val innerIndex = BigArrays.displacement(index)
        val offset = byteOffset(innerIndex, shift, base)
        unsafe.putIntVolatile(inner, offset, value)
    }

    
    override fun getAndSet(index: Long, new: Int): Int {
        if(index >= this.size || index < 0) throw NoSuchElementException()
        val inner = this.array[BigArrays.segment(index)]
        val innerIndex = BigArrays.displacement(index)
        val offset = byteOffset(innerIndex, shift, base)
        return unsafe.getAndSetInt(inner, offset, new) 
    }

    
    override fun getAndSet(index: Long, transform: (Int) -> Int): Int {
        if(index >= this.size || index < 0) throw NoSuchElementException()
        val inner = this.array[BigArrays.segment(index)]
        val innerIndex = BigArrays.displacement(index)
        val offset = byteOffset(innerIndex, shift, base)
        var old: Int; var new: Int
        do {
            old = unsafe.getIntVolatile(inner, offset) 
            new = transform(old)
        }
        while(!unsafe.compareAndSwapInt(inner, offset, old, new))
        return old
    }

    
    override fun setAndGet(index: Long, transform: (Int) -> Int): Int {
        if(index >= this.size || index < 0) throw NoSuchElementException()
        val inner = this.array[BigArrays.segment(index)]
        val innerIndex = BigArrays.displacement(index)
        val offset = byteOffset(innerIndex, shift, base)
        var old: Int; var new: Int
        do {
            old = unsafe.getIntVolatile(inner, offset) 
            new = transform(old)
        }
        while(!unsafe.compareAndSwapInt(inner, offset, old, new))
        return new
    }

    override fun compareAndSet(index: Long, new: Int, expected: Int): Boolean {
        if(index >= this.size || index < 0) throw NoSuchElementException()
        val inner = this.array[BigArrays.segment(index)]
        val innerIndex = BigArrays.displacement(index)
        val offset = byteOffset(innerIndex, shift, base)
        return unsafe.compareAndSwapInt(inner, offset, expected, new)
    }

    
    override fun compareAndSet(index: Long, new: Int, predicate: (old: Int, new: Int) -> Boolean): Boolean {
        if(index >= this.size || index < 0) throw NoSuchElementException()
        val inner = this.array[BigArrays.segment(index)]
        val innerIndex = BigArrays.displacement(index)
        val offset = byteOffset(innerIndex, shift, base)
        val old = unsafe.getIntVolatile(inner, offset) 
        return if(predicate(old, new)) unsafe.compareAndSwapInt(inner, offset, old, new) else false
    }

    override fun lazySet(index: Long, value: Int) {
        if(index >= this.size || index < 0) throw NoSuchElementException()
        val inner = this.array[BigArrays.segment(index)]
        val innerIndex = BigArrays.displacement(index)
        val offset = byteOffset(innerIndex, shift, base)
        unsafe.putOrderedInt(inner, offset, value)
    }

    override fun iterator(index: Long): AtomicIntArray64Iterator {
        if(index < 0 || index >= this.size) throw IllegalArgumentException("Invalid index provided.")
        return Iterator(this, index)
    }
    
    override operator fun iterator(): AtomicIntArray64Iterator = Iterator(this, 0)

    /**
     * A bidirectional iterator supporting atomic operations on the element at its position.
     * @constructor Constructs an iterator to the given [index] in the given [array].
     * @param array the array to iterate over
     * @param index the index to start at
     */
    private class Iterator (
        private val array: AtomicFastIntArray64,
        index: Long,
    ) : AtomicIntArray64Iterator() {

        override var index: Long = index
            private set
        private var outerIndex = BigArrays.segment(index)
        private var innerIndex = BigArrays.displacement(index)
        private var inner = array.array[outerIndex]
        private var offset: Long = byteOffset(innerIndex, shift, base)

        override fun hasNext(): Boolean = index < array.size
        override fun hasPrevious(): Boolean = index >= 0
        override fun nextInt(): Int {
            val retval = unsafe.getIntVolatile(inner, offset) 
            increaseIndices()
            return retval
        }
        override fun previousInt(): Int {
            val retval = unsafe.getIntVolatile(inner, offset) 
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

        override fun setInt(element: Int) = unsafe.putIntVolatile(inner, offset, element)
        override fun getAndSetInt(new: Int) = unsafe.getAndSetInt(inner, offset, new)
        override fun getAndSetInt(transform: (Int) -> Int): Int {
            var old: Int; var new: Int
            do {
                old = unsafe.getIntVolatile(inner, offset)
                new = transform(old)
            }
            while(!unsafe.compareAndSwapInt(inner, offset, old, new))
            return old
        }
        override fun setAndGetInt(transform: (Int) -> Int): Int {
            var old: Int; var new: Int
            do {
                old = unsafe.getIntVolatile(inner, offset) 
                new = transform(old)
            }
            while(!unsafe.compareAndSwapInt(inner, offset, old, new))
            return new
        }
        override fun compareAndSetInt(new: Int, expected: Int) = unsafe.compareAndSwapInt(inner, offset, expected, new)
        override fun compareAndSetInt(new: Int, predicate: (old: Int, new: Int) -> Boolean): Boolean {
            val old = unsafe.getIntVolatile(inner, offset) 
            return if(predicate(old, new)) unsafe.compareAndSwapInt(inner, offset, old, new) else false
        }
        override fun lazySetInt(value: Int) = unsafe.putOrderedInt(inner, offset, value)
    }
}