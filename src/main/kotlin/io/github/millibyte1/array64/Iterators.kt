package io.github.millibyte1.array64

/*
 * This file contains a collection of iterator interfaces and abstract classes which are used by this project.
 * FastUtil's iterator types weren't used due to their unwanted introduction of exceptions, deprecations, etc.
 *
 * It is strictly less performant to use iterators than to use the included higher order functions provided for each
 * concrete implementation type of the Array64 interface, and therefore should be avoided by external users.
 */

/**
 * An Iterator indexed by a Long value.
 * @param E The type of element stored in this iterator.
 */
interface LongIndexedIterator<out E> : Iterator<E> {
    /** The current index of this iterator. */
    val index: Long
}

/**
 * A LongIndexedIterator which can move in both the forward and backward directions and enables the user to set the values of elements.
 * @param E The type of element stored in this iterator.
 */
interface Array64Iterator<E> : Iterator<E>, LongIndexedIterator<E> {
    /**
     * Returns the previous element in the iteration.
     * (Note that alternating calls to [next] and [previous] will return the same element repeatedly.)
     */
    fun previous(): E
    /** Returns true if the iteration has more elements when traversing the list in the reverse direction. */
    fun hasPrevious(): Boolean
    /** Replaces the element at the current index (the last element returned by [next] or [previous]) with the specified element. */
    fun set(element: E)
}
/** An Array64Iterator for unboxed Bytes. */
abstract class ByteArray64Iterator : ByteIterator(), Array64Iterator<Byte> {
    /** Returns the previous element in the iteration without boxing. */
    abstract fun previousByte(): Byte
    final override fun previous(): Byte = previousByte()
    /** Replaces the element at the current index with the specified primitive. */
    abstract fun setByte(element: Byte)
    final override fun set(element: Byte) = setByte(element)
}
/** An Array64Iterator for unboxed Booleans. */
abstract class BooleanArray64Iterator : BooleanIterator(), Array64Iterator<Boolean> {
    /** Returns the previous element in the iteration without boxing. */
    abstract fun previousBoolean(): Boolean
    final override fun previous(): Boolean = previousBoolean()
    /** Replaces the element at the current index with the specified primitive. */
    abstract fun setBoolean(element: Boolean)
    final override fun set(element: Boolean) = setBoolean(element)
}
/** An Array64Iterator for unboxed Chars. */
abstract class CharArray64Iterator : CharIterator(), Array64Iterator<Char> {
    /** Returns the previous element in the iteration without boxing. */
    abstract fun previousChar(): Char
    final override fun previous(): Char = previousChar()
    /** Replaces the element at the current index with the specified primitive. */
    abstract fun setChar(element: Char)
    final override fun set(element: Char) = setChar(element)
}
/** An Array64Iterator for unboxed Shorts. */
abstract class ShortArray64Iterator : ShortIterator(), Array64Iterator<Short> {
    /** Returns the previous element in the iteration without boxing. */
    abstract fun previousShort(): Short
    final override fun previous(): Short = previousShort()
    /** Replaces the element at the current index with the specified primitive. */
    abstract fun setShort(element: Short)
    final override fun set(element: Short) = setShort(element)
}
/** An Array64Iterator for unboxed Ints. */
abstract class IntArray64Iterator : IntIterator(), Array64Iterator<Int> {
    /** Returns the previous element in the iteration without boxing. */
    abstract fun previousInt(): Int
    final override fun previous(): Int = previousInt()
    /** Replaces the element at the current index with the specified primitive. */
    abstract fun setInt(element: Int)
    final override fun set(element: Int) = setInt(element)
}
/** An Array64Iterator for unboxed Longs. */
abstract class LongArray64Iterator : LongIterator(), Array64Iterator<Long> {
    /** Returns the previous element in the iteration without boxing. */
    abstract fun previousLong(): Long
    final override fun previous(): Long = previousLong()
    /** Replaces the element at the current index with the specified primitive. */
    abstract fun setLong(element: Long)
    final override fun set(element: Long) = setLong(element)
}
/** An Array64Iterator for unboxed Floats. */
abstract class FloatArray64Iterator : FloatIterator(), Array64Iterator<Float> {
    /** Returns the previous element in the iteration without boxing. */
    abstract fun previousFloat(): Float
    final override fun previous(): Float = previousFloat()
    /** Replaces the element at the current index with the specified primitive. */
    abstract fun setFloat(element: Float)
    final override fun set(element: Float) = setFloat(element)
}
/** An Array64Iterator for unboxed Doubles. */
abstract class DoubleArray64Iterator : DoubleIterator(), Array64Iterator<Double> {
    /** Returns the previous element in the iteration without boxing. */
    abstract fun previousDouble(): Double
    final override fun previous(): Double = previousDouble()
    /** Replaces the element at the current index with the specified primitive. */
    abstract fun setDouble(element: Double)
    final override fun set(element: Double) = setDouble(element)
}