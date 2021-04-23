package com.millibyte1.array64

/**
 * Base interface for huge arrays which can store more elements than can be indexed by an [Int].
 * Closely mirrors the Kotlin standard library class [Array].
 * @param E The type of element stored in this array.
 */
interface Array64<E> : Iterable<E> {
    /** The number of elements in this array. */
    val size: Long
    /** Produces a shallow copy of this array. */
    fun copy(): Array64<E>
    /** Returns the element at the given [index]. This method can be called using the index operator. */
    operator fun get(index: Long): E
    /** Sets the element at the given [index] to the given [value]. This method can be called using the index operator. */
    operator fun set(index: Long, value: E)
    /** Returns an iterator to the element at the given [index]. */
    fun iterator(index: Long): LongIndexedIterator<E>
}
/** Marker interface for Array64s of unboxed primitive Bytes. */
interface ByteArray64 : Array64<Byte> {
    override fun copy(): ByteArray64
    override operator fun get(index: Long): Byte
    override operator fun set(index: Long, value: Byte)
    override operator fun iterator(): LongIndexedByteIterator
    override fun iterator(index: Long): LongIndexedByteIterator
}
/** Marker interface for Array64s of unboxed primitive Booleans. */
interface BooleanArray64 : Array64<Boolean> {
    override fun copy(): BooleanArray64
    override operator fun get(index: Long): Boolean
    override operator fun set(index: Long, value: Boolean)
    override operator fun iterator(): LongIndexedBooleanIterator
    override fun iterator(index: Long): LongIndexedBooleanIterator
}
/** Marker interface for Array64s of unboxed primitive Chars. */
interface CharArray64 : Array64<Char> {
    override fun copy(): CharArray64
    override operator fun get(index: Long): Char
    override operator fun set(index: Long, value: Char)
    override operator fun iterator(): LongIndexedCharIterator
    override fun iterator(index: Long): LongIndexedCharIterator
}
/** Marker interface for Array64s of unboxed primitive Shorts. */
interface ShortArray64 : Array64<Short> {
    override fun copy(): ShortArray64
    override operator fun get(index: Long): Short
    override operator fun set(index: Long, value: Short)
    override operator fun iterator(): LongIndexedShortIterator
    override fun iterator(index: Long): LongIndexedShortIterator
}
/** Marker interface for Array64s of unboxed primitive Ints. */
interface IntArray64 : Array64<Int> {
    override fun copy(): IntArray64
    override operator fun get(index: Long): Int
    override operator fun set(index: Long, value: Int)
    override operator fun iterator(): LongIndexedIntIterator
    override fun iterator(index: Long): LongIndexedIntIterator
}
/** Marker interface for Array64s of unboxed primitive Longs. */
interface LongArray64 : Array64<Long> {
    override fun copy(): LongArray64
    override operator fun get(index: Long): Long
    override operator fun set(index: Long, value: Long)
    override operator fun iterator(): LongIndexedLongIterator
    override fun iterator(index: Long): LongIndexedLongIterator
}
/** Marker interface for Array64s of unboxed primitive Floats. */
interface FloatArray64 : Array64<Float> {
    override fun copy(): FloatArray64
    override operator fun get(index: Long): Float
    override operator fun set(index: Long, value: Float)
    override operator fun iterator(): LongIndexedFloatIterator
    override fun iterator(index: Long): LongIndexedFloatIterator
}
/** Marker interface for Array64s of unboxed primitive Doubles. */
interface DoubleArray64 : Array64<Double> {
    override fun copy(): DoubleArray64
    override operator fun get(index: Long): Double
    override operator fun set(index: Long, value: Double)
    override operator fun iterator(): LongIndexedDoubleIterator
    override fun iterator(index: Long): LongIndexedDoubleIterator
}