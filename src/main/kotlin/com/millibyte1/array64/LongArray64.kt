package com.millibyte1.array64

import it.unimi.dsi.fastutil.BigArrays

/**
 * A 64-bit-indexed byte array with an interface mirroring that of the standard library class [LongArray].
 *
 * Internally uses a 2D array and the FastUtil [BigArrays] library.
 *
 * @property size the number of elements in this array
 * @property array the 2D array used internally. This should not be used except by extension functions.
 *
 */
class LongArray64 {

    val size: Long
    @PublishedApi internal val array: Array<LongArray>

    /** Creates a new array of the specified [size], with all elements initialized according to the given [init] function */
    constructor(size: Long, init: (Long) -> Long) {
        if(size > MAX_SIZE || size <= 0) throw IllegalArgumentException("Invalid size provided.")
        this.size = size
        //calculates the number of complete inner arrays and the size of the incomplete last inner array
        val fullArrays = (size / BigArrays.SEGMENT_SIZE).toInt()
        val innerSize = (size % BigArrays.SEGMENT_SIZE).toInt()
        //allocates the array
        array =
            if(innerSize == 0) Array(fullArrays) { LongArray(BigArrays.SEGMENT_SIZE) }
            else Array(fullArrays + 1) { i -> if(i == fullArrays) LongArray(innerSize) else LongArray(BigArrays.SEGMENT_SIZE) }
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
    constructor(array: Array<LongArray>) {
        this.size = BigArrays.length(array)
        this.array = BigArrays.copy(array)
    }
    /** Creates a copy of the given Array64 */
    constructor(array: LongArray64) : this(array.array)
    /** Creates a new array from the given standard library array */
    constructor(array: LongArray) {
        this.size = array.size.toLong()
        this.array = BigArrays.wrap(array)
    }

    fun copy(): LongArray64 = LongArray64(this)

    infix fun contentEquals(other: Any?): Boolean {
        if(this === other) return true
        if(other !is LongArray64) return false
        if(size != other.size) return false
        //this can be done safely but much more efficiently in parallel
        for(i in 0 until size) if(this[i] != other[i]) return false
        return true
    }

    /** Returns the array at the given [index]. This method can be called using the index operator. */
    operator fun get(index: Long): Long {
        if(index >= this.size || index < 0) throw NoSuchElementException()
        return BigArrays.get(array, index)
    }
    /** Sets the element at the given [index] to the given [value]. This method can be called using the index operator. */
    operator fun set(index: Long, value: Long) {
        if(index >= this.size || index < 0) throw NoSuchElementException()
        BigArrays.set(array, index, value)
    }

    /** Performs the given [action] on each element. */
    inline fun forEach(action: (Long) -> Unit) {
        //applies the action to each element using a cache-aware iteration
        for(inner in array) {
            for(element in inner) {
                action(element)
            }
        }
    }
    /** Performs the given [action] on each element, providing sequential index with the element. */
    inline fun forEachIndexed(action: (index: Long, Long) -> Unit) {
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
    operator fun iterator(): LongIterator = LongArray64Iterator(this, 0)

    companion object {
        const val MAX_SIZE = BigArrays.SEGMENT_SIZE.toLong() * Int.MAX_VALUE
    }
}

/** Simple forward iterator implementation for a LongArray64 */
private class LongArray64Iterator(val array: LongArray64, var index: Long) : LongIterator() {
    override fun hasNext(): Boolean = index < array.size
    override fun nextLong(): Long = if(index < array.size) array[++index] else throw NoSuchElementException()
}

/** Returns the last valid index for the array. */
val LongArray64.lastIndex: Long
    get() = size - 1
/** Returns the range of valid indices for the array. */
val LongArray64.indices: LongRange
    get() = LongRange(0, lastIndex)

/** Creates an [Iterable] instance that wraps the original array returning its elements when being iterated. */
fun LongArray64.asIterable(): Iterable<Long> = Iterable { this.iterator() }
/** Creates a [Sequence] instance that wraps the original array returning its elements when being iterated. */
fun LongArray64.asSequence(): Sequence<Long> = Sequence { this.iterator() }

/** Returns true if all elements match the given [predicate]. */
inline fun LongArray64.all(predicate: (Long) -> Boolean): Boolean {
    this.forEach { e -> if(!predicate(e)) return false }
    return true
}
/** Returns true if at least one element matches the given [predicate]. */
inline fun LongArray64.any(predicate: (Long) -> Boolean): Boolean {
    this.forEach { e -> if(predicate(e)) return true }
    return false
}
/** Returns true if no elements match the given [predicate]. */
inline fun LongArray64.none(predicate: (Long) -> Boolean): Boolean {
    this.forEach { e -> if(predicate(e)) return false }
    return true
}