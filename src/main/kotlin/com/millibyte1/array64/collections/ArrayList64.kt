package com.millibyte1.array64.collections

import com.millibyte1.array64.Array64
import com.millibyte1.array64.makeTyped2DArray
import com.millibyte1.array64.makeTypedArray64
import it.unimi.dsi.fastutil.BigArrays

//TODO: AbstractList64, AbstractCollection64, in-place resizing, misc. optimizations
class ArrayList64<E> : MutableList64<E> {

    var capacity: Long
        private set
    override var size: Long
        private set
    @PublishedApi internal var array: Array<Array<Any?>>

    constructor(initialCapacity: Long = 1) {
        this.capacity = initialCapacity
        this.size = 0
        this.array = makeTyped2DArray(initialCapacity) { null }
    }
    constructor(array: Array<E>) {
        this.capacity = array.size as Long
        this.size = array.size as Long
        this.array = BigArrays.wrap(array)
    }
    constructor(array: Array64<E>) {
        this.capacity = array.size
        this.size = array.size
        this.array = BigArrays.copy(array.array)
    }
    constructor(array: Array<Array<E>>) {
        this.array = BigArrays.copy(array)
        this.capacity = BigArrays.length(this.array)
        this.size = this.capacity
    }

    // basic access operations

    override fun get(index: Long): E {
        if(index >= this.size || index < 0) throw NoSuchElementException()
        return BigArrays.get(array, index) as E
    }
    override fun set(index: Long, element: E): E {
        if(index >= this.size || index < 0) throw NoSuchElementException()
        val oldElement = this[index]
        BigArrays.set(array, index, element)
        return oldElement
    }
    override fun contains(element: E): Boolean = indexOf(element) != -1L
    override fun containsAll(elements: Collection<E>): Boolean = elements.all { element -> indexOf(element) != -1L }
    override fun containsAll(elements: Collection64<E>): Boolean = elements.all { element -> indexOf(element) != -1L }
    override fun isEmpty(): Boolean = size == 0L

    override fun add(element: E): Boolean {
        ensureCapacity(capacity + 1)
        this[size++] = element
        return false
    }
    override fun remove(element: E): Boolean {
        val index = indexOf(element)
        if(index == -1L) return false
        removeAt(index)
        return true
    }
    override fun add(index: Long, element: E) {
        TODO("Not yet implemented")
    }
    override fun removeAt(index: Long): E {
        TODO("Not yet implemented")
    }

    // aggregate operations

    override fun addAll(elements: Collection<E>): Boolean {
        ensureCapacity(capacity + elements.size)
        elements.forEach { element -> add(element) }
        return elements.isNotEmpty()
    }
    override fun addAll(elements: Collection64<E>): Boolean {
        ensureCapacity(capacity + elements.size)
        elements.forEach { element -> add(element) }
        return elements.isNotEmpty()
    }
    override fun removeAll(elements: Collection<E>): Boolean {
        var numRemovals = 0
        val retval: Array<Array<Any?>> = makeTyped2DArray(this.capacity) { null }
        var index = 0L
        //TODO: use 2D iteration rather than set for cache performance
        for(element in this) {
            if(elements.contains(element)) numRemovals++
            else BigArrays.set(retval, index++, element)
        }
        this.array = retval
        this.size -= numRemovals
        return numRemovals != 0
    }
    override fun removeAll(elements: Collection64<E>): Boolean {
        var numRemovals = 0
        val retval: Array<Array<Any?>> = makeTyped2DArray(this.capacity) { null }
        var index = 0L
        //TODO: use 2D iteration rather than set for cache performance
        for(element in this) {
            if(elements.contains(element)) numRemovals++
            else BigArrays.set(retval, index++, element)
        }
        this.array = retval
        this.size -= numRemovals
        return numRemovals != 0
    }
    override fun retainAll(elements: Collection<E>): Boolean {
        var numRemovals = 0
        val retval: Array<Array<Any?>> = makeTyped2DArray(this.capacity) { null }
        var index = 0L
        //TODO: use 2D iteration rather than set for cache performance
        for(element in this) {
            if(!elements.contains(element)) numRemovals++
            else BigArrays.set(retval, index++, element)
        }
        this.array = retval
        this.size -= numRemovals
        return numRemovals != 0
    }
    override fun retainAll(elements: Collection64<E>): Boolean {
        var numRemovals = 0
        val retval: Array<Array<Any?>> = makeTyped2DArray(this.capacity) { null }
        var index = 0L
        //TODO: use 2D iteration rather than set for cache performance
        for(element in this) {
            if(!elements.contains(element)) numRemovals++
            else BigArrays.set(retval, index++, element)
        }
        this.array = retval
        this.size -= numRemovals
        return numRemovals != 0
    }

    override fun clear() {
        this.capacity = 1
        this.size = 0
        this.array = makeTyped2DArray(1) { null }
    }

    override fun indexOf(element: E): Long {
        var index = 0L
        //calculates outer and inner indices of last index
        val lastOuterIndex = BigArrays.segment(this.lastIndex)
        val lastInnerIndex = BigArrays.displacement(this.lastIndex)
        //iterates through the real elements of this array, returning when the target element is found
        for(outerIndex in 0..lastOuterIndex) {
            val inner = array[outerIndex]
            val currentLastInnerIndex = if(outerIndex == lastOuterIndex) lastInnerIndex else inner.lastIndex
            for(innerIndex in 0..currentLastInnerIndex) {
                if(inner[innerIndex] == element) return index
                index++
            }
        }
        return -1
    }

    override fun lastIndexOf(element: E): Long {
        var index = this.lastIndex
        //calculates outer and inner indices of last index
        val lastOuterIndex = BigArrays.segment(this.lastIndex)
        val lastInnerIndex = BigArrays.displacement(this.lastIndex)
        //iterates through the real elements of this array in reverse order, returning when the target element is found
        for(outerIndex in (0..lastOuterIndex).reversed()) {
            val inner = array[outerIndex]
            val currentLastInnerIndex = if(outerIndex == lastOuterIndex) lastInnerIndex else inner.lastIndex
            for(innerIndex in (0..currentLastInnerIndex).reversed()) {
                if(inner[innerIndex] == element) return index
                index++
            }
        }
        return -1
    }

    override fun iterator(): MutableIterator<E> {
        TODO("Not yet implemented")
    }
    override fun listIterator(): ListIterator<E> = listIterator(0)
    override fun listIterator(index: Long): ListIterator<E> {
        TODO("Not yet implemented")
    }

    override fun subList(fromIndex: Long, toIndex: Long): ArrayList64<E> {
        val retval = ArrayList64<E>(toIndex - fromIndex);
        forEachInRange(fromIndex until toIndex) { e -> retval.add(e) }
        return retval
    }

    /** Trims the capacity of this ArrayList64 to exactly the current size. */
    fun trimToSize() {
        // TODO: In-place reimplementation. This takes space O(capacity + minCapacity) rather than O(segmentSize + minCapacity).
        this.array = BigArrays.trim(array, this.size)
        this.capacity = this.size
    }
    /**
     * Grows the capacity of this ArrayList64 to exactly the specified [capacity],
     * or does nothing if the current capacity is sufficient.
     * @param capacity the desired capacity
     */
    fun grow(capacity: Long) {
        // TODO: In-place reimplementation. This takes space O(capacity + minCapacity) rather than O(segmentSize + minCapacity).
        if(this.capacity >= capacity) return
        this.array = BigArrays.ensureCapacity(array, capacity)
        this.capacity = capacity
    }
    /*
     * Shrinks the capacity of this ArrayList64 to exactly the specified [capacity],
     * discarding any elements that are outside of the new bounds.
     * @param capacity the desired capacity
     */
    //fun shrink(capacity: Long)

    /** Performs the given [action] on each element. */
    inline fun forEach(action: (E) -> Unit) = forEachInRange(0 until this.size, action)
    /** Performs the given [action] on each element, providing sequential index with the element. */
    inline fun forEachIndexed(action: (index: Long, E) -> Unit) = forEachInRangeIndexed(0 until this.size, action)
    /** Performs the given [action] on each element within the [range]. */
    inline fun forEachInRange(range: LongRange, action: (E) -> Unit) {
        if(range.first < 0 || range.last >= this.size) throw NoSuchElementException()
        //Calculates the indices of the first and last elements in the range
        val outerIndexOfFirst = BigArrays.segment(range.first)
        val outerIndexOfLast = BigArrays.segment(range.last)
        val innerIndexOfFirst = (range.first - (BigArrays.SEGMENT_SIZE * outerIndexOfFirst)).toInt()
        val innerIndexOfLast = (range.last - (BigArrays.SEGMENT_SIZE * outerIndexOfLast)).toInt()
        //Performs the iteration
        for(outerIndex in outerIndexOfFirst..outerIndexOfLast) {
            val inner = array[outerIndex]
            val startingInnerIndex = if(outerIndex == outerIndexOfFirst) innerIndexOfFirst else 0
            val endingInnerIndex = if(outerIndex == outerIndexOfLast) innerIndexOfLast else BigArrays.SEGMENT_SIZE - 1
            for(innerIndex in startingInnerIndex..endingInnerIndex) {
                action(inner[innerIndex] as E)
            }
        }
    }
    /** Performs the given [action] on each element within the [range], providing sequential index with the element. */
    inline fun forEachInRangeIndexed(range: LongRange, action: (index: Long, E) -> Unit) {
        if(range.first < 0 || range.last >= this.size) throw NoSuchElementException()
        //Calculates the indices of the first and last elements in the range
        val outerIndexOfFirst = BigArrays.segment(range.first)
        val outerIndexOfLast = BigArrays.segment(range.last)
        val innerIndexOfFirst = (range.first - (BigArrays.SEGMENT_SIZE * outerIndexOfFirst)).toInt()
        val innerIndexOfLast = (range.last - (BigArrays.SEGMENT_SIZE * outerIndexOfLast)).toInt()
        //Performs the iteration
        var index = range.first
        for(outerIndex in outerIndexOfFirst..outerIndexOfLast) {
            val inner = array[outerIndex]
            val startingInnerIndex = if(outerIndex == outerIndexOfFirst) innerIndexOfFirst else 0
            val endingInnerIndex = if(outerIndex == outerIndexOfLast) innerIndexOfLast else BigArrays.SEGMENT_SIZE - 1
            for(innerIndex in startingInnerIndex..endingInnerIndex) {
                action(index, inner[innerIndex] as E)
                index++
            }
        }
    }

    /**
     * Increases the capacity of this ArrayList64 instance, if necessary, to ensure that it can hold at least the number
     * of elements specified by the minimum capacity argument.
     * @param minCapacity the desired minimum capacity
     */
    private fun ensureCapacity(minCapacity: Long) {
        // TODO: In-place reimplementation. This takes space O(capacity + minCapacity) rather than O(segmentSize + minCapacity).
        this.array = BigArrays.ensureCapacity(array, minCapacity)
        this.capacity = minCapacity
    }

    companion object {

        /**
         * Creates a new ArrayList64 of the specified [size], with all elements initialized according to the given [init] function.
         *
         * This is a pseudo-constructor. Reified type parameters are needed for generic 2D array creation but aren't possible
         * with real constructors, so an inlined operator function is used to act like a constructor.
         */
        inline operator fun <reified E> invoke(size: Long, crossinline init: (Long) -> E): ArrayList64<E> = ArrayList64(makeTyped2DArray(size, init))
        /**
         * Creates a new ArrayList64 containing the elements of the given [collection].
         *
         * This is a pseudo-constructor. Reified type parameters are needed for generic 2D array creation but aren't possible
         * with real constructors, so an inlined operator function is used to act like a constructor.
         */
        inline operator fun <reified E> invoke(collection: Collection<out E>): ArrayList64<E> = ArrayList64(collection.toTypedArray())
        /**
         * Creates a new ArrayList64 containing the elements of the given [collection].
         *
         * This is a pseudo-constructor. Reified type parameters are needed for generic 2D array creation but aren't possible
         * with real constructors, so an inlined operator function is used to act like a constructor.
         */
        inline operator fun <reified E> invoke(collection: Collection64<out E>): ArrayList64<E> = TODO()
    }
}