package io.github.millibyte1.array64.atomic

import io.github.millibyte1.array64.Array64

/**
 * An extension of the Array64 interface supporting atomic operations such as compareAndSet.
 * Loosely based off the Jetbrains JDK implementation of atomic arrays.
 * @param E The type of element stored in this array.
 */
interface AtomicArray64<E> : Array64<E> {
    /**
     * Returns the element at the given [index]. Volatile.
     * @param index the index of the desired element
     * @return the element at [index]
     * @throws NoSuchElementException if the index is out of bounds
     */
    override operator fun get(index: Long): E
    /**
     * Sets the element at the given [index] to the given [value]. Volatile.
     * @param index the index of the element to set
     * @param value the value to set the element to
     * @throws NoSuchElementException if the index is out of bounds
     */
    override operator fun set(index: Long, value: E)
    /**
     * Sets the element at position [index] to the given [new] value and returns the old value. Atomic.
     * @param index the index of the element to set
     * @param new the value to set the element to
     * @return the old value of the element
     * @throws NoSuchElementException if the index is out of bounds
     */
    fun getAndSet(index: Long, new: E): E
    /**
     * Sets the element at position [index] to the value resulting from applying the given [transform] to the old value. Atomic.
     * @param index the index of the element to set
     * @param transform the pure (side effect-free) transform function to apply to the old value
     * @return the old value of the element
     * @throws NoSuchElementException if the index is out of bounds
     */
    fun getAndSet(index: Long, transform: (E) -> E): E
    /**
     * Sets the element at position [index] to the resulting value from applying the given [transform] to the old value. Atomic.
     * @param index the index of the element to set
     * @param transform the pure (side effect-free) transform function to apply to the old value
     * @return the new value of the element
     * @throws NoSuchElementException if the index is out of bounds
     */
    fun setAndGet(index: Long, transform: (E) -> E): E
    /**
     * Sets the element at position [index] to the given [new] value if the current value matches [expected]. Atomic.
     * @param index the index of the element to try and set
     * @param new the value to set the element to
     * @param expected the expected value of the element
     * @return true if the element was able to be updated, otherwise false
     * @throws NoSuchElementException if the index is out of bounds
     */
    fun compareAndSet(index: Long, new: E, expected: E): Boolean
    /**
     * Sets the element at position [index] to the given [new] value if the provided [predicate] returns true when
     * applied to the old and new values.
     * @param index the index of the element to try and set
     * @param new the value to set the element to
     * @param predicate the binary predicate to apply to the old and new values to decide whether to update
     * @return true if the element was able to be updated, otherwise false
     * @throws NoSuchElementException if the index is out of bounds
     */
    fun compareAndSet(index: Long, new: E, predicate: (old: E, new: E) -> Boolean): Boolean
    /**
     * Eventually sets the element at the given [index] to the given [value].
     * @param index the index of the element to set
     * @param value the value to set the element to
     * @throws NoSuchElementException if the index is out of bounds
     */
    fun lazySet(index: Long, value: E)
    /** Returns an iterator to the first element of this array which supports volatile and atomic operations. */
    override operator fun iterator(): AtomicArray64Iterator<E>
    /**
     * Returns an iterator to the element at the given [index] which supports volatile and atomic operations.
     * @throws IllegalArgumentException if an invalid index is provided
     */
    override fun iterator(index: Long): AtomicArray64Iterator<E>
}

/** Marker interface for AtomicArray64s of unboxed primitive Booleans. */
interface AtomicBooleanArray64 : AtomicArray64<Boolean> {
    override operator fun get(index: Long): Boolean
    override operator fun set(index: Long, value: Boolean)
    override fun getAndSet(index: Long, new: Boolean): Boolean
    override fun getAndSet(index: Long, transform: (Boolean) -> Boolean): Boolean
    override fun setAndGet(index: Long, transform: (Boolean) -> Boolean): Boolean
    override fun compareAndSet(index: Long, new: Boolean, expected: Boolean): Boolean
    override fun compareAndSet(index: Long, new: Boolean, predicate: (old: Boolean, new: Boolean) -> Boolean): Boolean
    override fun lazySet(index: Long, value: Boolean)
    override operator fun iterator(): AtomicBooleanArray64Iterator
    override fun iterator(index: Long): AtomicBooleanArray64Iterator
}
/** Marker interface for AtomicArray64s of unboxed primitive Bytes. */
interface AtomicByteArray64 : AtomicArray64<Byte> {
    override operator fun get(index: Long): Byte
    override operator fun set(index: Long, value: Byte)
    override fun getAndSet(index: Long, new: Byte): Byte
    override fun getAndSet(index: Long, transform: (Byte) -> Byte): Byte
    override fun setAndGet(index: Long, transform: (Byte) -> Byte): Byte
    override fun compareAndSet(index: Long, new: Byte, expected: Byte): Boolean
    override fun compareAndSet(index: Long, new: Byte, predicate: (old: Byte, new: Byte) -> Boolean): Boolean
    override fun lazySet(index: Long, value: Byte)
    override operator fun iterator(): AtomicByteArray64Iterator
    override fun iterator(index: Long): AtomicByteArray64Iterator
}
/** Marker interface for AtomicArray64s of unboxed primitive Chars. */
interface AtomicCharArray64 : AtomicArray64<Char> {
    override operator fun get(index: Long): Char
    override operator fun set(index: Long, value: Char)
    override fun getAndSet(index: Long, new: Char): Char
    override fun getAndSet(index: Long, transform: (Char) -> Char): Char
    override fun setAndGet(index: Long, transform: (Char) -> Char): Char
    override fun compareAndSet(index: Long, new: Char, expected: Char): Boolean
    override fun compareAndSet(index: Long, new: Char, predicate: (old: Char, new: Char) -> Boolean): Boolean
    override fun lazySet(index: Long, value: Char)
    override operator fun iterator(): AtomicCharArray64Iterator
    override fun iterator(index: Long): AtomicCharArray64Iterator
}
/** Marker interface for AtomicArray64s of unboxed primitive Doubles. */
interface AtomicDoubleArray64 : AtomicArray64<Double> {
    override operator fun get(index: Long): Double
    override operator fun set(index: Long, value: Double)
    override fun getAndSet(index: Long, new: Double): Double
    override fun getAndSet(index: Long, transform: (Double) -> Double): Double
    override fun setAndGet(index: Long, transform: (Double) -> Double): Double
    override fun compareAndSet(index: Long, new: Double, expected: Double): Boolean
    override fun compareAndSet(index: Long, new: Double, predicate: (old: Double, new: Double) -> Boolean): Boolean
    override fun lazySet(index: Long, value: Double)
    override operator fun iterator(): AtomicDoubleArray64Iterator
    override fun iterator(index: Long): AtomicDoubleArray64Iterator
}
/** Marker interface for AtomicArray64s of unboxed primitive Floats. */
interface AtomicFloatArray64 : AtomicArray64<Float> {
    override operator fun get(index: Long): Float
    override operator fun set(index: Long, value: Float)
    override fun getAndSet(index: Long, new: Float): Float
    override fun getAndSet(index: Long, transform: (Float) -> Float): Float
    override fun setAndGet(index: Long, transform: (Float) -> Float): Float
    override fun compareAndSet(index: Long, new: Float, expected: Float): Boolean
    override fun compareAndSet(index: Long, new: Float, predicate: (old: Float, new: Float) -> Boolean): Boolean
    override fun lazySet(index: Long, value: Float)
    override operator fun iterator(): AtomicFloatArray64Iterator
    override fun iterator(index: Long): AtomicFloatArray64Iterator
}
/** Marker interface for AtomicArray64s of unboxed primitive Ints. */
interface AtomicIntArray64 : AtomicArray64<Int> {
    override operator fun get(index: Long): Int
    override operator fun set(index: Long, value: Int)
    override fun getAndSet(index: Long, new: Int): Int
    override fun getAndSet(index: Long, transform: (Int) -> Int): Int
    override fun setAndGet(index: Long, transform: (Int) -> Int): Int
    override fun compareAndSet(index: Long, new: Int, expected: Int): Boolean
    override fun compareAndSet(index: Long, new: Int, predicate: (old: Int, new: Int) -> Boolean): Boolean
    override fun lazySet(index: Long, value: Int)
    override operator fun iterator(): AtomicIntArray64Iterator
    override fun iterator(index: Long): AtomicIntArray64Iterator
}
/** Marker interface for AtomicArray64s of unboxed primitive Longs. */
interface AtomicLongArray64 : AtomicArray64<Long> {
    override operator fun get(index: Long): Long
    override operator fun set(index: Long, value: Long)
    override fun getAndSet(index: Long, new: Long): Long
    override fun getAndSet(index: Long, transform: (Long) -> Long): Long
    override fun setAndGet(index: Long, transform: (Long) -> Long): Long
    override fun compareAndSet(index: Long, new: Long, expected: Long): Boolean
    override fun compareAndSet(index: Long, new: Long, predicate: (old: Long, new: Long) -> Boolean): Boolean
    override fun lazySet(index: Long, value: Long)
    override operator fun iterator(): AtomicLongArray64Iterator
    override fun iterator(index: Long): AtomicLongArray64Iterator
}
/** Marker interface for AtomicArray64s of unboxed primitive Shorts. */
interface AtomicShortArray64 : AtomicArray64<Short> {
    override operator fun get(index: Long): Short
    override operator fun set(index: Long, value: Short)
    override fun getAndSet(index: Long, new: Short): Short
    override fun getAndSet(index: Long, transform: (Short) -> Short): Short
    override fun setAndGet(index: Long, transform: (Short) -> Short): Short
    override fun compareAndSet(index: Long, new: Short, expected: Short): Boolean
    override fun compareAndSet(index: Long, new: Short, predicate: (old: Short, new: Short) -> Boolean): Boolean
    override fun lazySet(index: Long, value: Short)
    override operator fun iterator(): AtomicShortArray64Iterator
    override fun iterator(index: Long): AtomicShortArray64Iterator
}
