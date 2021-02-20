package com.millibyte1.array64

import com.millibyte1.array64.util.UnsafeUtils
import sun.misc.Unsafe

/*
 * TODO: Implement phantom reference counting and automatic resource management with Java 9's Cleaner utility
 * TODO: Implement parallel processing of elements for efficient aggregate operations and content equality checking
 */
/**
 * A primitive ByteArray re-implementation using 64-bit indexing.
 *
 * Implemented via direct memory management through the sun.misc.Unsafe class. This means that this is not restricted by
 * the maximum heap space, but rather just by the available RAM.
 *
 * @property size Returns the number of elements in the array.
 * @property address The address of the array in memory.
 *
 * @constructor
 * Creates a new array of the specified [size], where each element is calculated by calling the specified [init] function.
 */
class ByteArray64 {

    val size: Long
    val address: Long

    constructor(size: Long, init: (Long) -> Byte) {
        if(size <= 0) throw IllegalArgumentException("Invalid size provided.")
        this.size = size
        address = UnsafeUtils.unsafe.allocateMemory(size)
        for(i in 0 until size) setInternal(i, init(i))
    }

    /** Creates a new array of the specified [size], with all elements initialized to zero. */
    constructor(size: Long) : this(size, { 0 })
    /** Creates a copy of the given array */
    constructor(array: ByteArray64) : this(array.size, { i -> array[i] })
    /** Creates a new array from the given standard library array */
    constructor(array: ByteArray) : this(array.size.toLong(), { i -> array[i.toInt()] })

    fun copy(): ByteArray64 = ByteArray64(this)

    infix fun contentEquals(other: Any?): Boolean {
        if(this === other) return true
        if(other !is ByteArray64) return false
        if(size != other.size) return false
        if(address == other.address) return true
        //this can be done safely but much more efficiently in parallel
        for(i in 0 until size) if(this[i] != other[i]) return false
        return true
    }

    /** Returns the array at the given [index]. This method can be called using the index operator. */
    operator fun get(index: Long): Byte {
        if(index >= this.size || index < 0) throw NoSuchElementException()
        return getInternal(index)
    }
    /** Sets the element at the given [index] to the given [value]. This method can be called using the index operator. */
    operator fun set(index: Long, value: Byte) {
        if(index >= this.size || index < 0) throw NoSuchElementException()
        setInternal(index, value)
    }

    /** Creates an iterator over the elements of the array. */
    operator fun iterator(): ByteIterator = ByteArray64Iterator(this, 0)

    /** Frees this array from the off-heap memory. */
    fun free() = UnsafeUtils.unsafe.freeMemory(address)
    /** Gets the byte at the given index in the array without bounds-checking. Significantly faster than checked get. */
    @PublishedApi internal inline fun getInternal(index: Long): Byte = UnsafeUtils.unsafe.getByte(address + index)
    /** Sets the byte at the given index in the array without bounds-checking. Singificantly faster than checked set. */
    @PublishedApi internal inline fun setInternal(index: Long, value: Byte) = UnsafeUtils.unsafe.putByte(address + index, value)

}

/** Simple forward iterator implementation for a ByteArray64 */
private class ByteArray64Iterator(val array: ByteArray64, var index: Long) : ByteIterator() {
    override fun hasNext(): Boolean = index < array.size
    override fun nextByte(): Byte = if(index < array.size) array[++index] else throw NoSuchElementException()
}

/** Returns the last valid index for the array. */
val ByteArray64.lastIndex: Long
    get() = size - 1
/** Returns the range of valid indices for the array. */
val ByteArray64.indices: LongRange
    get() = LongRange(0, lastIndex)

/** Creates an [Iterable] instance that wraps the original array returning its elements when being iterated. */
fun ByteArray64.asIterable(): Iterable<Byte> = Iterable { this.iterator() }
/** Creates a [Sequence] instance that wraps the original array returning its elements when being iterated. */
fun ByteArray64.asSequence(): Sequence<Byte> = Sequence { this.iterator() }

/** Returns true if all elements match the given [predicate]. */
inline fun ByteArray64.all(predicate: (Byte) -> Boolean): Boolean {
    this.forEach { e -> if(!predicate(e)) return false }
    return true
}
/** Returns true if at least one element matches the given [predicate]. */
inline fun ByteArray64.any(predicate: (Byte) -> Boolean): Boolean {
    this.forEach { e -> if(predicate(e)) return true }
    return false
}
/** Returns true if no elements match the given [predicate]. */
inline fun ByteArray64.none(predicate: (Byte) -> Boolean): Boolean {
    this.forEach { e -> if(predicate(e)) return false }
    return true
}
//TODO: filter will not work for arrays with more than 2^32 matching elements
/** Returns a list containing only elements matching the given [predicate]. */
inline fun ByteArray64.filter(predicate: (Byte) -> Boolean): List<Byte> {
    TODO()
}
/** Returns a list containing only elements matching the given [predicate]. */
inline fun ByteArray64.filterIndexed(predicate: (index: Long, Byte) -> Boolean): List<Byte> {
    TODO()
}
/** Accumulates value starting with the first element and applying [operation] from left to right to current accumulator value and each element. */
inline fun ByteArray64.reduce(operation: (acc: Byte, Byte) -> Byte): Byte {
    TODO()
}
/** Accumulates value starting with the first element and applying [operation] from left to right to current accumulator value and each element with its index in the original array. */
inline fun ByteArray64.reduceIndexed(operation: (index: Long, acc: Byte, Byte) -> Byte): Byte {
    TODO()
}
/** Accumulates value starting with the first element and applying [operation] from right to left to current accumulator value and each element. */
inline fun ByteArray64.reduceRight(operation: (acc: Byte, Byte) -> Byte): Byte {
    TODO()
}
/** Accumulates value starting with the first element and applying [operation] from right to left to current accumulator value and each element with its index in the original array. */
inline fun ByteArray64.reduceRightIndexed(operation: (index: Long, acc: Byte, Byte) -> Byte): Byte {
    TODO()
}
/** Accumulates value starting with [initial] value and applying [operation] from left to right to current accumulator value and each element. */
inline fun <R> ByteArray64.fold(initial: R, operation: (acc: R, Byte) -> R): R {
    TODO()
}
/** Accumulates value starting with [initial] value and applying [operation] from left to right to current accumulator value and each element with its index in the original array. */
inline fun <R> ByteArray64.foldIndexed(initial: R, operation: (index: Long, acc: R, Byte) -> R): R {
    TODO()
}
/** Accumulates value starting with [initial] value and applying [operation] from right to left to current accumulator value and each element. */
inline fun <R> ByteArray64.foldRight(initial: R, operation: (acc: R, Byte) -> R): R {
    TODO()
}
/** Accumulates value starting with [initial] value and applying [operation] from right to left to current accumulator value and each element with its index in the original array. */
inline fun <R> ByteArray64.foldRightIndexed(initial: R, operation: (index: Long, acc: R, Byte) -> R): R {
    TODO()
}
//TODO: map will not work for arrays with more than 2^32 elements
/** Returns a list containing the results of applying the given [transform] function to each element in the original array. */
inline fun <R> ByteArray64.map(transform: (Byte) -> R): List<R> {
    TODO()
}
/** Returns a list containing the results of applying the given [transform] function to each element and its index in the original array. */
inline fun <R> ByteArray64.mapIndexed(transform: (index: Long, Byte) -> R): List<R> {
    TODO()
}
/** Applies the given [transform] function to each element in the original array and appends the results to the given destination. */
inline fun <R, C : MutableCollection<in R>> ByteArray64.mapTo(destination: C, transform: (Byte) -> R): C {
    TODO()
}
/** Applies the given [transform] function to each element and its index in the original array and appends the results to the given destination. */
inline fun <R, C : MutableCollection<in R>> ByteArray64.mapIndexedTo(destination: C, transform: (index: Long, Byte) -> R): C {
    TODO()
}
/** Performs the given [action] on each element. */
inline fun ByteArray64.forEach(action: (Byte) -> Unit) {
    for(i in this.indices) action(getInternal(i))
}
/** Performs the given [action] on each element, providing sequential index with the element. */
inline fun ByteArray64.forEachIndexed(action: (index: Long, Byte) -> Unit) {
    for(i in this.indices) action(i, getInternal(i))
}