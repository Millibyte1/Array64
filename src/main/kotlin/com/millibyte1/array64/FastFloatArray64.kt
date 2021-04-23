package com.millibyte1.array64

import it.unimi.dsi.fastutil.BigArrays

/**
 * A 64-bit-indexed byte array with an interface mirroring that of the standard library class [FloatArray].
 *
 * Internally uses a 2D array and the FastUtil [BigArrays] library.
 *
 * @property size the number of elements in this array
 * @property array the 2D array used internally. This should not be used except by extension functions.
 *
 */
class FastFloatArray64 : FloatArray64 {

    override val size: Long
    @PublishedApi internal val array: Array<FloatArray>

    /** Creates a new array of the specified [size], with all elements initialized according to the given [init] function */
    constructor(size: Long, init: (Long) -> Float) {
        if(size > MAX_SIZE || size <= 0) throw IllegalArgumentException("Invalid size provided.")
        this.size = size
        //calculates the number of complete inner arrays and the size of the incomplete last inner array
        val fullArrays = (size / BigArrays.SEGMENT_SIZE).toInt()
        val innerSize = (size % BigArrays.SEGMENT_SIZE).toInt()
        //allocates the array
        array =
            if(innerSize == 0) Array(fullArrays) { FloatArray(BigArrays.SEGMENT_SIZE) }
            else Array(fullArrays + 1) { i -> if(i == fullArrays) FloatArray(innerSize) else FloatArray(BigArrays.SEGMENT_SIZE) }
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
    constructor(size: Long) : this(size, { 0.0F })
    /** Creates a copy of the given FastUtil BigArray */
    constructor(array: Array<FloatArray>) {
        this.size = BigArrays.length(array)
        this.array = BigArrays.copy(array)
    }
    /** Creates a copy of the given Array64 */
    constructor(array: FastFloatArray64) : this(array.array)
    /** Creates a new array from the given standard library array */
    constructor(array: FloatArray) {
        this.size = array.size.toLong()
        this.array = BigArrays.wrap(array)
    }

    override fun copy(): FastFloatArray64 = FastFloatArray64(this)

    /** Returns the array at the given [index]. This method can be called using the index operator. */
    override operator fun get(index: Long): Float {
        if(index >= this.size || index < 0) throw NoSuchElementException()
        return BigArrays.get(array, index)
    }
    /** Sets the element at the given [index] to the given [value]. This method can be called using the index operator. */
    override operator fun set(index: Long, value: Float) {
        if(index >= this.size || index < 0) throw NoSuchElementException()
        BigArrays.set(array, index, value)
    }

    override operator fun iterator(): LongIndexedFloatIterator = FastFloatArray64Iterator(this, 0)
    override fun iterator(index: Long): LongIndexedFloatIterator = FastFloatArray64Iterator(this, index)

    companion object {
        const val MAX_SIZE = BigArrays.SEGMENT_SIZE.toLong() * Int.MAX_VALUE
    }
}

/**
 * A simple efficient forward iterator for the FastArray64 class.
 * @constructor Constructs an iterator to the given [index] in the given [array].
 * @param array the array to iterate over
 * @param index the index to start at
 */
class FastFloatArray64Iterator(private val array: FastFloatArray64, index: Long) : LongIndexedFloatIterator() {
    override var index: Long = index
        private set
    private var outerIndex = BigArrays.segment(index)
    private var innerIndex = BigArrays.displacement(index)
    private var inner = array.array[outerIndex]

    override fun hasNext(): Boolean = index < array.size
    override fun nextFloat(): Float {
        val retval = inner[innerIndex]
        updateIndices()
        return retval
    }
    //updates the current index and the cached inner array
    private fun updateIndices() {
        if(innerIndex == BigArrays.SEGMENT_SIZE - 1) {
            innerIndex = 0
            outerIndex++
            inner = array.array[outerIndex]
        }
        else innerIndex++
        index++
    }
}