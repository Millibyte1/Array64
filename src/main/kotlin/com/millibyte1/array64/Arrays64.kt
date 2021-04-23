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
/** Performs the given [action] on each element. */
inline fun ByteArray64.forEach(action: (Byte) -> Unit) {
    val iterator = this.iterator()
    while(iterator.hasNext()) action(iterator.next())
}
/** Performs the given [action] on each element. */
inline fun BooleanArray64.forEach(action: (Boolean) -> Unit) {
    val iterator = this.iterator()
    while(iterator.hasNext()) action(iterator.next())
}
/** Performs the given [action] on each element. */
inline fun CharArray64.forEach(action: (Char) -> Unit) {
    val iterator = this.iterator()
    while(iterator.hasNext()) action(iterator.next())
}
/** Performs the given [action] on each element. */
inline fun ShortArray64.forEach(action: (Short) -> Unit) {
    val iterator = this.iterator()
    while(iterator.hasNext()) action(iterator.next())
}
/** Performs the given [action] on each element. */
inline fun IntArray64.forEach(action: (Int) -> Unit) {
    val iterator = this.iterator()
    while(iterator.hasNext()) action(iterator.next())
}
/** Performs the given [action] on each element. */
inline fun LongArray64.forEach(action: (Long) -> Unit) {
    val iterator = this.iterator()
    while(iterator.hasNext()) action(iterator.next())
}
/** Performs the given [action] on each element. */
inline fun FloatArray64.forEach(action: (Float) -> Unit) {
    val iterator = this.iterator()
    while(iterator.hasNext()) action(iterator.next())
}
/** Performs the given [action] on each element. */
inline fun DoubleArray64.forEach(action: (Double) -> Unit) {
    val iterator = this.iterator()
    while(iterator.hasNext()) action(iterator.next())
}
/** Performs the given [action] on each element, providing sequential index with the element. */
inline fun <E> FastArray64<E>.forEachIndexed(action: (index: Long, E) -> Unit) {
    val iterator = this.iterator()
    while(iterator.hasNext()) action(iterator.index, iterator.next())
}
/** Performs the given [action] on each element, providing sequential index with the element. */
inline fun ByteArray64.forEachIndexed(action: (index: Long, Byte) -> Unit) {
    var index = 0L
    val iterator = this.iterator()
    while(iterator.hasNext()) {
        action(index, iterator.next())
        index++
    }
}
/** Performs the given [action] on each element, providing sequential index with the element. */
inline fun BooleanArray64.forEachIndexed(action: (index: Long, Boolean) -> Unit) {
    var index = 0L
    val iterator = this.iterator()
    while(iterator.hasNext()) {
        action(index, iterator.next())
        index++
    }
}
/** Performs the given [action] on each element, providing sequential index with the element. */
inline fun CharArray64.forEachIndexed(action: (index: Long, Char) -> Unit) {
    var index = 0L
    val iterator = this.iterator()
    while(iterator.hasNext()) {
        action(index, iterator.next())
        index++
    }
}
/** Performs the given [action] on each element, providing sequential index with the element. */
inline fun ShortArray64.forEachIndexed(action: (index: Long, Short) -> Unit) {
    var index = 0L
    val iterator = this.iterator()
    while(iterator.hasNext()) {
        action(index, iterator.next())
        index++
    }
}
/** Performs the given [action] on each element, providing sequential index with the element. */
inline fun IntArray64.forEachIndexed(action: (index: Long, Int) -> Unit) {
    var index = 0L
    val iterator = this.iterator()
    while(iterator.hasNext()) {
        action(index, iterator.next())
        index++
    }
}
/** Performs the given [action] on each element, providing sequential index with the element. */
inline fun LongArray64.forEachIndexed(action: (index: Long, Long) -> Unit) {
    var index = 0L
    val iterator = this.iterator()
    while(iterator.hasNext()) {
        action(index, iterator.next())
        index++
    }
}
/** Performs the given [action] on each element, providing sequential index with the element. */
inline fun FloatArray64.forEachIndexed(action: (index: Long, Float) -> Unit) {
    var index = 0L
    val iterator = this.iterator()
    while(iterator.hasNext()) {
        action(index, iterator.next())
        index++
    }
}
/** Performs the given [action] on each element, providing sequential index with the element. */
inline fun DoubleArray64.forEachIndexed(action: (index: Long, Double) -> Unit) {
    var index = 0L
    val iterator = this.iterator()
    while(iterator.hasNext()) {
        action(index, iterator.next())
        index++
    }
}
/** Performs the given [action] on each element within the [range]. */
inline fun <E> Array64<E>.forEachInRange(range: LongRange, action: (E) -> Unit) {
    if(range.first < 0 || range.last >= this.size) throw NoSuchElementException()
    var index = range.first
    for(element in this.iterator(range.first)) {
        action(element)
        index++
    }
}
/** Performs the given [action] on each element within the [range]. */
inline fun ByteArray64.forEachInRange(range: LongRange, action: (Byte) -> Unit) {
    if(range.first < 0 || range.last >= this.size) throw NoSuchElementException()
    var index = range.first
    for(element in this.iterator(range.first)) {
        action(element)
        index++
    }
}
/** Performs the given [action] on each element within the [range]. */
inline fun BooleanArray64.forEachInRange(range: LongRange, action: (Boolean) -> Unit) {
    if(range.first < 0 || range.last >= this.size) throw NoSuchElementException()
    var index = range.first
    for(element in this.iterator(range.first)) {
        action(element)
        index++
    }
}
/** Performs the given [action] on each element within the [range]. */
inline fun CharArray64.forEachInRange(range: LongRange, action: (Char) -> Unit) {
    if(range.first < 0 || range.last >= this.size) throw NoSuchElementException()
    var index = range.first
    for(element in this.iterator(range.first)) {
        action(element)
        index++
    }
}
/** Performs the given [action] on each element within the [range]. */
inline fun ShortArray64.forEachInRange(range: LongRange, action: (Short) -> Unit) {
    if(range.first < 0 || range.last >= this.size) throw NoSuchElementException()
    var index = range.first
    for(element in this.iterator(range.first)) {
        action(element)
        index++
    }
}
/** Performs the given [action] on each element within the [range]. */
inline fun IntArray64.forEachInRange(range: LongRange, action: (Int) -> Unit) {
    if(range.first < 0 || range.last >= this.size) throw NoSuchElementException()
    var index = range.first
    for(element in this.iterator(range.first)) {
        action(element)
        index++
    }
}
/** Performs the given [action] on each element within the [range]. */
inline fun LongArray64.forEachInRange(range: LongRange, action: (Long) -> Unit) {
    if(range.first < 0 || range.last >= this.size) throw NoSuchElementException()
    var index = range.first
    for(element in this.iterator(range.first)) {
        action(element)
        index++
    }
}
/** Performs the given [action] on each element within the [range]. */
inline fun FloatArray64.forEachInRange(range: LongRange, action: (Float) -> Unit) {
    if(range.first < 0 || range.last >= this.size) throw NoSuchElementException()
    var index = range.first
    for(element in this.iterator(range.first)) {
        action(element)
        index++
    }
}
/** Performs the given [action] on each element within the [range]. */
inline fun DoubleArray64.forEachInRange(range: LongRange, action: (Double) -> Unit) {
    if(range.first < 0 || range.last >= this.size) throw NoSuchElementException()
    var index = range.first
    for(element in this.iterator(range.first)) {
        action(element)
        index++
    }
}
/** Performs the given [action] on each element within the [range], providing sequential index with the element. */
inline fun <E> Array64<E>.forEachInRangeIndexed(range: LongRange, action: (index: Long, E) -> Unit) {
    if(range.first < 0 || range.last >= this.size) throw NoSuchElementException()
    var index = range.first
    for(element in this.iterator(range.first)) {
        action(index, element)
        index++
    }
}
/** Performs the given [action] on each element within the [range], providing sequential index with the element. */
inline fun ByteArray64.forEachInRangeIndexed(range: LongRange, action: (index: Long, Byte) -> Unit) {
    if(range.first < 0 || range.last >= this.size) throw NoSuchElementException()
    var index = range.first
    for(element in this.iterator(range.first)) {
        action(index, element)
        index++
    }
}
/** Performs the given [action] on each element within the [range], providing sequential index with the element. */
inline fun BooleanArray64.forEachInRangeIndexed(range: LongRange, action: (index: Long, Boolean) -> Unit) {
    if(range.first < 0 || range.last >= this.size) throw NoSuchElementException()
    var index = range.first
    for(element in this.iterator(range.first)) {
        action(index, element)
        index++
    }
}
/** Performs the given [action] on each element within the [range], providing sequential index with the element. */
inline fun CharArray64.forEachInRangeIndexed(range: LongRange, action: (index: Long, Char) -> Unit) {
    if(range.first < 0 || range.last >= this.size) throw NoSuchElementException()
    var index = range.first
    for(element in this.iterator(range.first)) {
        action(index, element)
        index++
    }
}
/** Performs the given [action] on each element within the [range], providing sequential index with the element. */
inline fun ShortArray64.forEachInRangeIndexed(range: LongRange, action: (index: Long, Short) -> Unit) {
    if(range.first < 0 || range.last >= this.size) throw NoSuchElementException()
    var index = range.first
    for(element in this.iterator(range.first)) {
        action(index, element)
        index++
    }
}
/** Performs the given [action] on each element within the [range], providing sequential index with the element. */
inline fun IntArray64.forEachInRangeIndexed(range: LongRange, action: (index: Long, Int) -> Unit) {
    if(range.first < 0 || range.last >= this.size) throw NoSuchElementException()
    var index = range.first
    for(element in this.iterator(range.first)) {
        action(index, element)
        index++
    }
}
/** Performs the given [action] on each element within the [range], providing sequential index with the element. */
inline fun LongArray64.forEachInRangeIndexed(range: LongRange, action: (index: Long, Long) -> Unit) {
    if(range.first < 0 || range.last >= this.size) throw NoSuchElementException()
    var index = range.first
    for(element in this.iterator(range.first)) {
        action(index, element)
        index++
    }
}
/** Performs the given [action] on each element within the [range], providing sequential index with the element. */
inline fun FloatArray64.forEachInRangeIndexed(range: LongRange, action: (index: Long, Float) -> Unit) {
    if(range.first < 0 || range.last >= this.size) throw NoSuchElementException()
    var index = range.first
    for(element in this.iterator(range.first)) {
        action(index, element)
        index++
    }
}
/** Performs the given [action] on each element within the [range], providing sequential index with the element. */
inline fun DoubleArray64.forEachInRangeIndexed(range: LongRange, action: (index: Long, Double) -> Unit) {
    if(range.first < 0 || range.last >= this.size) throw NoSuchElementException()
    var index = range.first
    for(element in this.iterator(range.first)) {
        action(index, element)
        index++
    }
}

/** Creates a [Sequence] instance that wraps the original array returning its elements when being iterated. */
fun FastByteArray64.asSequence(): Sequence<Byte> = Sequence { this.iterator() }

/** Returns true if all elements match the given [predicate]. */
inline fun FastByteArray64.all(predicate: (Byte) -> Boolean): Boolean {
    this.forEach { e -> if(!predicate(e)) return false }
    return true
}
/** Returns true if at least one element matches the given [predicate]. */
inline fun FastByteArray64.any(predicate: (Byte) -> Boolean): Boolean {
    this.forEach { e -> if(predicate(e)) return true }
    return false
}
/** Returns true if no elements match the given [predicate]. */
inline fun FastByteArray64.none(predicate: (Byte) -> Boolean): Boolean {
    this.forEach { e -> if(predicate(e)) return false }
    return true
}