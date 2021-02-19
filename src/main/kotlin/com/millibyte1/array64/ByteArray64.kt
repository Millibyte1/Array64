package com.millibyte1.array64

/*
 * The terms '32-bit indexing' and '64-bit indexing' in the context of arrays on the JVM are slightly misleading; because the JVM uses 32-bit
 * SIGNED integers for indexing, only 31 bits are actually used for indexing, so the expected maximum size of an array is 2^31 - 1. In actuality
 * the maximum array size is implementation dependent and is generally around 2^31 - 5.
 *
 * In order to calculate the inner and outer indices and sizes using only a single bitwise operation for each, for the max size of the
 * partial arrays, we need to use a power of 2 that can fit in a single unsigned 32-bit integer, so we end up with 2^30 as the max size
 * of both the inner and outer arrays.
 *
 * For indexing, assuming the index is in bounds:
 * Bits (0-29) constitute the inner index.
 * Bits (30-59) the outer index.
 * Bits (60-63) are unused in calculating partial indexes.
 *
 * For sizing, assuming the size is in bounds:
 * Let bits (0-29) be INNER. If INNER = 0, then the inner size is 2^30, else the inner size is INNER.
 * Let bits (30-59) be OUTER. If OUTER = 0, then the outer size is 2^30, else the outer size is OUTER + 1.
 * Bits (60-63) are unused in calculating partial sizes.
 *
 * Bit 60 is 1 if the outer (and total) size is the maximum, just like bit 30 is 1 if the inner size is the maximum.
 * However it is not used for calculating partial sizes, whereas bit 30 factors into the outer size.
 *
 * The following chart shows the physical bit layout of an array index or size:
 *
 * |======================================================================================================================================|
 * |U                               L                                                                                                     |
 * |0111111111111111111111111111111111111111111111111111111111111111 = max value of signed 64-bit integer = 2^63 - 1                      |
 * |--------------------------------------------------------------------------------------------------------------------------------------|
 * |U                               L                                                                                                     |
 * |                                00111111111111111111111111111111 = max partial index (inner)          = 2^30 - 1 = 1073741823         |
 * |U                               L                                                                                                     |
 * |  00111111111111111111111111111111                               = max partial index (outer)          = 2^30 - 1 = 1073741823         |
 * |U                               L                                                                                                     |
 * |0000111111111111111111111111111111111111111111111111111111111111 = max total index                    = 2^60 - 1 = 1152921504606846975|
 * |--------------------------------------------------------------------------------------------------------------------------------------|
 * |U                               L                                                                                                     |
 * |                                01000000000000000000000000000000 = max partial size (inner)           = 2^30     = 1073741824         |
 * |U                               L                                                                                                     |
 * |  01000000000000000000000000000000                               = max partial size (outer)           = 2^30     = 1073741824         |
 * |U                               L                                                                                                     |
 * |0001000000000000000000000000000000000000000000000000000000000000 = max total size                     = 2^60     = 1152921504606846976|
 * |--------------------------------------------------------------------------------------------------------------------------------------|
 * |U                               L                                                                                                     |
 * |000                                                              = always 0 in size                                                   |
 * |U                               L                                                                                                     |
 * |0000                                                             = always 0 in index                                                  |
 * |======================================================================================================================================|
 *
 */
/**
 * A primitive ByteArray re-implementation using 64-bit indexing.
 *
 * Internally uses an Array<ByteArray>, so this is slightly less space efficient and significantly
 * less time efficient than a standard library array.
 *
 * @property size Returns the number of elements in the array.
 *
 * @constructor
 * Creates a new array of the specified [size], where each element is calculated by calling the specified [init] function.
 */
class ByteArray64 {

    val size: Long
    private val data: Array<ByteArray>

    constructor(size: Long, init: (Long) -> Byte) {
        if(size <= 0 || size > MAX_SIZE) throw IllegalArgumentException("Invalid size provided.")
        this.size = size
        val outerSize = getOuterSize(size)
        val innerSize = getInnerSize(size)
        //allocates the arrays
        this.data = Array(outerSize) { outerIndex -> if(outerIndex == outerSize - 1) ByteArray(innerSize) else ByteArray(MAX_PARTIAL_SIZE.toInt()) }
        //applies the initializer function to every index
        for(index in 0 until size) {
            val outerIndex = getOuterIndex(index)
            val innerIndex = getInnerIndex(index)
            data[outerIndex][innerIndex] = init(index)
        }
    }
    /** Creates a new array of the specified [size], with all elements initialized to zero. */
    constructor(size: Long) : this(size, { 0 })
    /** Creates a copy of the given array */
    constructor(array: ByteArray64) : this(array.size, { i -> array[i] })
    /** Creates a new array from the given standard library array */
    constructor(array: ByteArray) : this(array.size.toLong(), { i -> array[i.toInt()] })
    /** Creates a new array from the given ordered list of standard library arrays */
    constructor(arrays: List<ByteArray>) : this(
        arrays.fold(0) { sum, array -> sum + array.size },
        { index -> arrays[getOuterIndex(index)][getInnerIndex(index)] }
    )

    fun copy(): ByteArray64 = ByteArray64(this)

    infix fun contentEquals(other: Any?): Boolean {
        if(this === other) return true
        if(other !is ByteArray64) return false
        return data.contentDeepEquals(other.data)
    }

    /** Returns the array at the given [index]. This method can be called using the index operator. */
    operator fun get(index: Long): Byte {
        if(index >= this.size || index < 0) throw NoSuchElementException()
        //we need to access the lowest 31 bits as the inner and the next lowest 31 bits as the outer, ignoring the 2 uppermost bits
        val outerIndex = getOuterIndex(index)
        val innerIndex = getInnerIndex(index)
        return data[outerIndex][innerIndex]
    }
    /** Sets the element at the given [index] to the given [value]. This method can be called using the index operator. */
    operator fun set(index: Long, value: Byte) {
        if(index >= this.size || index < 0) throw NoSuchElementException()
        val outerIndex = getOuterIndex(index)
        val innerIndex = getInnerIndex(index)
        data[outerIndex][innerIndex] = value
    }

    /** Creates an iterator over the elements of the array. */
    operator fun iterator(): ByteIterator {
        return ByteArray64Iterator(this, 0)
    }

    companion object {

        const val MAX_SIZE = (1L shl 60)
        const val MAX_INDEX = MAX_SIZE - 1

        internal const val MAX_PARTIAL_SIZE = (1L shl 30)
        internal const val MAX_PARTIAL_INDEX = MAX_PARTIAL_SIZE - 1

        /** Applies a bitmask to the index to extract just the 30 INNER bits padded by 0s as the inner index. Assumes the index is in bounds. */
        internal fun getInnerIndex(index: Long): Int = (index and MAX_PARTIAL_INDEX).toInt()
        /** Shifts the index right 30 bits to get just the 30 OUTER bits padded by 0s as the outer index. Assumes the index is in bounds. */
        internal fun getOuterIndex(index: Long): Int = (index shr 30).toInt()

        /** Grabs the size of the inner array. Assumes the total size is in bounds. */
        internal fun getInnerSize(size: Long): Int {
            val innerBits = getInnerIndex(size)
            return if(innerBits == 0) MAX_PARTIAL_SIZE.toInt() else innerBits
        }
        /** Grabs the size of the outer array. Assumes the total size is in bounds. */
        internal fun getOuterSize(size: Long): Int {
            val outerBits = getOuterIndex(size)
            return if(outerBits == 0) MAX_PARTIAL_SIZE.toInt() else outerBits + 1
        }
    }
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
    for(i in this.indices) action(this[i])
}
/** Performs the given [action] on each element, providing sequential index with the element. */
inline fun ByteArray64.forEachIndexed(action: (index: Long, Byte) -> Unit) {
    for(i in this.indices) action(i, this[i])
}
