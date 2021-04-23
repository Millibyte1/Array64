package com.millibyte1.array64

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
 * An Iterator which can move in both the forward and backward directions.
 * @param E The type of element stored in this iterator.
 */
interface BidirectionalIterator<out E> : Iterator<E> {
    /**
     * Returns the previous element in the iteration.
     * (Note that alternating calls to [next] and [previous] will return the same element repeatedly.)
     */
    fun previous(): E
    /** Returns true if the iteration has more elements when traversing the list in the reverse direction. */
    fun hasPrevious(): Boolean
}
/** A bidirectional ByteIterator. */
abstract class BidirectionalByteIterator : ByteIterator(), BidirectionalIterator<Byte> {
    final override fun previous(): Byte = previousByte()
    abstract fun previousByte(): Byte
}
/** A bidirectional BooleanIterator. */
abstract class BidirectionalBooleanIterator : BooleanIterator(), BidirectionalIterator<Boolean> {
    final override fun previous(): Boolean = previousBoolean()
    abstract fun previousBoolean(): Boolean
}
/** A bidirectional CharIterator. */
abstract class BidirectionalCharIterator : CharIterator(), BidirectionalIterator<Char> {
    final override fun previous(): Char = previousChar()
    abstract fun previousChar(): Char
}
/** A bidirectional ShortIterator. */
abstract class BidirectionalShortIterator : ShortIterator(), BidirectionalIterator<Short> {
    final override fun previous(): Short = previousShort()
    abstract fun previousShort(): Short
}
/** A bidirectional IntIterator. */
abstract class BidirectionalIntIterator : IntIterator(), BidirectionalIterator<Int> {
    final override fun previous(): Int = previousInt()
    abstract fun previousInt(): Int
}
/** A bidirectional LongIterator. */
abstract class BidirectionalLongIterator : LongIterator(), BidirectionalIterator<Long> {
    final override fun previous(): Long = previousLong()
    abstract fun previousLong(): Long
}
/** A bidirectional FloatIterator. */
abstract class BidirectionalFloatIterator : FloatIterator(), BidirectionalIterator<Float> {
    final override fun previous(): Float = previousFloat()
    abstract fun previousFloat(): Float
}
/** A bidirectional DoubleIterator. */
abstract class BidirectionalDoubleIterator : DoubleIterator(), BidirectionalIterator<Double> {
    final override fun previous(): Double = previousDouble()
    abstract fun previousDouble(): Double
}

/**
 * A bidirectional Iterator indexed by a Long value.
 * @param E The type of element stored in this iterator.
 */
interface LongIndexedBidirectionalIterator<out E> : LongIndexedIterator<E>, BidirectionalIterator<E>
/** A bidirectional ByteIterator indexed by a Long value. */
abstract class LongIndexedBidirectionalByteIterator : BidirectionalByteIterator(), LongIndexedBidirectionalIterator<Byte>
/** A bidirectional BooleanIterator indexed by a Long value. */
abstract class LongIndexedBidirectionalBooleanIterator : BidirectionalBooleanIterator(), LongIndexedBidirectionalIterator<Boolean>
/** A bidirectional CharIterator indexed by a Long value. */
abstract class LongIndexedBidirectionalCharIterator : BidirectionalCharIterator(), LongIndexedBidirectionalIterator<Char>
/** A bidirectional ShortIterator indexed by a Long value. */
abstract class LongIndexedBidirectionalShortIterator : BidirectionalShortIterator(), LongIndexedBidirectionalIterator<Short>
/** An BidirectionalIntIterator indexed by a Long value. */
abstract class LongIndexedBidirectionalIntIterator : BidirectionalIntIterator(), LongIndexedBidirectionalIterator<Int>
/** A bidirectional LongIterator indexed by a Long value. */
abstract class LongIndexedBidirectionalLongIterator : BidirectionalLongIterator(), LongIndexedBidirectionalIterator<Long>
/** A bidirectional FloatIterator indexed by a Long value. */
abstract class LongIndexedBidirectionalFloatIterator : BidirectionalFloatIterator(), LongIndexedBidirectionalIterator<Float>
/** A bidirectional DoubleIterator indexed by a Long value. */
abstract class LongIndexedBidirectionalDoubleIterator : BidirectionalDoubleIterator(), LongIndexedBidirectionalIterator<Double>