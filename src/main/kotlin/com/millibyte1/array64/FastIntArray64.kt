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
class FastIntArray64 : IntArray64 {

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

    /** Creates a copy of the given Array64 */
    constructor(array: FastIntArray64) : this(array.array)
    /**
     * Creates a new array from the given FastUtil BigArray, either by copying its contents or simply wrapping it.
     * @param array the array in question
     * @param copy whether to copy (true) the array or directly use it as the internal array (false)
     */
    constructor(array: Array<IntArray>, copy: Boolean = true) {
        this.size = BigArrays.length(array)
        this.array = if(copy) BigArrays.copy(array) else array
    }
    /**
     * Creates a new array from the given standard library array, either by copying its contents or simply wrapping it.
     * @param array the array in question
     * @param copy whether to copy (true) the array or directly use it as the internal array (false)
     */
    constructor(array: IntArray, copy: Boolean = true) {
        this.size = array.size.toLong()
        this.array = if(copy) BigArrays.wrap(array) else Array(1) { array }
    }

    override fun equals(other: Any?): Boolean {
        if(other === this) return true
        if(other !is FastIntArray64) return false
        if(this.size != other.size) return false
        val thisIterator = this.iterator()
        val otherIterator = other.iterator()
        while(thisIterator.hasNext()) if(thisIterator.next() != otherIterator.next()) return false
        return true
    }

    override fun copy(): FastIntArray64 = FastIntArray64(this)

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

    /**
     * Returns an iterator to the element at the given [index].
     * @throws IllegalArgumentException if an invalid index is provided
     */
    override fun iterator(index: Long): LongIndexedBidirectionalIntIterator {
        if(index < 0 || index >= this.size) throw IllegalArgumentException("Invalid index provided.")
        return FastIntArray64Iterator(this, index)
    }
    override operator fun iterator(): LongIndexedBidirectionalIntIterator = FastIntArray64Iterator(this, 0)

    companion object {
        const val MAX_SIZE = BigArrays.SEGMENT_SIZE.toLong() * Int.MAX_VALUE
    }
}

/**
 * A simple efficient bidirectional iterator for the FastIntArray64 class.
 * @constructor Constructs an iterator to the given [index] in the given [array].
 * @param array the array to iterate over
 * @param index the index to start at
 */
private class FastIntArray64Iterator(private val array: FastIntArray64, index: Long) : LongIndexedBidirectionalIntIterator() {
    override var index: Long = index
        private set
    private var outerIndex = BigArrays.segment(index)
    private var innerIndex = BigArrays.displacement(index)
    private var inner = array.array[outerIndex]

    override fun hasNext(): Boolean = index < array.size
    override fun hasPrevious(): Boolean = index > 0
    override fun nextInt(): Int {
        val retval = inner[innerIndex]
        increaseIndices()
        return retval
    }
    override fun previousInt(): Int {
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