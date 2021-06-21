package io.github.millibyte1.array64

import it.unimi.dsi.fastutil.BigArrays

/**
 * A 64-bit-indexed generic array with an interface mirroring that of the standard library class [Array].
 *
 * Internally uses a 2D array and the FastUtil [BigArrays] library.
 *
 * @param E The type of element stored in this array.
 */
class FastArray64<E> : Array64<E> {

    /** The number of elements in this array. */
    @Suppress("INAPPLICABLE_JVM_NAME")
    @get:JvmName("size")
    override val size: Long

    /** The 2D array used internally. This should not be used except when extending the API. */
    @PublishedApi internal val array: Array<Array<E>>

    /**
     * Creates a copy of the given array.
     * @param array the array in question
     */
    constructor(array: FastArray64<E>) : this(array.array)
    /**
     * Creates a new array from the given FastUtil BigArray, either by copying its contents or simply wrapping it.
     * @param array the array in question
     * @param copy whether to copy (true) the array or directly use it as the internal array (false)
     */
    constructor(array: Array<Array<E>>, copy: Boolean = true) {
        this.size = BigArrays.length(array)
        this.array = if(copy) BigArrays.copy(array) else array
    }
    /**
     * Creates a new array from the given standard library array, either by copying its contents or simply wrapping it.
     * @param array the array in question
     * @param copy whether to copy (true) the array or directly use it as the internal array (false)
     */
    constructor(array: Array<E>, copy: Boolean = true) {
        this.size = array.size.toLong()
        this.array = if(copy) BigArrays.wrap(array) else Array(1) { array }
    }

    override fun copy(): FastArray64<E> = FastArray64(this)

    override operator fun get(index: Long): E {
        if(index >= this.size || index < 0) throw NoSuchElementException()
        return BigArrays.get(array, index)
    }
    override operator fun set(index: Long, value: E) {
        if(index >= this.size || index < 0) throw NoSuchElementException()
        BigArrays.set(array, index, value)
    }

    override fun iterator(index: Long): Array64Iterator<E> {
        if(index < 0 || index >= this.size) throw IllegalArgumentException("Invalid index provided.")
        return Iterator(this, index)
    }
    override operator fun iterator(): Array64Iterator<E> = Iterator(this, 0)

    override fun equals(other: Any?): Boolean {
        if(other === this) return true
        if(other !is FastArray64<*>) return false
        if(this.size != other.size) return false
        val thisIterator = this.iterator()
        val otherIterator = other.iterator()
        while(thisIterator.hasNext()) if(thisIterator.next() != otherIterator.next()) return false
        return true
    }

    /**
     * A simple efficient bidirectional iterator for the FastArray64 class.
     * @param E the type of element stored in the array
     * @constructor Constructs an iterator to the given [index] in the given [array].
     * @param array the array to iterate over
     * @param index the index to start at
     */
    private class Iterator<E>(private val array: FastArray64<E>, index: Long) : Array64Iterator<E> {

        override var index: Long = index
            private set
        private var outerIndex = BigArrays.segment(index)
        private var innerIndex = BigArrays.displacement(index)
        private var inner = array.array[outerIndex]

        override fun hasNext(): Boolean = index < array.size
        override fun hasPrevious(): Boolean = index >= 0
        override fun next(): E {
            val retval = inner[innerIndex]
            increaseIndices()
            return retval
        }
        override fun previous(): E {
            val retval = inner[innerIndex]
            decreaseIndices()
            return retval
        }
        override fun set(element: E) {
            inner[innerIndex] = element
        }
        //increases the current index and the cached inner array
        private fun increaseIndices() {
            if(innerIndex == BigArrays.SEGMENT_SIZE - 1) {
                innerIndex = 0
                outerIndex++
                if(index != array.lastIndex) inner = array.array[outerIndex]
            }
            else innerIndex++
            index++
        }
        //decreases the current index and the cached inner array
        private fun decreaseIndices() {
            if(innerIndex == 0) {
                innerIndex = BigArrays.SEGMENT_SIZE - 1
                outerIndex--
                if(index != 0L) inner = array.array[outerIndex]
            }
            else innerIndex--
            index--
        }
    }
}