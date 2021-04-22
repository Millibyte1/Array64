package com.millibyte1.array64

import it.unimi.dsi.fastutil.BigArrays

/**
 * A 64-bit-indexed byte array with an interface mirroring that of the standard library class [BooleanArray].
 *
 * Internally uses a 2D array and the FastUtil [BigArrays] library.
 *
 * @property size the number of elements in this array
 * @property array the 2D array used internally. This should not be used except by extension functions.
 *
 */
class SafeBooleanArray64 : BooleanArray64 {

    override val size: Long
    @PublishedApi internal val array: Array<BooleanArray>

    /** Creates a new array of the specified [size], with all elements initialized according to the given [init] function */
    constructor(size: Long, init: (Long) -> Boolean) {
        if(size > MAX_SIZE || size <= 0) throw IllegalArgumentException("Invalid size provided.")
        this.size = size
        //calculates the number of complete inner arrays and the size of the incomplete last inner array
        val fullArrays = (size / BigArrays.SEGMENT_SIZE).toInt()
        val innerSize = (size % BigArrays.SEGMENT_SIZE).toInt()
        //allocates the array
        array =
            if(innerSize == 0) Array(fullArrays) { BooleanArray(BigArrays.SEGMENT_SIZE) }
            else Array(fullArrays + 1) { i -> if(i == fullArrays) BooleanArray(innerSize) else BooleanArray(BigArrays.SEGMENT_SIZE) }
        //initializes the elements of the array using cache-aware iteration as per FastUtil specification
        var index = 0L
        for(inner in array) {
            for(innerIndex in inner.indices) {
                inner[innerIndex] = init(index)
                index++
            }
        }
    }
    /** Creates a new array of the specified [size], with all elements initialized to zero. */
    constructor(size: Long) : this(size, { false })
    /** Creates a copy of the given FastUtil BigArray */
    constructor(array: Array<BooleanArray>) {
        this.size = BigArrays.length(array)
        this.array = BigArrays.copy(array)
    }
    /** Creates a copy of the given Array64 */
    constructor(array: SafeBooleanArray64) : this(array.array)
    /** Creates a new array from the given standard library array */
    constructor(array: BooleanArray) {
        this.size = array.size.toLong()
        this.array = BigArrays.wrap(array)
    }

    override fun copy(): SafeBooleanArray64 = SafeBooleanArray64(this)

    /** Returns the array at the given [index]. This method can be called using the index operator. */
    override operator fun get(index: Long): Boolean {
        if(index >= this.size || index < 0) throw NoSuchElementException()
        return BigArrays.get(array, index)
    }
    /** Sets the element at the given [index] to the given [value]. This method can be called using the index operator. */
    override operator fun set(index: Long, value: Boolean) {
        if(index >= this.size || index < 0) throw NoSuchElementException()
        BigArrays.set(array, index, value)
    }

    /** Creates an iterator over the elements of the array. */
    override operator fun iterator(): BooleanIterator = BooleanArray64Iterator(this, 0)
    override fun iterator(index: Long): BooleanIterator {
        TODO("Not yet implemented")
    }

    companion object {
        const val MAX_SIZE = BigArrays.SEGMENT_SIZE.toLong() * Int.MAX_VALUE
    }
}

/** Simple forward iterator implementation for a BooleanArray64 */
private class BooleanArray64Iterator(val array: SafeBooleanArray64, var index: Long) : BooleanIterator() {
    override fun hasNext(): Boolean = index < array.size
    override fun nextBoolean(): Boolean = if(index < array.size) array[++index] else throw NoSuchElementException()
}