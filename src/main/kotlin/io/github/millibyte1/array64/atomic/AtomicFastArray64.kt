package io.github.millibyte1.array64.atomic

import io.github.millibyte1.array64.*
import io.github.millibyte1.array64.util.byteOffset
import it.unimi.dsi.fastutil.BigArrays
import io.github.millibyte1.array64.util.unsafe
import io.github.millibyte1.array64.util.getArrayBaseOffset
import io.github.millibyte1.array64.util.getArrayIndexScale

/**
 * An extension of the FastArray64 class supporting atomic operations such as compareAndSet.
 * Loosely based off the Jetbrains JDK implementation of AtomicReferenceArray.
 * @param E The type of element stored in this array.
 */
class AtomicFastArray64<E>
@PublishedApi internal constructor(
    private val base: Int,
    private val shift: Int,
    array: Array<Array<E>>,
    copy: Boolean = true,
) : FastArray64<E>(array, copy), AtomicArray64<E> {

    @Suppress("UNCHECKED_CAST")
    override operator fun get(index: Long): E {
        if(index >= this.size || index < 0) throw NoSuchElementException()
        val inner = this.array[BigArrays.segment(index)]
        val innerIndex = BigArrays.displacement(index)
        val offset = byteOffset(innerIndex, shift, base)
        return unsafe.getObjectVolatile(inner, offset) as E
    }

    override operator fun set(index: Long, value: E) {
        if(index >= this.size || index < 0) throw NoSuchElementException()
        val inner = this.array[BigArrays.segment(index)]
        val innerIndex = BigArrays.displacement(index)
        val offset = byteOffset(innerIndex, shift, base)
        unsafe.putObjectVolatile(inner, offset, value)
    }

    @Suppress("UNCHECKED_CAST")
    override fun getAndSet(index: Long, new: E): E {
        if(index >= this.size || index < 0) throw NoSuchElementException()
        val inner = this.array[BigArrays.segment(index)]
        val innerIndex = BigArrays.displacement(index)
        val offset = byteOffset(innerIndex, shift, base)
        return unsafe.getAndSetObject(inner, offset, new) as E
    }

    @Suppress("UNCHECKED_CAST")
    override fun getAndSet(index: Long, transform: (E) -> E): E {
        if(index >= this.size || index < 0) throw NoSuchElementException()
        val inner = this.array[BigArrays.segment(index)]
        val innerIndex = BigArrays.displacement(index)
        val offset = byteOffset(innerIndex, shift, base)
        var old: E; var new: E
        do {
            old = unsafe.getObjectVolatile(inner, offset) as E
            new = transform(old)
        }
        while(!unsafe.compareAndSwapObject(inner, offset, old, new))
        return old
    }

    @Suppress("UNCHECKED_CAST")
    override fun setAndGet(index: Long, transform: (E) -> E): E {
        if(index >= this.size || index < 0) throw NoSuchElementException()
        val inner = this.array[BigArrays.segment(index)]
        val innerIndex = BigArrays.displacement(index)
        val offset = byteOffset(innerIndex, shift, base)
        var old: E; var new: E
        do {
            old = unsafe.getObjectVolatile(inner, offset) as E
            new = transform(old)
        }
        while(!unsafe.compareAndSwapObject(inner, offset, old, new))
        return new
    }

    override fun compareAndSet(index: Long, new: E, expected: E): Boolean {
        if(index >= this.size || index < 0) throw NoSuchElementException()
        val inner = this.array[BigArrays.segment(index)]
        val innerIndex = BigArrays.displacement(index)
        val offset = byteOffset(innerIndex, shift, base)
        return unsafe.compareAndSwapObject(inner, offset, expected, new)
    }

    @Suppress("UNCHECKED_CAST")
    override fun compareAndSet(index: Long, new: E, predicate: (old: E, new: E) -> Boolean): Boolean {
        if(index >= this.size || index < 0) throw NoSuchElementException()
        val inner = this.array[BigArrays.segment(index)]
        val innerIndex = BigArrays.displacement(index)
        val offset = byteOffset(innerIndex, shift, base)
        val old = unsafe.getObjectVolatile(inner, offset) as E
        return if(predicate(old, new)) unsafe.compareAndSwapObject(inner, offset, old, new) else false
    }

    override fun lazySet(index: Long, value: E) {
        if(index >= this.size || index < 0) throw NoSuchElementException()
        val inner = this.array[BigArrays.segment(index)]
        val innerIndex = BigArrays.displacement(index)
        val offset = byteOffset(innerIndex, shift, base)
        unsafe.putOrderedObject(inner, offset, value)
    }

    override fun iterator(index: Long): AtomicArray64Iterator<E> {
        if(index < 0 || index >= this.size) throw IllegalArgumentException("Invalid index provided.")
        return Iterator(this, index, base, shift)
    }
    override operator fun iterator(): AtomicArray64Iterator<E> = Iterator(this, 0, base, shift)

    companion object {
        /**
         * Creates a new array of the specified [size], with all elements initialized according to the given [init] function.
         * @param size the desired size of the array
         * @param init the initializer function to apply
         * @throws IllegalArgumentException if [size] is not between 1 and [MAX_SIZE]
         * @return the created array
         */
        @JvmStatic @JvmName("create")
        inline operator fun <reified E> invoke(size: Long, crossinline init: (Long) -> E): AtomicFastArray64<E> {
            val instance = init(0)
            val scale = getArrayIndexScale(instance)
            if(scale and (scale - 1) != 0) throw Error("data type scale not a power of two")
            val shift = 31 - Integer.numberOfLeadingZeros(scale)
            return AtomicFastArray64(getArrayBaseOffset(instance), shift, makeTyped2DArray(size, init))
        }
        /**
         * Creates a copy of the given array.
         * @param array the array in question
         * @return the created array
         */
        @JvmStatic @JvmName("create")
        inline operator fun <reified E> invoke(array: FastArray64<E>) = AtomicFastArray64(array.array)
        /**
         * Creates a new array from the given FastUtil BigArray, either by copying its contents or simply wrapping it.
         * @param array the array in question
         * @param copy whether to copy (true) the array or directly use it as the internal array (false)
         * @return the created array
         */
        @JvmStatic @JvmName("create")
        inline operator fun <reified E> invoke(array: Array<Array<E>>, copy: Boolean = true): AtomicFastArray64<E> {
            val instance = array[0][0]
            val scale = getArrayIndexScale(instance)
            if(scale and (scale - 1) != 0) throw Error("data type scale not a power of two")
            val shift = 31 - Integer.numberOfLeadingZeros(scale)
            return AtomicFastArray64(getArrayBaseOffset(instance), shift, array, copy)
        }
        /**
         * Creates a new array from the given standard library array, either by copying its contents or simply wrapping it.
         * @param array the array in question
         * @param copy whether to copy (true) the array or directly use it as the internal array (false)
         * @return the created array
         */
        @JvmStatic @JvmName("create")
        inline operator fun <reified E> invoke(array: Array<E>, copy: Boolean = true): AtomicFastArray64<E> {
            val instance = array[0]
            val scale = getArrayIndexScale(instance)
            if(scale and (scale - 1) != 0) throw Error("data type scale not a power of two")
            val shift = 31 - Integer.numberOfLeadingZeros(scale)
            val fastArray = if(copy) BigArrays.wrap(array) else Array(1) { array }
            return AtomicFastArray64(getArrayBaseOffset(instance), shift, fastArray, copy)
        }
    }

    /**
     * A bidirectional iterator supporting atomic operations on the element at its position.
     * @param E the type of element stored in the array
     * @constructor Constructs an iterator to the given [index] in the given [array].
     * @param array the array to iterate over
     * @param index the index to start at
     * @param base the base offset of this array type
     * @param shift the amount to shift the index to calculate the offset of an element of this array type
     */
    private class Iterator<E>(
        private val array: AtomicFastArray64<E>,
        index: Long,
        private val base: Int,
        private val shift: Int,
    ) : AtomicArray64Iterator<E> {

        override var index: Long = index
            private set
        private var outerIndex = BigArrays.segment(index)
        private var innerIndex = BigArrays.displacement(index)
        private var inner = array.array[outerIndex]
        private var offset: Long = byteOffset(innerIndex, shift, base)

        override fun hasNext(): Boolean = index < array.size
        override fun hasPrevious(): Boolean = index >= 0
        @Suppress("UNCHECKED_CAST")
        override fun next(): E {
            val retval = unsafe.getObjectVolatile(inner, offset) as E
            increaseIndices()
            return retval
        }
        @Suppress("UNCHECKED_CAST")
        override fun previous(): E {
            val retval = unsafe.getObjectVolatile(inner, offset) as E
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
        override fun set(element: E) = unsafe.putObjectVolatile(inner, offset, element)

        @Suppress("UNCHECKED_CAST")
        override fun getAndSet(new: E) = unsafe.getAndSetObject(inner, offset, new) as E
        @Suppress("UNCHECKED_CAST")
        override fun getAndSet(transform: (E) -> E): E {
            var old: E; var new: E
            do {
                old = unsafe.getObjectVolatile(inner, offset) as E
                new = transform(old)
            }
            while(!unsafe.compareAndSwapObject(inner, offset, old, new))
            return old
        }
        @Suppress("UNCHECKED_CAST")
        override fun setAndGet(transform: (E) -> E): E {
            var old: E; var new: E
            do {
                old = unsafe.getObjectVolatile(inner, offset) as E
                new = transform(old)
            }
            while(!unsafe.compareAndSwapObject(inner, offset, old, new))
            return new
        }
        @Suppress("UNCHECKED_CAST")
        override fun compareAndSet(new: E, expected: E) = unsafe.compareAndSwapObject(inner, offset, expected, new)
        @Suppress("UNCHECKED_CAST")
        override fun compareAndSet(new: E, predicate: (old: E, new: E) -> Boolean): Boolean {
            val old = unsafe.getObjectVolatile(inner, offset) as E
            return if(predicate(old, new)) unsafe.compareAndSwapObject(inner, offset, old, new) else false
        }
        override fun lazySet(value: E) = unsafe.putOrderedObject(inner, offset, value)
    }
}