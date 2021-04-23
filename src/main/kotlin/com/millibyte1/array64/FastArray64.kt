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
    /** Creates an Array64 copy of the given FastUtil BigArray. */
    constructor(array: Array<Array<E>>) {
        this.size = BigArrays.length(array)
        this.array = BigArrays.copy(array)
    }
    /** Creates an Array64 copy of the given standard library array. */
    constructor(array: Array<E>) {
        this.size = array.size.toLong()
        this.array = BigArrays.wrap(array)
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
    override fun iterator(index: Long): LongIndexedBidirectionalIterator<E> {
        if(index < 0 || index >= this.size) throw IllegalArgumentException("Invalid index provided.")
        return FastArray64Iterator(this, index)
    }
    override operator fun iterator(): LongIndexedBidirectionalIterator<E> = FastArray64Iterator(this, 0)

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
private class FastArray64Iterator<E>(private val array: FastArray64<E>, index: Long) : LongIndexedBidirectionalIterator<E> {

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