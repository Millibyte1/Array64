package io.github.millibyte1.array64.atomic

import io.github.millibyte1.array64.*

/** An Array64Iterator which supports atomic operations such as compareAndSet. */
interface AtomicArray64Iterator<E> : Array64Iterator<E> {
    /**
     * Returns the next element in the iteration. Volatile.
     * (Note that alternating calls to [next] and [previous] will return the same element repeatedly.)
     */
    override fun next(): E
    /**
     * Returns the previous element in the iteration. Volatile.
     * (Note that alternating calls to [next] and [previous] will return the same element repeatedly.)
     */
    override fun previous(): E
    /**
     * Replaces the element at the current index (the last element returned by [next] or [previous])
     * with the specified element. Volatile.
     */
    override fun set(element: E)
    /**
     * Sets the element at the current index to the given [new] value and returns the old value. Atomic.
     * @param new the value to set the element to
     * @return the old value of the element
     */
    fun getAndSet(new: E): E
    /**
     * Sets the element at the current index to the value resulting from applying the given [transform] to the old value. Atomic.
     * @param transform the pure (side effect-free) transform function to apply to the old value
     * @return the old value of the element
     */
    fun getAndSet(transform: (E) -> E): E
    /**
     * Sets the element at the current index to the resulting value from applying the given [transform] to the old value. Atomic.
     * @param transform the pure (side effect-free) transform function to apply to the old value
     * @return the new value of the element
     */
    fun setAndGet(transform: (E) -> E): E
    /**
     * Sets the element at the current index to the given [new] value if the current value matches [expected]. Atomic.
     * @param new the value to set the element to
     * @param expected the expected value of the element
     * @return true if the element was able to be updated, otherwise false
     */
    fun compareAndSet(new: E, expected: E): Boolean
    /**
     * Sets the element at the current index to the given [new] value if the provided [predicate] returns true when
     * applied to the old and new values.
     * @param new the value to set the element to
     * @param predicate the binary predicate to apply to the old and new values to decide whether to update
     * @return true if the element was able to be updated, otherwise false
     */
    fun compareAndSet(new: E, predicate: (old: E, new: E) -> Boolean): Boolean
    /**
     * Eventually sets the element at the given [index] to the given [value].
     * Depending on the implementation, may immediately perform a volatile write. 
     * @param value the value to set the element to
     */
    fun lazySet(value: E)
}
/** A BooleanArray64Iterator which supports atomic operations such as compareAndSet. */
abstract class AtomicBooleanArray64Iterator : BooleanArray64Iterator(), AtomicArray64Iterator<Boolean> {
    /** Returns the next element in the iteration without boxing. Volatile. */
    abstract override fun previousBoolean(): Boolean
    /** Returns the previous element in the iteration without boxing. Volatile. */
    abstract override fun nextBoolean(): Boolean
    /** Replaces the element at the current index with the specified primitive. Volatile. */
    abstract override fun setBoolean(element: Boolean)
    /**
     * Sets the element at the current index to the given [new] value and returns the old value. Atomic.
     * @param new the value to set the element to
     * @return the old value of the element
     */
    abstract fun getAndSetBoolean(new: Boolean): Boolean
    /**
     * Sets the element at the current index to the value resulting from applying the given [transform] to the old value. Atomic.
     * @param transform the pure (side effect-free) transform function to apply to the old value
     * @return the old value of the element
     */
    abstract fun getAndSetBoolean(transform: (Boolean) -> Boolean): Boolean
    /**
     * Sets the element at the current index to the resulting value from applying the given [transform] to the old value. Atomic.
     * @param transform the pure (side effect-free) transform function to apply to the old value
     * @return the new value of the element
     */
    abstract fun setAndGetBoolean(transform: (Boolean) -> Boolean): Boolean
    /**
     * Sets the element at the current index to the given [new] value if the current value matches [expected]. Atomic.
     * @param new the value to set the element to
     * @param expected the expected value of the element
     * @return true if the element was able to be updated, otherwise false
     */
    abstract fun compareAndSetBoolean(new: Boolean, expected: Boolean): Boolean
    /**
     * Sets the element at the current index to the given [new] value if the provided [predicate] returns true when
     * applied to the old and new values.
     * @param new the value to set the element to
     * @param predicate the binary predicate to apply to the old and new values to decide whether to update
     * @return true if the element was able to be updated, otherwise false
     */
    abstract fun compareAndSetBoolean(new: Boolean, predicate: (old: Boolean, new: Boolean) -> Boolean): Boolean
    /**
     * Eventually sets the element at the given [index] to the given [value].
     * Depending on the implementation, may immediately perform a volatile write.
     * @param value the value to set the element to
     */
    abstract fun lazySetBoolean(value: Boolean)
    final override fun getAndSet(new: Boolean) = getAndSetBoolean(new)
    final override fun getAndSet(transform: (Boolean) -> Boolean) = getAndSetBoolean(transform)
    final override fun setAndGet(transform: (Boolean) -> Boolean) = setAndGetBoolean(transform)
    final override fun compareAndSet(new: Boolean, expected: Boolean) = compareAndSetBoolean(new, expected)
    final override fun compareAndSet(new: Boolean, predicate: (old: Boolean, new: Boolean) -> Boolean) = compareAndSetBoolean(new, predicate)
    final override fun lazySet(value: Boolean) = lazySetBoolean(value)
}
/** A ByteArray64Iterator which supports atomic operations such as compareAndSet. */
abstract class AtomicByteArray64Iterator : ByteArray64Iterator(), AtomicArray64Iterator<Byte> {
    /** Returns the next element in the iteration without boxing. Volatile. */
    abstract override fun previousByte(): Byte
    /** Returns the previous element in the iteration without boxing. Volatile. */
    abstract override fun nextByte(): Byte
    /** Replaces the element at the current index with the specified primitive. Volatile. */
    abstract override fun setByte(element: Byte)
    /**
     * Sets the element at the current index to the given [new] value and returns the old value. Atomic.
     * @param new the value to set the element to
     * @return the old value of the element
     */
    abstract fun getAndSetByte(new: Byte): Byte
    /**
     * Sets the element at the current index to the value resulting from applying the given [transform] to the old value. Atomic.
     * @param transform the pure (side effect-free) transform function to apply to the old value
     * @return the old value of the element
     */
    abstract fun getAndSetByte(transform: (Byte) -> Byte): Byte
    /**
     * Sets the element at the current index to the resulting value from applying the given [transform] to the old value. Atomic.
     * @param transform the pure (side effect-free) transform function to apply to the old value
     * @return the new value of the element
     */
    abstract fun setAndGetByte(transform: (Byte) -> Byte): Byte
    /**
     * Sets the element at the current index to the given [new] value if the current value matches [expected]. Atomic.
     * @param new the value to set the element to
     * @param expected the expected value of the element
     * @return true if the element was able to be updated, otherwise false
     */
    abstract fun compareAndSetByte(new: Byte, expected: Byte): Boolean
    /**
     * Sets the element at the current index to the given [new] value if the provided [predicate] returns true when
     * applied to the old and new values.
     * @param new the value to set the element to
     * @param predicate the binary predicate to apply to the old and new values to decide whether to update
     * @return true if the element was able to be updated, otherwise false
     */
    abstract fun compareAndSetByte(new: Byte, predicate: (old: Byte, new: Byte) -> Boolean): Boolean
    /**
     * Eventually sets the element at the given [index] to the given [value].
     * Depending on the implementation, may immediately perform a volatile write.
     * @param value the value to set the element to
     */
    abstract fun lazySetByte(value: Byte)
    final override fun getAndSet(new: Byte) = getAndSetByte(new)
    final override fun getAndSet(transform: (Byte) -> Byte) = getAndSetByte(transform)
    final override fun setAndGet(transform: (Byte) -> Byte) = setAndGetByte(transform)
    final override fun compareAndSet(new: Byte, expected: Byte) = compareAndSetByte(new, expected)
    final override fun compareAndSet(new: Byte, predicate: (old: Byte, new: Byte) -> Boolean) = compareAndSetByte(new, predicate)
    final override fun lazySet(value: Byte) = lazySetByte(value)
}
/** A CharArray64Iterator which supports atomic operations such as compareAndSet. */
abstract class AtomicCharArray64Iterator : CharArray64Iterator(), AtomicArray64Iterator<Char> {
    /** Returns the next element in the iteration without boxing. Volatile. */
    abstract override fun previousChar(): Char
    /** Returns the previous element in the iteration without boxing. Volatile. */
    abstract override fun nextChar(): Char
    /** Replaces the element at the current index with the specified primitive. Volatile. */
    abstract override fun setChar(element: Char)
    /**
     * Sets the element at the current index to the given [new] value and returns the old value. Atomic.
     * @param new the value to set the element to
     * @return the old value of the element
     */
    abstract fun getAndSetChar(new: Char): Char
    /**
     * Sets the element at the current index to the value resulting from applying the given [transform] to the old value. Atomic.
     * @param transform the pure (side effect-free) transform function to apply to the old value
     * @return the old value of the element
     */
    abstract fun getAndSetChar(transform: (Char) -> Char): Char
    /**
     * Sets the element at the current index to the resulting value from applying the given [transform] to the old value. Atomic.
     * @param transform the pure (side effect-free) transform function to apply to the old value
     * @return the new value of the element
     */
    abstract fun setAndGetChar(transform: (Char) -> Char): Char
    /**
     * Sets the element at the current index to the given [new] value if the current value matches [expected]. Atomic.
     * @param new the value to set the element to
     * @param expected the expected value of the element
     * @return true if the element was able to be updated, otherwise false
     */
    abstract fun compareAndSetChar(new: Char, expected: Char): Boolean
    /**
     * Sets the element at the current index to the given [new] value if the provided [predicate] returns true when
     * applied to the old and new values.
     * @param new the value to set the element to
     * @param predicate the binary predicate to apply to the old and new values to decide whether to update
     * @return true if the element was able to be updated, otherwise false
     */
    abstract fun compareAndSetChar(new: Char, predicate: (old: Char, new: Char) -> Boolean): Boolean
    /**
     * Eventually sets the element at the given [index] to the given [value].
     * Depending on the implementation, may immediately perform a volatile write.
     * @param value the value to set the element to
     */
    abstract fun lazySetChar(value: Char)
    final override fun getAndSet(new: Char) = getAndSetChar(new)
    final override fun getAndSet(transform: (Char) -> Char) = getAndSetChar(transform)
    final override fun setAndGet(transform: (Char) -> Char) = setAndGetChar(transform)
    final override fun compareAndSet(new: Char, expected: Char) = compareAndSetChar(new, expected)
    final override fun compareAndSet(new: Char, predicate: (old: Char, new: Char) -> Boolean) = compareAndSetChar(new, predicate)
    final override fun lazySet(value: Char) = lazySetChar(value)
}
/** A DoubleArray64Iterator which supports atomic operations such as compareAndSet. */
abstract class AtomicDoubleArray64Iterator : DoubleArray64Iterator(), AtomicArray64Iterator<Double> {
    /** Returns the next element in the iteration without boxing. Volatile. */
    abstract override fun previousDouble(): Double
    /** Returns the previous element in the iteration without boxing. Volatile. */
    abstract override fun nextDouble(): Double
    /** Replaces the element at the current index with the specified primitive. Volatile. */
    abstract override fun setDouble(element: Double)
    /**
     * Sets the element at the current index to the given [new] value and returns the old value. Atomic.
     * @param new the value to set the element to
     * @return the old value of the element
     */
    abstract fun getAndSetDouble(new: Double): Double
    /**
     * Sets the element at the current index to the value resulting from applying the given [transform] to the old value. Atomic.
     * @param transform the pure (side effect-free) transform function to apply to the old value
     * @return the old value of the element
     */
    abstract fun getAndSetDouble(transform: (Double) -> Double): Double
    /**
     * Sets the element at the current index to the resulting value from applying the given [transform] to the old value. Atomic.
     * @param transform the pure (side effect-free) transform function to apply to the old value
     * @return the new value of the element
     */
    abstract fun setAndGetDouble(transform: (Double) -> Double): Double
    /**
     * Sets the element at the current index to the given [new] value if the current value matches [expected]. Atomic.
     * @param new the value to set the element to
     * @param expected the expected value of the element
     * @return true if the element was able to be updated, otherwise false
     */
    abstract fun compareAndSetDouble(new: Double, expected: Double): Boolean
    /**
     * Sets the element at the current index to the given [new] value if the provided [predicate] returns true when
     * applied to the old and new values.
     * @param new the value to set the element to
     * @param predicate the binary predicate to apply to the old and new values to decide whether to update
     * @return true if the element was able to be updated, otherwise false
     */
    abstract fun compareAndSetDouble(new: Double, predicate: (old: Double, new: Double) -> Boolean): Boolean
    /**
     * Eventually sets the element at the given [index] to the given [value].
     * Depending on the implementation, may immediately perform a volatile write.
     * @param value the value to set the element to
     */
    abstract fun lazySetDouble(value: Double)
    final override fun getAndSet(new: Double) = getAndSetDouble(new)
    final override fun getAndSet(transform: (Double) -> Double) = getAndSetDouble(transform)
    final override fun setAndGet(transform: (Double) -> Double) = setAndGetDouble(transform)
    final override fun compareAndSet(new: Double, expected: Double) = compareAndSetDouble(new, expected)
    final override fun compareAndSet(new: Double, predicate: (old: Double, new: Double) -> Boolean) = compareAndSetDouble(new, predicate)
    final override fun lazySet(value: Double) = lazySetDouble(value)
}
/** A FloatArray64Iterator which supports atomic operations such as compareAndSet. */
abstract class AtomicFloatArray64Iterator : FloatArray64Iterator(), AtomicArray64Iterator<Float> {
    /** Returns the next element in the iteration without boxing. Volatile. */
    abstract override fun previousFloat(): Float
    /** Returns the previous element in the iteration without boxing. Volatile. */
    abstract override fun nextFloat(): Float
    /** Replaces the element at the current index with the specified primitive. Volatile. */
    abstract override fun setFloat(element: Float)
    /**
     * Sets the element at the current index to the given [new] value and returns the old value. Atomic.
     * @param new the value to set the element to
     * @return the old value of the element
     */
    abstract fun getAndSetFloat(new: Float): Float
    /**
     * Sets the element at the current index to the value resulting from applying the given [transform] to the old value. Atomic.
     * @param transform the pure (side effect-free) transform function to apply to the old value
     * @return the old value of the element
     */
    abstract fun getAndSetFloat(transform: (Float) -> Float): Float
    /**
     * Sets the element at the current index to the resulting value from applying the given [transform] to the old value. Atomic.
     * @param transform the pure (side effect-free) transform function to apply to the old value
     * @return the new value of the element
     */
    abstract fun setAndGetFloat(transform: (Float) -> Float): Float
    /**
     * Sets the element at the current index to the given [new] value if the current value matches [expected]. Atomic.
     * @param new the value to set the element to
     * @param expected the expected value of the element
     * @return true if the element was able to be updated, otherwise false
     */
    abstract fun compareAndSetFloat(new: Float, expected: Float): Boolean
    /**
     * Sets the element at the current index to the given [new] value if the provided [predicate] returns true when
     * applied to the old and new values.
     * @param new the value to set the element to
     * @param predicate the binary predicate to apply to the old and new values to decide whether to update
     * @return true if the element was able to be updated, otherwise false
     */
    abstract fun compareAndSetFloat(new: Float, predicate: (old: Float, new: Float) -> Boolean): Boolean
    /**
     * Eventually sets the element at the given [index] to the given [value].
     * Depending on the implementation, may immediately perform a volatile write.
     * @param value the value to set the element to
     */
    abstract fun lazySetFloat(value: Float)
    final override fun getAndSet(new: Float) = getAndSetFloat(new)
    final override fun getAndSet(transform: (Float) -> Float) = getAndSetFloat(transform)
    final override fun setAndGet(transform: (Float) -> Float) = setAndGetFloat(transform)
    final override fun compareAndSet(new: Float, expected: Float) = compareAndSetFloat(new, expected)
    final override fun compareAndSet(new: Float, predicate: (old: Float, new: Float) -> Boolean) = compareAndSetFloat(new, predicate)
    final override fun lazySet(value: Float) = lazySetFloat(value)
}
/** A IntArray64Iterator which supports atomic operations such as compareAndSet. */
abstract class AtomicIntArray64Iterator : IntArray64Iterator(), AtomicArray64Iterator<Int> {
    /** Returns the next element in the iteration without boxing. Volatile. */
    abstract override fun previousInt(): Int
    /** Returns the previous element in the iteration without boxing. Volatile. */
    abstract override fun nextInt(): Int
    /** Replaces the element at the current index with the specified primitive. Volatile. */
    abstract override fun setInt(element: Int)
    /**
     * Sets the element at the current index to the given [new] value and returns the old value. Atomic.
     * @param new the value to set the element to
     * @return the old value of the element
     */
    abstract fun getAndSetInt(new: Int): Int
    /**
     * Sets the element at the current index to the value resulting from applying the given [transform] to the old value. Atomic.
     * @param transform the pure (side effect-free) transform function to apply to the old value
     * @return the old value of the element
     */
    abstract fun getAndSetInt(transform: (Int) -> Int): Int
    /**
     * Sets the element at the current index to the resulting value from applying the given [transform] to the old value. Atomic.
     * @param transform the pure (side effect-free) transform function to apply to the old value
     * @return the new value of the element
     */
    abstract fun setAndGetInt(transform: (Int) -> Int): Int
    /**
     * Sets the element at the current index to the given [new] value if the current value matches [expected]. Atomic.
     * @param new the value to set the element to
     * @param expected the expected value of the element
     * @return true if the element was able to be updated, otherwise false
     */
    abstract fun compareAndSetInt(new: Int, expected: Int): Boolean
    /**
     * Sets the element at the current index to the given [new] value if the provided [predicate] returns true when
     * applied to the old and new values.
     * @param new the value to set the element to
     * @param predicate the binary predicate to apply to the old and new values to decide whether to update
     * @return true if the element was able to be updated, otherwise false
     */
    abstract fun compareAndSetInt(new: Int, predicate: (old: Int, new: Int) -> Boolean): Boolean
    /**
     * Eventually sets the element at the given [index] to the given [value].
     * Depending on the implementation, may immediately perform a volatile write.
     * @param value the value to set the element to
     */
    abstract fun lazySetInt(value: Int)
    final override fun getAndSet(new: Int) = getAndSetInt(new)
    final override fun getAndSet(transform: (Int) -> Int) = getAndSetInt(transform)
    final override fun setAndGet(transform: (Int) -> Int) = setAndGetInt(transform)
    final override fun compareAndSet(new: Int, expected: Int) = compareAndSetInt(new, expected)
    final override fun compareAndSet(new: Int, predicate: (old: Int, new: Int) -> Boolean) = compareAndSetInt(new, predicate)
    final override fun lazySet(value: Int) = lazySetInt(value)
}
/** A LongArray64Iterator which supports atomic operations such as compareAndSet. */
abstract class AtomicLongArray64Iterator : LongArray64Iterator(), AtomicArray64Iterator<Long> {
    /** Returns the next element in the iteration without boxing. Volatile. */
    abstract override fun previousLong(): Long
    /** Returns the previous element in the iteration without boxing. Volatile. */
    abstract override fun nextLong(): Long
    /** Replaces the element at the current index with the specified primitive. Volatile. */
    abstract override fun setLong(element: Long)
    /**
     * Sets the element at the current index to the given [new] value and returns the old value. Atomic.
     * @param new the value to set the element to
     * @return the old value of the element
     */
    abstract fun getAndSetLong(new: Long): Long
    /**
     * Sets the element at the current index to the value resulting from applying the given [transform] to the old value. Atomic.
     * @param transform the pure (side effect-free) transform function to apply to the old value
     * @return the old value of the element
     */
    abstract fun getAndSetLong(transform: (Long) -> Long): Long
    /**
     * Sets the element at the current index to the resulting value from applying the given [transform] to the old value. Atomic.
     * @param transform the pure (side effect-free) transform function to apply to the old value
     * @return the new value of the element
     */
    abstract fun setAndGetLong(transform: (Long) -> Long): Long
    /**
     * Sets the element at the current index to the given [new] value if the current value matches [expected]. Atomic.
     * @param new the value to set the element to
     * @param expected the expected value of the element
     * @return true if the element was able to be updated, otherwise false
     */
    abstract fun compareAndSetLong(new: Long, expected: Long): Boolean
    /**
     * Sets the element at the current index to the given [new] value if the provided [predicate] returns true when
     * applied to the old and new values.
     * @param new the value to set the element to
     * @param predicate the binary predicate to apply to the old and new values to decide whether to update
     * @return true if the element was able to be updated, otherwise false
     */
    abstract fun compareAndSetLong(new: Long, predicate: (old: Long, new: Long) -> Boolean): Boolean
    /**
     * Eventually sets the element at the given [index] to the given [value].
     * Depending on the implementation, may immediately perform a volatile write.
     * @param value the value to set the element to
     */
    abstract fun lazySetLong(value: Long)
    final override fun getAndSet(new: Long) = getAndSetLong(new)
    final override fun getAndSet(transform: (Long) -> Long) = getAndSetLong(transform)
    final override fun setAndGet(transform: (Long) -> Long) = setAndGetLong(transform)
    final override fun compareAndSet(new: Long, expected: Long) = compareAndSetLong(new, expected)
    final override fun compareAndSet(new: Long, predicate: (old: Long, new: Long) -> Boolean) = compareAndSetLong(new, predicate)
    final override fun lazySet(value: Long) = lazySetLong(value)
}
/** A ShortArray64Iterator which supports atomic operations such as compareAndSet. */
abstract class AtomicShortArray64Iterator : ShortArray64Iterator(), AtomicArray64Iterator<Short> {
    /** Returns the next element in the iteration without boxing. Volatile. */
    abstract override fun previousShort(): Short
    /** Returns the previous element in the iteration without boxing. Volatile. */
    abstract override fun nextShort(): Short
    /** Replaces the element at the current index with the specified primitive. Volatile. */
    abstract override fun setShort(element: Short)
    /**
     * Sets the element at the current index to the given [new] value and returns the old value. Atomic.
     * @param new the value to set the element to
     * @return the old value of the element
     */
    abstract fun getAndSetShort(new: Short): Short
    /**
     * Sets the element at the current index to the value resulting from applying the given [transform] to the old value. Atomic.
     * @param transform the pure (side effect-free) transform function to apply to the old value
     * @return the old value of the element
     */
    abstract fun getAndSetShort(transform: (Short) -> Short): Short
    /**
     * Sets the element at the current index to the resulting value from applying the given [transform] to the old value. Atomic.
     * @param transform the pure (side effect-free) transform function to apply to the old value
     * @return the new value of the element
     */
    abstract fun setAndGetShort(transform: (Short) -> Short): Short
    /**
     * Sets the element at the current index to the given [new] value if the current value matches [expected]. Atomic.
     * @param new the value to set the element to
     * @param expected the expected value of the element
     * @return true if the element was able to be updated, otherwise false
     */
    abstract fun compareAndSetShort(new: Short, expected: Short): Boolean
    /**
     * Sets the element at the current index to the given [new] value if the provided [predicate] returns true when
     * applied to the old and new values.
     * @param new the value to set the element to
     * @param predicate the binary predicate to apply to the old and new values to decide whether to update
     * @return true if the element was able to be updated, otherwise false
     */
    abstract fun compareAndSetShort(new: Short, predicate: (old: Short, new: Short) -> Boolean): Boolean
    /**
     * Eventually sets the element at the given [index] to the given [value].
     * Depending on the implementation, may immediately perform a volatile write.
     * @param value the value to set the element to
     */
    abstract fun lazySetShort(value: Short)
    final override fun getAndSet(new: Short) = getAndSetShort(new)
    final override fun getAndSet(transform: (Short) -> Short) = getAndSetShort(transform)
    final override fun setAndGet(transform: (Short) -> Short) = setAndGetShort(transform)
    final override fun compareAndSet(new: Short, expected: Short) = compareAndSetShort(new, expected)
    final override fun compareAndSet(new: Short, predicate: (old: Short, new: Short) -> Boolean) = compareAndSetShort(new, predicate)
    final override fun lazySet(value: Short) = lazySetShort(value)
}