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
class Array64<E> {

    val size: Long
    @PublishedApi internal val array: Array<Array<E>>

    /** Creates a copy of the given FastUtil BigArray. */
    constructor(array: Array<Array<E>>) {
        this.size = BigArrays.length(array)
        this.array = BigArrays.copy(array)
    }
    /** Creates a copy of the given Array64. */
    constructor(array: Array64<E>) : this(array.array)
    /** Creates a new array from the given standard library array. */
    constructor(array: Array<E>) {
        this.size = array.size.toLong()
        this.array = BigArrays.wrap(array)
    }

    fun copy(): Array64<E> = Array64(this)

    infix fun contentEquals(other: Any?): Boolean {
        if(this === other) return true
        if(other !is Array64<*>) return false
        if(size != other.size) return false
        //this can be done safely but much more efficiently in parallel
        for(i in 0 until size) if(this[i] != other[i]) return false
        return true
    }

    /** Returns the element at the given [index]. This method can be called using the index operator. */
    operator fun get(index: Long): E {
        if(index >= this.size || index < 0) throw NoSuchElementException()
        return BigArrays.get(array, index)
    }
    /** Sets the element at the given [index] to the given [value]. This method can be called using the index operator. */
    operator fun set(index: Long, value: E) {
        if(index >= this.size || index < 0) throw NoSuchElementException()
        BigArrays.set(array, index, value)
    }

    /** Performs the given [action] on each element. */
    inline fun forEach(action: (E) -> Unit) {
        //applies the action to each element using a cache-aware iteration
        for(inner in array) {
            for(element in inner) {
                action(element)
            }
        }
    }
    /** Performs the given [action] on each element, providing sequential index with the element. */
    inline fun forEachIndexed(action: (index: Long, E) -> Unit) {
        //applies the action to each element using a cache-aware iteration
        var index = 0L
        for(inner in array) {
            for(element in inner) {
                action(index, element)
                index++
            }
        }
    }
    /** Performs the given [action] on each element within the [range]. */
    inline fun forEachInRange(range: LongRange, action: (E) -> Unit) {
        if(range.first < 0 || range.last >= this.size) throw NoSuchElementException()
        //Calculates the indices of the first and last elements in the range
        val outerIndexOfFirst = BigArrays.segment(range.first)
        val outerIndexOfLast = BigArrays.segment(range.last)
        val innerIndexOfFirst = (range.first - (BigArrays.SEGMENT_SIZE * outerIndexOfFirst)).toInt()
        val innerIndexOfLast = (range.last - (BigArrays.SEGMENT_SIZE * outerIndexOfLast)).toInt()
        //Performs the iteration
        for(outerIndex in outerIndexOfFirst..outerIndexOfLast) {
            val inner = array[outerIndex]
            val startingInnerIndex = if(outerIndex == outerIndexOfFirst) innerIndexOfFirst else 0
            val endingInnerIndex = if(outerIndex == outerIndexOfLast) innerIndexOfLast else BigArrays.SEGMENT_SIZE - 1
            for(innerIndex in startingInnerIndex..endingInnerIndex) {
                action(inner[innerIndex])
            }
        }
    }
    /** Performs the given [action] on each element within the [range], providing sequential index with the element. */
    inline fun forEachInRangeIndexed(range: LongRange, action: (index: Long, E) -> Unit) {
        if(range.first < 0 || range.last >= this.size) throw NoSuchElementException()
        //Calculates the indices of the first and last elements in the range
        val outerIndexOfFirst = BigArrays.segment(range.first)
        val outerIndexOfLast = BigArrays.segment(range.last)
        val innerIndexOfFirst = (range.first - (BigArrays.SEGMENT_SIZE * outerIndexOfFirst)).toInt()
        val innerIndexOfLast = (range.last - (BigArrays.SEGMENT_SIZE * outerIndexOfLast)).toInt()
        //Performs the iteration
        var index = range.first
        for(outerIndex in outerIndexOfFirst..outerIndexOfLast) {
            val inner = array[outerIndex]
            val startingInnerIndex = if(outerIndex == outerIndexOfFirst) innerIndexOfFirst else 0
            val endingInnerIndex = if(outerIndex == outerIndexOfLast) innerIndexOfLast else BigArrays.SEGMENT_SIZE - 1
            for(innerIndex in startingInnerIndex..endingInnerIndex) {
                action(index, inner[innerIndex])
                index++
            }
        }
    }

    companion object {

        const val MAX_SIZE = BigArrays.SEGMENT_SIZE.toLong() * Int.MAX_VALUE

        /**
         * Creates a new array of the specified [size], with all elements initialized according to the given [init] function.
         *
         * This is a pseudo-constructor. Reified type parameters are needed for generic 2D array creation but aren't possible
         * with real constructors, so an inlined operator function is used to act like a constructor.
         */
        inline operator fun <reified E> invoke(size: Long, crossinline init: (Long) -> E): Array64<E> = makeTypedArray64(size, init)
    }
}