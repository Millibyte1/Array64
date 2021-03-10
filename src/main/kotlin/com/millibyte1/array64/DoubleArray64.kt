package com.millibyte1.array64

import it.unimi.dsi.fastutil.BigArrays

/**
 * A 64-bit-indexed byte array with an interface mirroring that of the standard library class [DoubleArray].
 *
 * Internally uses a 2D array and the FastUtil [BigArrays] library.
 *
 * @property size the number of elements in this array
 * @property array the 2D array used internally. This should not be used except by extension functions.
 *
 */
class DoubleArray64 {

    val size: Long
    @PublishedApi internal val array: Array<DoubleArray>

    /** Creates a new array of the specified [size], with all elements initialized according to the given [init] function */
    constructor(size: Long, init: (Long) -> Double) {
        if(size > MAX_SIZE || size <= 0) throw IllegalArgumentException("Invalid size provided.")
        this.size = size
        //calculates the number of complete inner arrays and the size of the incomplete last inner array
        val fullArrays = (size / BigArrays.SEGMENT_SIZE).toInt()
        val innerSize = (size % BigArrays.SEGMENT_SIZE).toInt()
        //allocates the array
        array =
            if(innerSize == 0) Array(fullArrays) { DoubleArray(BigArrays.SEGMENT_SIZE) }
            else Array(fullArrays + 1) { i -> if(i == fullArrays) DoubleArray(innerSize) else DoubleArray(BigArrays.SEGMENT_SIZE) }
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
    constructor(size: Long) : this(size, { 0.0 })
    /** Creates a copy of the given FastUtil BigArray */
    constructor(array: Array<DoubleArray>) {
        this.size = BigArrays.length(array)
        this.array = BigArrays.copy(array)
    }
    /** Creates a copy of the given Array64 */
    constructor(array: DoubleArray64) : this(array.array)
    /** Creates a new array from the given standard library array */
    constructor(array: DoubleArray) {
        this.size = array.size.toLong()
        this.array = BigArrays.wrap(array)
    }

    fun copy(): DoubleArray64 = DoubleArray64(this)

    infix fun contentEquals(other: Any?): Boolean {
        if(this === other) return true
        if(other !is DoubleArray64) return false
        if(size != other.size) return false
        //this can be done safely but much more efficiently in parallel
        for(i in 0 until size) if(this[i] != other[i]) return false
        return true
    }

    /** Returns the array at the given [index]. This method can be called using the index operator. */
    operator fun get(index: Long): Double {
        if(index >= this.size || index < 0) throw NoSuchElementException()
        return BigArrays.get(array, index)
    }
    /** Sets the element at the given [index] to the given [value]. This method can be called using the index operator. */
    operator fun set(index: Long, value: Double) {
        if(index >= this.size || index < 0) throw NoSuchElementException()
        BigArrays.set(array, index, value)
    }

    /** Performs the given [action] on each element. */
    inline fun forEach(action: (Double) -> Unit) {
        //applies the action to each element using a cache-aware iteration
        for(inner in array) {
            for(element in inner) {
                action(element)
            }
        }
    }
    /** Performs the given [action] on each element, providing sequential index with the element. */
    inline fun forEachIndexed(action: (index: Long, Double) -> Unit) {
        //applies the action to each element using a cache-aware iteration
        var index = 0L
        for(inner in array) {
            for(element in inner) {
                action(index, element)
                index++
            }
        }
    }

    /** Creates an iterator over the elements of the array. */
    operator fun iterator(): DoubleIterator = DoubleArray64Iterator(this, 0)

    companion object {
        const val MAX_SIZE = BigArrays.SEGMENT_SIZE.toLong() * Int.MAX_VALUE
    }
}

/** Simple forward iterator implementation for a DoubleArray64 */
private class DoubleArray64Iterator(val array: DoubleArray64, var index: Long) : DoubleIterator() {
    override fun hasNext(): Boolean = index < array.size
    override fun nextDouble(): Double = if(index < array.size) array[++index] else throw NoSuchElementException()
}