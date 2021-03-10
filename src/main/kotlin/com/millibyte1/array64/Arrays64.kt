package com.millibyte1.array64

import com.millibyte1.array64.ByteArray64

/** A collection of extension functions for collections processing for the different implemented array types */


/* ========================================= ByteArray extension functions ========================================= */

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