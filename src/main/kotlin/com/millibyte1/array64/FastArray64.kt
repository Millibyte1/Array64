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

    override operator fun iterator(): Iterator<E> {
        TODO("Not yet implemented")
    }
    override fun iterator(index: Long): Iterator<E> {
        TODO("Not yet implemented")
    }

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
 * A simple efficient iterator for the FastArray64 class.
 * Does not require a
 */
private class FastArray64Iterator<E>(private val array: FastArray64<E>, private var index: Long) : Iterator<E> {
    private var outerIndex = BigArrays.segment(index)
    private var innerIndex = BigArrays.displacement(index)
    override fun hasNext(): Boolean {
        TODO("Not yet implemented")
    }
    override fun next(): E {
        TODO("Not yet implemented")
    }

}