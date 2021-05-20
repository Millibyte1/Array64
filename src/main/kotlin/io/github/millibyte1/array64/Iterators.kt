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
 * An Iterator which can move in both the forward and backward directions and enables the user to set the values of elements.
 * @param E The type of element stored in this iterator.
 */
interface ArrayIterator<E> : Iterator<E> {
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
/** An ArrayIterator for unboxed Bytes. */
abstract class ByteArrayIterator : ByteIterator(), ArrayIterator<Byte> {
    /** Returns the previous element in the iteration without boxing. */
    abstract fun previousByte(): Byte
    final override fun previous(): Byte = previousByte()
    /** Replaces the element at the current index with the specified primitive. */
    abstract fun setByte(element: Byte)
    final override fun set(element: Byte) = setByte(element)
}
/** An ArrayIterator for unboxed Booleans. */
abstract class BooleanArrayIterator : BooleanIterator(), ArrayIterator<Boolean> {
    /** Returns the previous element in the iteration without boxing. */
    abstract fun previousBoolean(): Boolean
    final override fun previous(): Boolean = previousBoolean()
    /** Replaces the element at the current index with the specified primitive. */
    abstract fun setBoolean(element: Boolean)
    final override fun set(element: Boolean) = setBoolean(element)
}
/** An ArrayIterator for unboxed Chars. */
abstract class CharArrayIterator : CharIterator(), ArrayIterator<Char> {
    /** Returns the previous element in the iteration without boxing. */
    abstract fun previousChar(): Char
    final override fun previous(): Char = previousChar()
    /** Replaces the element at the current index with the specified primitive. */
    abstract fun setChar(element: Char)
    final override fun set(element: Char) = setChar(element)
}
/** An ArrayIterator for unboxed Shorts. */
abstract class ShortArrayIterator : ShortIterator(), ArrayIterator<Short> {
    /** Returns the previous element in the iteration without boxing. */
    abstract fun previousShort(): Short
    final override fun previous(): Short = previousShort()
    /** Replaces the element at the current index with the specified primitive. */
    abstract fun setShort(element: Short)
    final override fun set(element: Short) = setShort(element)
}
/** An ArrayIterator for unboxed Ints. */
abstract class IntArrayIterator : IntIterator(), ArrayIterator<Int> {
    /** Returns the previous element in the iteration without boxing. */
    abstract fun previousInt(): Int
    final override fun previous(): Int = previousInt()
    /** Replaces the element at the current index with the specified primitive. */
    abstract fun setInt(element: Int)
    final override fun set(element: Int) = setInt(element)
}
/** An ArrayIterator for unboxed Longs. */
abstract class LongArrayIterator : LongIterator(), ArrayIterator<Long> {
    /** Returns the previous element in the iteration without boxing. */
    abstract fun previousLong(): Long
    final override fun previous(): Long = previousLong()
    /** Replaces the element at the current index with the specified primitive. */
    abstract fun setLong(element: Long)
    final override fun set(element: Long) = setLong(element)
}
/** An ArrayIterator for unboxed Floats. */
abstract class FloatArrayIterator : FloatIterator(), ArrayIterator<Float> {
    /** Returns the previous element in the iteration without boxing. */
    abstract fun previousFloat(): Float
    final override fun previous(): Float = previousFloat()
    /** Replaces the element at the current index with the specified primitive. */
    abstract fun setFloat(element: Float)
    final override fun set(element: Float) = setFloat(element)
}
/** An ArrayIterator for unboxed Doubles. */
abstract class DoubleArrayIterator : DoubleIterator(), ArrayIterator<Double> {
    /** Returns the previous element in the iteration without boxing. */
    abstract fun previousDouble(): Double
    final override fun previous(): Double = previousDouble()
    /** Replaces the element at the current index with the specified primitive. */
    abstract fun setDouble(element: Double)
    final override fun set(element: Double) = setDouble(element)
}

/**
 * An ArrayIterator indexed by a Long value.
 * @param E The type of element stored in this iterator.
 */
interface Array64Iterator<E> : LongIndexedIterator<E>, ArrayIterator<E>
/** A ByteArrayIterator indexed by a Long value. */
abstract class ByteArray64Iterator : ByteArrayIterator(), Array64Iterator<Byte>
/** A BooleanArrayIterator indexed by a Long value. */
abstract class BooleanArray64Iterator : BooleanArrayIterator(), Array64Iterator<Boolean>
/** A CharArrayIterator indexed by a Long value. */
abstract class CharArray64Iterator : CharArrayIterator(), Array64Iterator<Char>
/** A ShortArrayIterator indexed by a Long value. */
abstract class ShortArray64Iterator : ShortArrayIterator(), Array64Iterator<Short>
/** An IntArrayIterator indexed by a Long value. */
abstract class IntArray64Iterator : IntArrayIterator(), Array64Iterator<Int>
/** A LongArrayIterator indexed by a Long value. */
abstract class LongArray64Iterator : LongArrayIterator(), Array64Iterator<Long>
/** A FloatArrayIterator indexed by a Long value. */
abstract class FloatArray64Iterator : FloatArrayIterator(), Array64Iterator<Float>
/** A DoubleArrayIterator indexed by a Long value. */
abstract class DoubleArray64Iterator : DoubleArrayIterator(), Array64Iterator<Double>