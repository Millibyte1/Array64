package io.github.millibyte1.array64

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

    /** Creates a new array of the specified [size], with all elements initialized to zero. */
    constructor(size: Long) {
        if(size > MAX_SIZE || size <= 0) throw IllegalArgumentException("Invalid size provided.")
        this.size = size
        //calculates the number of complete inner arrays and the size of the incomplete last inner array
        val fullArrays = (size / BigArrays.SEGMENT_SIZE).toInt()
        val innerSize = (size % BigArrays.SEGMENT_SIZE).toInt()
        //allocates the array
        array =
            if(innerSize == 0) Array(fullArrays) { FloatArray(BigArrays.SEGMENT_SIZE) }
            else Array(fullArrays + 1) { i -> if(i == fullArrays) FloatArray(innerSize) else FloatArray(BigArrays.SEGMENT_SIZE) }
    }

    /** Creates a copy of the given Array64 */
    constructor(array: FastFloatArray64) : this(array.array)
    /**
     * Creates a new array from the given FastUtil BigArray, either by copying its contents or simply wrapping it.
     * @param array the array in question
     * @param copy whether to copy (true) the array or directly use it as the internal array (false)
     */
    constructor(array: Array<FloatArray>, copy: Boolean = true) {
        this.size = BigArrays.length(array)
        this.array = if(copy) BigArrays.copy(array) else array
    }
    /**
     * Creates a new array from the given standard library array, either by copying its contents or simply wrapping it.
     * @param array the array in question
     * @param copy whether to copy (true) the array or directly use it as the internal array (false)
     */
    constructor(array: FloatArray, copy: Boolean = true) {
        this.size = array.size.toLong()
        this.array = if(copy) BigArrays.wrap(array) else Array(1) { array }
    }

    override fun equals(other: Any?): Boolean {
        if(other === this) return true
        if(other !is FastFloatArray64) return false
        if(this.size != other.size) return false
        val thisIterator = this.iterator()
        val otherIterator = other.iterator()
        while(thisIterator.hasNext()) if(thisIterator.nextFloat() != otherIterator.nextFloat()) return false
        return true
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

    /**
     * Returns an iterator to the element at the given [index].
     * @throws IllegalArgumentException if an invalid index is provided
     */
    override fun iterator(index: Long): FloatArray64Iterator {
        if(index < 0 || index >= this.size) throw IllegalArgumentException("Invalid index provided.")
        return FastFloatArray64Iterator(this, index)
    }
    override operator fun iterator(): FloatArray64Iterator = FastFloatArray64Iterator(this, 0)

    companion object {
        /**
         * Creates a new array of the specified [size], with all elements initialized according to the given [init] function.
         *
         * This is a pseudo-constructor. Reified type parameters are needed for generic 2D array creation but aren't possible
         * with real constructors, so an inlined operator function is used to act like a constructor.
         */
        inline operator fun invoke(size: Long, init: (Long) -> Float): FastFloatArray64 {
            val retval = FastFloatArray64(size)
            //initializes the elements of the array using cache-aware iteration as per FastUtil specification
            var index = 0L
            for(inner in retval.array) {
                var innerIndex = 0
                while(innerIndex < inner.size) {
                    inner[innerIndex] = init(index)
                    innerIndex++
                    index++
                }
            }
            return retval
        }
        /** The theoretical maximum number of elements that can fit in this array */
        const val MAX_SIZE = BigArrays.SEGMENT_SIZE.toLong() * Int.MAX_VALUE
    }
}

/**
 * A simple efficient bidirectional iterator for the FastFloatArray64 class.
 * @constructor Constructs an iterator to the given [index] in the given [array].
 * @param array the array to iterate over
 * @param index the index to start at
 */
private class FastFloatArray64Iterator(private val array: FastFloatArray64, index: Long) : FloatArray64Iterator() {
    override var index: Long = index
        private set
    private var outerIndex = BigArrays.segment(index)
    private var innerIndex = BigArrays.displacement(index)
    private var inner = array.array[outerIndex]

    override fun hasNext(): Boolean = index < array.size
    override fun hasPrevious(): Boolean = index > 0
    override fun nextFloat(): Float {
        if(!hasNext()) throw NoSuchElementException()
        val retval = inner[innerIndex]
        increaseIndices()
        return retval
    }
    override fun previousFloat(): Float {
        if(!hasPrevious()) throw NoSuchElementException()
        val retval = inner[innerIndex]
        decreaseIndices()
        return retval
    }
    override fun setFloat(element: Float) {
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