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
    override fun iterator(): Array64Iterator<E>
    /** Returns an iterator to the element at the given [index]. */
    fun iterator(index: Long): Array64Iterator<E>
}
/** Marker interface for Array64s of unboxed primitive Bytes. */
interface ByteArray64 : Array64<Byte> {
    override fun copy(): ByteArray64
    override operator fun get(index: Long): Byte
    override operator fun set(index: Long, value: Byte)
    override operator fun iterator(): ByteArray64Iterator
    override fun iterator(index: Long): ByteArray64Iterator
}
/** Marker interface for Array64s of unboxed primitive Booleans. */
interface BooleanArray64 : Array64<Boolean> {
    override fun copy(): BooleanArray64
    override operator fun get(index: Long): Boolean
    override operator fun set(index: Long, value: Boolean)
    override operator fun iterator(): BooleanArray64Iterator
    override fun iterator(index: Long): BooleanArray64Iterator
}
/** Marker interface for Array64s of unboxed primitive Chars. */
interface CharArray64 : Array64<Char> {
    override fun copy(): CharArray64
    override operator fun get(index: Long): Char
    override operator fun set(index: Long, value: Char)
    override operator fun iterator(): CharArray64Iterator
    override fun iterator(index: Long): CharArray64Iterator
}
/** Marker interface for Array64s of unboxed primitive Shorts. */
interface ShortArray64 : Array64<Short> {
    override fun copy(): ShortArray64
    override operator fun get(index: Long): Short
    override operator fun set(index: Long, value: Short)
    override operator fun iterator(): ShortArray64Iterator
    override fun iterator(index: Long): ShortArray64Iterator
}
/** Marker interface for Array64s of unboxed primitive Ints. */
interface IntArray64 : Array64<Int> {
    override fun copy(): IntArray64
    override operator fun get(index: Long): Int
    override operator fun set(index: Long, value: Int)
    override operator fun iterator(): IntArray64Iterator
    override fun iterator(index: Long): IntArray64Iterator
}
/** Marker interface for Array64s of unboxed primitive Longs. */
interface LongArray64 : Array64<Long> {
    override fun copy(): LongArray64
    override operator fun get(index: Long): Long
    override operator fun set(index: Long, value: Long)
    override operator fun iterator(): LongArray64Iterator
    override fun iterator(index: Long): LongArray64Iterator
}
/** Marker interface for Array64s of unboxed primitive Floats. */
interface FloatArray64 : Array64<Float> {
    override fun copy(): FloatArray64
    override operator fun get(index: Long): Float
    override operator fun set(index: Long, value: Float)
    override operator fun iterator(): FloatArray64Iterator
    override fun iterator(index: Long): FloatArray64Iterator
}
/** Marker interface for Array64s of unboxed primitive Doubles. */
interface DoubleArray64 : Array64<Double> {
    override fun copy(): DoubleArray64
    override operator fun get(index: Long): Double
    override operator fun set(index: Long, value: Double)
    override operator fun iterator(): DoubleArray64Iterator
    override fun iterator(index: Long): DoubleArray64Iterator
}