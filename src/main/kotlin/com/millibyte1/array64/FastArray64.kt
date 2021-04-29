package com.millibyte1.array64

import it.unimi.dsi.fastutil.BigArrays

/**
 * A 64-bit-indexed generic array with an interface mirroring that of the standard library class [Array].
 *
 * Internally uses a 2D array and the FastUtil [BigArrays] library.
 *
 * @property size the number of elements in this array.
 * @property array the 2D array used internally. This should not be used except when extending the API.
 *
 */
class FastArray64<E> : Array64<E> {

    override val size: Long
    @PublishedApi internal val array: Array<Array<E>>

    /** Creates a copy of the given Array64. */
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

    override fun equals(other: Any?): Boolean {
        if(other === this) return true
        if(other !is FastArray64<*>) return false
        if(this.size != other.size) return false
        val thisIterator = this.iterator()
        val otherIterator = other.iterator()
        while(thisIterator.hasNext()) if(thisIterator.next() != otherIterator.next()) return false
        return true
    }

    override fun copy(): FastArray64<E> = FastArray64(this)

    /** Returns the element at the given [index]. This method can be called using the index operator. */
    override operator fun get(index: Long): E {
        if(index >= this.size || index < 0) throw NoSuchElementException()
        return BigArrays.get(array, index)
    }
    /** Sets the element at the given [index] to the given [value]. This method can be called using the index operator. */
    override operator fun set(index: Long, value: E) {
        if(index >= this.size || index < 0) throw NoSuchElementException()
        BigArrays.set(array, index, value)
    }

    /**
     * Returns an iterator to the element at the given [index].
     * @throws IllegalArgumentException if an invalid index is provided
     */
    override fun iterator(index: Long): Array64Iterator<E> {
        if(index < 0 || index >= this.size) throw IllegalArgumentException("Invalid index provided.")
        return FastArray64Iterator(this, index)
    }
    override operator fun iterator(): Array64Iterator<E> = FastArray64Iterator(this, 0)

    companion object {
        /**
         * Creates a new array of the specified [size], with all elements initialized according to the given [init] function.
         *
         * This is a pseudo-constructor. Reified type parameters are needed for generic 2D array creation but aren't possible
         * with real constructors, so an inlined operator function is used to act like a constructor.
         */
        inline operator fun <reified E> invoke(size: Long, crossinline init: (Long) -> E): FastArray64<E> = makeTypedArray64(size, init)
        /** The theoretical maximum number of elements that can fit in this array */
        const val MAX_SIZE = BigArrays.SEGMENT_SIZE.toLong() * Int.MAX_VALUE
    }
}

/**
 * A simple efficient bidirectional iterator for the FastArray64 class.
 * @param E the type of element stored in the array
 * @constructor Constructs an iterator to the given [index] in the given [array].
 * @param array the array to iterate over
 * @param index the index to start at
 */
private class FastArray64Iterator<E>(private val array: FastArray64<E>, index: Long) : Array64Iterator<E> {

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
            inner = array.array[outerIndex]
        }
        else innerIndex++
        index++
    }
    //decreases the current index and the cached inner array
    private fun decreaseIndices() {
        if(innerIndex == 0) {
            innerIndex = BigArrays.SEGMENT_SIZE - 1
            outerIndex--
            inner = array.array[outerIndex]
        }
        else innerIndex--
        index--
    }
}