package com.millibyte1.array64

/**
 * An Iterator indexed by a Long value.
 * @param E The type of element stored in this iterator.
 */
interface LongIndexedIterator<out E> : Iterator<E> {
    val index: Long
}
/** A ByteIterator indexed by a Long value. */
abstract class LongIndexedByteIterator : ByteIterator(), LongIndexedIterator<Byte>
/** A BooleanIterator indexed by a Long value. */
abstract class LongIndexedBooleanIterator : BooleanIterator(), LongIndexedIterator<Boolean>
/** A CharIterator indexed by a Long value. */
abstract class LongIndexedCharIterator : CharIterator(), LongIndexedIterator<Char>
/** A ShortIterator indexed by a Long value. */
abstract class LongIndexedShortIterator : ShortIterator(), LongIndexedIterator<Short>
/** An IntIterator indexed by a Long value. */
abstract class LongIndexedIntIterator : IntIterator(), LongIndexedIterator<Int>
/** A LongIterator indexed by a Long value. */
abstract class LongIndexedLongIterator : LongIterator(), LongIndexedIterator<Long>
/** A FloatIterator indexed by a Long value. */
abstract class LongIndexedFloatIterator : FloatIterator(), LongIndexedIterator<Float>
/** A DoubleIterator indexed by a Long value. */
abstract class LongIndexedDoubleIterator : DoubleIterator(), LongIndexedIterator<Double>