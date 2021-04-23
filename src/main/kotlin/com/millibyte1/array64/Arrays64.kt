package com.millibyte1.array64

import it.unimi.dsi.fastutil.BigArrays

/** Creates a generic array instance with the given size and initializer. */
inline fun <reified E> makeTypedArray(size: Int, init: (Int) -> E): Array<E> = Array(size) { i -> init(i) }
/** Creates a generic [FastArray64] with the given size and initializer. */
inline fun <reified E> makeTypedArray64(size: Long, crossinline init: (Long) -> E): FastArray64<E> = FastArray64(makeTyped2DArray(size, init))
/** Creates a generic 2D array with the given size and initializer and inner arrays filled up to [BigArrays.SEGMENT_SIZE] */
inline fun <reified E> makeTyped2DArray(size: Long, crossinline init: (Long) -> E): Array<Array<E>> {
    //determines the number of completely filled inner arrays and the size of the last, unfilled inner array (0 if all arrays are full)
    val fullArrays = (size / BigArrays.SEGMENT_SIZE).toInt()
    val lastInnerSize = (size % BigArrays.SEGMENT_SIZE).toInt()
    //creates an array storing the size of the inner array at each index
    val innerSizes =
        if(lastInnerSize == 0) Array(fullArrays) { BigArrays.SEGMENT_SIZE }
        else Array(fullArrays + 1) { index -> if(index == fullArrays) lastInnerSize else BigArrays.SEGMENT_SIZE }
    //creates an equivalent initializer which takes inner and outer indices instead of just a single long index
    val fakeInit: (Int, Int) -> E = { outerIndex, innerIndex -> init((outerIndex * BigArrays.SEGMENT_SIZE + innerIndex).toLong()) }
    //creates and returns a 2D array using the fakeInit function
    return makeTyped2DArray(innerSizes, fakeInit)
}
/** Creates a generic 2D array with sizes defined by [innerSizes] and elements initialized according to the provided [init] function. */
inline fun <reified E> makeTyped2DArray(innerSizes: Array<Int>, init: (Int, Int) -> E): Array<Array<E>> {
    return Array(innerSizes.size) { outerIndex ->
        Array(innerSizes[outerIndex]) { innerIndex ->
            init(outerIndex, innerIndex)
        }
    }
}

/** Returns the last valid index for the array. */
val Array64<*>.lastIndex: Long
    get() = size - 1
/** Returns the range of valid indices for the array. */
val Array64<*>.indices: LongRange
    get() = LongRange(0, lastIndex)

/**
 * Returns true if the two specified arrays are structurally equal to one another,
 * i.e. contain the same number of elements in the same order.
 *
 * The elements are compared for equality with the [Any.equals] function.
 */
infix fun <E> Array64<E>.contentEquals(other: Array64<E>): Boolean {
    if(this === other) return true
    if(size != other.size) return false
    val thisIterator = this.iterator()
    val otherIterator = other.iterator()
    while(thisIterator.hasNext()) if(thisIterator.next() != otherIterator.next()) return false
    return true
}

/** Performs the given [action] on each element. */
inline fun <E> Array64<E>.forEach(action: (E) -> Unit) {
    val iterator = this.iterator()
    while(iterator.hasNext()) action(iterator.next())
}
/** Performs the given [action] on each element, providing sequential index with the element. */
inline fun <E> Array64<E>.forEachIndexed(action: (index: Long, E) -> Unit) {
    val iterator = this.iterator()
    while(iterator.hasNext()) action(iterator.index, iterator.next())
}
/** Performs the given [action] on each element within the [range]. */
inline fun <E> Array64<E>.forEachInRange(range: LongRange, action: (E) -> Unit) {
    if(range.first < 0 || range.last >= this.size) throw NoSuchElementException()
    val iterator = this.iterator(range.first)
    while(iterator.index <= range.last) action(iterator.next())
}
/** Performs the given [action] on each element within the [range], providing sequential index with the element. */
inline fun <E> Array64<E>.forEachInRangeIndexed(range: LongRange, action: (index: Long, E) -> Unit) {
    if(range.first < 0 || range.last >= this.size) throw NoSuchElementException()
    val iterator = this.iterator(range.first)
    while(iterator.index <= range.last) action(iterator.index, iterator.next())
}

/** Creates a [Sequence] instance that wraps the original array returning its elements when being iterated. */
fun <E> Array64<E>.asSequence(): Sequence<E> = Sequence { this.iterator() }

/** Returns true if all elements match the given [predicate]. */
inline fun <E> Array64<E>.all(predicate: (E) -> Boolean): Boolean {
    this.forEach { e -> if(!predicate(e)) return false }
    return true
}
/** Returns true if at least one element matches the given [predicate]. */
inline fun <E> Array64<E>.any(predicate: (E) -> Boolean): Boolean {
    this.forEach { e -> if(predicate(e)) return true }
    return false
}
/** Returns true if no elements match the given [predicate]. */
inline fun <E> Array64<E>.none(predicate: (E) -> Boolean): Boolean {
    this.forEach { e -> if(predicate(e)) return false }
    return true
}