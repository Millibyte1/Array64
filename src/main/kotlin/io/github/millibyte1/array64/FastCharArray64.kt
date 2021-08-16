package io.github.millibyte1.array64

import it.unimi.dsi.fastutil.BigArrays

/**
 * A 64-bit-indexed byte array with an interface mirroring that of the standard library class [CharArray].
 *
 * Internally uses a 2D array and the FastUtil [BigArrays] library.
 *
 */
open class FastCharArray64 : CharArray64 {

    /** The number of elements in this array. */
    @Suppress("INAPPLICABLE_JVM_NAME")
    @get:JvmName("size")
    override val size: Long

    /** The 2D array used internally. This should not be used except when extending the API. */
    @PublishedApi internal val array: Array<CharArray>

    /**
     * Creates a new array of the specified [size], with all elements initialized to zero.
     * @throws IllegalArgumentException if [size] is not between 1 and [MAX_SIZE]
     */
    constructor(size: Long) {
        if(size > MAX_SIZE || size <= 0) throw IllegalArgumentException("Invalid size provided.")
        this.size = size
        //calculates the number of complete inner arrays and the size of the incomplete last inner array
        val fullArrays = (size / BigArrays.SEGMENT_SIZE).toInt()
        val innerSize = (size % BigArrays.SEGMENT_SIZE).toInt()
        //allocates the array
        array =
            if(innerSize == 0) Array(fullArrays) { CharArray(BigArrays.SEGMENT_SIZE) }
            else Array(fullArrays + 1) { i -> if(i == fullArrays) CharArray(innerSize) else CharArray(BigArrays.SEGMENT_SIZE) }
    }
    /**
     * Creates a new array of the specified [size], with all elements initialized according to the given [init] function.
     * @throws IllegalArgumentException if [size] is not between 1 and [MAX_SIZE]
     */
    constructor(size: Long, init: (Long) -> Char) : this(size) {
        //initializes the elements of the array using cache-aware iteration as per FastUtil specification
        var index = 0L
        for(inner in this.array) {
            var innerIndex = 0
            while(innerIndex < inner.size) {
                inner[innerIndex] = init(index)
                innerIndex++
                index++
            }
        }
    }

    /**
     * Creates a copy of the given array.
     * @param array the array in question
     */
    constructor(array: FastCharArray64) : this(array.array)
    /**
     * Creates a new array from the given FastUtil BigArray, either by copying its contents or simply wrapping it.
     * @param array the array in question
     * @param copy whether to copy (true) the array or directly use it as the internal array (false)
     */
    constructor(array: Array<CharArray>, copy: Boolean = true) {
        this.size = BigArrays.length(array)
        this.array = if(copy) BigArrays.copy(array) else array
    }
    /**
     * Creates a new array from the given standard library array, either by copying its contents or simply wrapping it.
     * @param array the array in question
     * @param copy whether to copy (true) the array or directly use it as the internal array (false)
     */
    constructor(array: CharArray, copy: Boolean = true) {
        this.size = array.size.toLong()
        this.array = if(copy) BigArrays.wrap(array) else Array(1) { array }
    }

    override fun copy(): FastCharArray64 = FastCharArray64(this)

    override operator fun get(index: Long): Char {
        if(index >= this.size || index < 0) throw NoSuchElementException()
        return BigArrays.get(array, index)
    }
    override operator fun set(index: Long, value: Char) {
        if(index >= this.size || index < 0) throw NoSuchElementException()
        BigArrays.set(array, index, value)
    }

    override fun iterator(index: Long): CharArray64Iterator {
        if(index < 0 || index >= this.size) throw IllegalArgumentException("Invalid index provided.")
        return Iterator(this, index)
    }
    override operator fun iterator(): CharArray64Iterator = Iterator(this, 0)

    override fun equals(other: Any?): Boolean {
        if(other === this) return true
        if(other !is FastCharArray64) return false
        if(this.size != other.size) return false
        val thisIterator = this.iterator()
        val otherIterator = other.iterator()
        while(thisIterator.hasNext()) if(thisIterator.nextChar() != otherIterator.nextChar()) return false
        return true
    }

    /**
     * A simple efficient bidirectional iterator for the FastCharArray64 class.
     * @constructor Constructs an iterator to the given [index] in the given [array].
     * @param array the array to iterate over
     * @param index the index to start at
     */
    private class Iterator(private val array: FastCharArray64, index: Long) : CharArray64Iterator() {
        override var index: Long = index
            private set
        private var outerIndex = BigArrays.segment(index)
        private var innerIndex = BigArrays.displacement(index)
        private var inner = array.array[outerIndex]

        override fun hasNext(): Boolean = index < array.size
        override fun hasPrevious(): Boolean = index > 0
        override fun nextChar(): Char {
            if(!hasNext()) throw NoSuchElementException()
            val retval = inner[innerIndex]
            increaseIndices()
            return retval
        }
        override fun previousChar(): Char {
            if(!hasPrevious()) throw NoSuchElementException()
            val retval = inner[innerIndex]
            decreaseIndices()
            return retval
        }
        override fun setChar(element: Char) {
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