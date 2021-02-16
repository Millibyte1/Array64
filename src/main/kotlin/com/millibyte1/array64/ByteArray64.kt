package com.millibyte1.array64

/**
 * A primitive ByteArray re-implementation using 64 bits of indices.
 * Internally uses an Array<ByteArray>, so there's slightly more overhead than the basic 32-bit ByteArray.
 *
 * @property size Returns the number of elements in the array.
 *
 * @constructor
 * Creates a new array of the specified [size], where each element is calculated by calling the specified [init] function.
 */
class ByteArray64(val size: Long, init: (Long) -> Byte) {

    private val data: Array<ByteArray>

    /** Creates a new array of the specified [size], with all elements initialized to zero. */
    constructor(size: Long) : this(size, { 0 })
    /** Creates a copy of the given array */
    constructor(array: ByteArray64) : this(array.size, { i -> array[i] })
    /** Creates a new array from the given standard library array */
    constructor(array: ByteArray) : this(array.size.toLong(), { i -> array[i.toInt()] })
    /** Creates a new array from the given ordered list of standard library arrays */
    constructor(arrays: List<ByteArray>) : this(
        arrays.fold(0) { sum, array -> sum + array.size },
        { longIndex -> arrays[(longIndex shr 32).toInt()][(longIndex shl 32).toInt()] }
    )

    init {
        val outerSize = (size shr 32).toInt()
        val innerSize = (size shl 32).toInt()
        //allocates the arrays
        data = Array(outerSize) { i -> if(i == outerSize - 1) ByteArray(innerSize) else ByteArray(Int.MAX_VALUE) }
        //applies the initializer function to every index
        for(i in 0 until size) {
            val outerIndex = (i shr 32).toInt()
            val innerIndex = (i shl 32).toInt()
            data[outerIndex][innerIndex] = init(i)
        }
    }

    fun copy(): ByteArray64 {
        return ByteArray64(this)
    }

    /** Returns the array at the given [index]. This method can be called using the index operator. */
    operator fun get(index: Long): Byte {
        val outerIndex = (index shr 32).toInt()
        val innerIndex = (index shl 32).toInt()
        return data[outerIndex][innerIndex]
    }
    /** Sets the element at the given [index] to the given [value]. This method can be called using the index operator. */
    operator fun set(index: Long, value: Byte) {
        val outerIndex = (index shr 32).toInt()
        val innerIndex = (index shl 32).toInt()
        data[outerIndex][innerIndex] = value
    }

    /** Creates an iterator over the elements of the array. */
    operator fun iterator(): ByteIterator {
        TODO()
    }
}

/** Returns the last valid index for the array. */
val ByteArray64.lastIndex: Long
    get() = size - 1
/** Returns the range of valid indices for the array. */
val ByteArray64.indices: LongRange
    get() = LongRange(0, lastIndex)

/** Creates an [Iterable] instance that wraps the original array returning its elements when being iterated. */
fun ByteArray64.asIterable(): Iterable<Byte> {
    TODO()
}
/** Creates a [Sequence] instance that wraps the original array returning its elements when being iterated. */
fun ByteArray64.asSequence(): Sequence<Byte> {
    TODO()
}

/** Returns true if all elements match the given [predicate]. */
inline fun ByteArray64.all(predicate: (Byte) -> Boolean): Boolean {
    TODO()
}
/** Returns true if at least one element matches the given [predicate]. */
inline fun ByteArray64.any(predicate: (Byte) -> Boolean): Boolean {
    TODO()
}
/** Returns true if no elements match the given [predicate]. */
inline fun ByteArray64.none(predicate: (Byte) -> Boolean): Boolean {
    TODO()
}
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
    TODO()
}
/** Performs the given [action] on each element, providing sequential index with the element. */
inline fun ByteArray64.forEachIndexed(action: (index: Long, Byte) -> Unit) {
    TODO()
}
