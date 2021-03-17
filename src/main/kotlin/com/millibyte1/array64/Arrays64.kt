package com.millibyte1.array64

import com.millibyte1.array64.ByteArray64
import it.unimi.dsi.fastutil.BigArrays

/** Creates a generic array instance with the given size and initializer. */
inline fun <reified T> makeTypedArray(size: Int, init: (Int) -> T): Array<T> = Array(size) { i -> init(i) }
/** Creates a generic [Array64] with the given size and initializer. */
inline fun <reified T> makeTypedArray64(size: Long, crossinline init: (Long) -> T): Array64<T> {
    //determines the number of completely filled inner arrays and the size of the last, unfilled inner array (0 if all arrays are full)
    val fullArrays = (size / BigArrays.SEGMENT_SIZE).toInt()
    val lastInnerSize = (size % BigArrays.SEGMENT_SIZE).toInt()
    //creates an array storing the size of the inner array at each index
    val innerSizes =
        if(lastInnerSize == 0) Array(fullArrays) { BigArrays.SEGMENT_SIZE }
        else Array(fullArrays + 1) { index -> if(index == fullArrays) lastInnerSize else BigArrays.SEGMENT_SIZE }
    //creates an equivalent initializer which takes inner and outer indices instead of just a single long index
    val fakeInit: (Int, Int) -> T = { outerIndex, innerIndex -> init((outerIndex * BigArrays.SEGMENT_SIZE + innerIndex).toLong()) }
    //creates and returns a 2D array using the fakeInit function
    return Array64(makeTyped2DArray(innerSizes, fakeInit))
}
/** Creates a generic 2D array with sizes defined by [innerSizes] and elements initialized according to the provided [init] function. */
inline fun <reified T> makeTyped2DArray(innerSizes: Array<Int>, init: (Int, Int) -> T): Array<Array<T>> {
    return Array(innerSizes.size) { outerIndex ->
        Array(innerSizes[outerIndex]) { innerIndex ->
            init(outerIndex, innerIndex)
        }
    }
}

/** Returns the last valid index for the array. */
val ByteArray64.lastIndex: Long
    get() = size - 1
/** Returns the range of valid indices for the array. */
val ByteArray64.indices: LongRange
    get() = LongRange(0, lastIndex)
/** Returns the last valid index for the array. */
val ShortArray64.lastIndex: Long
    get() = size - 1
/** Returns the range of valid indices for the array. */
val ShortArray64.indices: LongRange
    get() = LongRange(0, lastIndex)
/** Returns the last valid index for the array. */
val IntArray64.lastIndex: Long
    get() = size - 1
/** Returns the range of valid indices for the array. */
val IntArray64.indices: LongRange
    get() = LongRange(0, lastIndex)
/** Returns the last valid index for the array. */
val LongArray64.lastIndex: Long
    get() = size - 1
/** Returns the range of valid indices for the array. */
val LongArray64.indices: LongRange
    get() = LongRange(0, lastIndex)
/** Returns the last valid index for the array. */
val FloatArray64.lastIndex: Long
    get() = size - 1
/** Returns the range of valid indices for the array. */
val FloatArray64.indices: LongRange
    get() = LongRange(0, lastIndex)
/** Returns the last valid index for the array. */
val DoubleArray64.lastIndex: Long
    get() = size - 1
/** Returns the range of valid indices for the array. */
val DoubleArray64.indices: LongRange
    get() = LongRange(0, lastIndex)
/** Returns the last valid index for the array. */
val BooleanArray64.lastIndex: Long
    get() = size - 1
/** Returns the range of valid indices for the array. */
val BooleanArray64.indices: LongRange
    get() = LongRange(0, lastIndex)
/** Returns the last valid index for the array. */
val CharArray64.lastIndex: Long
    get() = size - 1
/** Returns the range of valid indices for the array. */
val CharArray64.indices: LongRange
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