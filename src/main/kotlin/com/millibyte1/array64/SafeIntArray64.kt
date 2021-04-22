package com.millibyte1.array64

import it.unimi.dsi.fastutil.BigArrays

/**
 * A 64-bit-indexed byte array with an interface mirroring that of the standard library class [IntArray].
 *
 * Internally uses a 2D array and the FastUtil [BigArrays] library.
 *
 * @property size the number of elements in this array
 * @property array the 2D array used internally. This should not be used except by extension functions.
 *
 */
class SafeIntArray64 : IntArray64 {

    override val size: Long
    @PublishedApi internal val array: Array<IntArray>

    /** Creates a new array of the specified [size], with all elements initialized according to the given [init] function */
    constructor(size: Long, init: (Long) -> Int) {
        if(size > MAX_SIZE || size <= 0) throw IllegalArgumentException("Invalid size provided.")
        this.size = size
        //calculates the number of complete inner arrays and the size of the incomplete last inner array
        val fullArrays = (size / BigArrays.SEGMENT_SIZE).toInt()
        val innerSize = (size % BigArrays.SEGMENT_SIZE).toInt()
        //allocates the array
        array =
            if(innerSize == 0) Array(fullArrays) { IntArray(BigArrays.SEGMENT_SIZE) }
            else Array(fullArrays + 1) { i -> if(i == fullArrays) IntArray(innerSize) else IntArray(BigArrays.SEGMENT_SIZE) }
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
    constructor(size: Long) : this(size, { 0 })
    /** Creates a copy of the given FastUtil BigArray */
    constructor(array: Array<IntArray>) {
        this.size = BigArrays.length(array)
        this.array = BigArrays.copy(array)
    }
    /** Creates a copy of the given Array64 */
    constructor(array: SafeIntArray64) : this(array.array)
    /** Creates a new array from the given standard library array */
    constructor(array: IntArray) {
        this.size = array.size.toLong()
        this.array = BigArrays.wrap(array)
    }

    override fun copy(): SafeIntArray64 = SafeIntArray64(this)

    /** Returns the array at the given [index]. This method can be called using the index operator. */
    override operator fun get(index: Long): Int {
        if(index >= this.size || index < 0) throw NoSuchElementException()
        return BigArrays.get(array, index)
    }
    /** Sets the element at the given [index] to the given [value]. This method can be called using the index operator. */
    override operator fun set(index: Long, value: Int) {
        if(index >= this.size || index < 0) throw NoSuchElementException()
        BigArrays.set(array, index, value)
    }

    /** Creates an iterator over the elements of the array. */
    override operator fun iterator(): IntIterator = IntArray64Iterator(this, 0)
    override fun iterator(index: Long): IntIterator {
        TODO("Not yet implemented")
    }

    companion object {
        const val MAX_SIZE = BigArrays.SEGMENT_SIZE.toLong() * Int.MAX_VALUE
    }
}

/** Simple forward iterator implementation for a IntArray64 */
private class IntArray64Iterator(val array: SafeIntArray64, var index: Long) : IntIterator() {
    override fun hasNext(): Boolean = index < array.size
    override fun nextInt(): Int = if(index < array.size) array[++index] else throw NoSuchElementException()
}