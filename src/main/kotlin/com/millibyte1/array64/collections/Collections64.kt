package com.millibyte1.array64.collections

import kotlin.collections.List

/**
 * A generic collection of elements. Methods in this interface support only read-only access to the collection;
 * read/write access is supported through the [MutableCollection] interface.
 * @param E the type of elements contained in the collection. The collection is covariant in its element type.
 */
interface Collection64<out E> : Iterable<E> {
    /** Returns the size of the collection. */
    val size: Long
    /** Checks if the specified element is contained in this collection. */
    operator fun contains(element: @UnsafeVariance E): Boolean
    /** Checks if all elements in the specified collection are contained in this collection. */
    fun containsAll(element: Collection<@UnsafeVariance E>): Boolean
    /** Checks if all elements in the specified collection are contained in this collection. */
    fun containsAll(element: Collection64<@UnsafeVariance E>): Boolean
    /** Returns `true` if this collection is empty (contains no elements), `false` otherwise. */
    fun isEmpty(): Boolean
}
/**
 * A generic collection of elements that supports adding and removing elements.
 * @param E the type of elements contained in the collection. The mutable collection is invariant in its element type.
 */
interface MutableCollection64<E> : Collection64<E>, MutableIterable<E> {
    /**
     * Adds the specified element to the collection.
     * @return `true` if the element has been added, `false` if the collection does not support duplicates
     * and the element is already contained in the collection.
     */
    fun add(element: E): Boolean
    /**
     * Adds all of the elements of the specified collection to this collection.
     * @return `true` if any of the specified elements was added to the collection, `false` if the collection was not modified.
     */
    fun addAll(elements: Collection<E>): Boolean
    /**
     * Adds all of the elements of the specified collection to this collection.
     * @return `true` if any of the specified elements was added to the collection, `false` if the collection was not modified.
     */
    fun addAll(elements: Collection64<E>): Boolean
    /**
     * Removes a single instance of the specified element from this
     * collection, if it is present.
     * @return `true` if the element has been successfully removed; `false` if it was not present in the collection.
     */
    fun remove(element: E): Boolean
    /**
     * Removes all of this collection's elements that are also contained in the specified collection.
     * @return `true` if any of the specified elements was removed from the collection, `false` if the collection was not modified.
     */
    fun removeAll(elements: Collection<E>): Boolean
    /**
     * Removes all of this collection's elements that are also contained in the specified collection.
     * @return `true` if any of the specified elements was removed from the collection, `false` if the collection was not modified.
     */
    fun removeAll(elements: Collection64<E>): Boolean
    /**
     * Retains only the elements in this collection that are contained in the specified collection.
     * @return `true` if any element was removed from the collection, `false` if the collection was not modified.
     */
    fun retainAll(elements: Collection<E>): Boolean
    /**
     * Retains only the elements in this collection that are contained in the specified collection.
     * @return `true` if any element was removed from the collection, `false` if the collection was not modified.
     */
    fun retainAll(elements: Collection64<E>): Boolean
    /** Removes all elements from this collection. */
    fun clear()
}
/**
 * A generic ordered collection of elements. Methods in this interface support only read-only access to the list;
 * read/write access is supported through the [MutableList] interface.
 * @param E the type of elements contained in the list. The list is covariant in its element type.
 */
interface List64<out E> : Collection64<E> {
    /** Returns the element at the specified index in the list. */
    operator fun get(index: Long): E
    /**
     * Returns the index of the first occurrence of the specified element in the list, or -1 if the specified
     * element is not contained in the list.
     */
    fun indexOf(element: @UnsafeVariance E): Long
    /**
     * Returns the index of the last occurrence of the specified element in the list, or -1 if the specified
     * element is not contained in the list.
     */
    fun lastIndexOf(element: @UnsafeVariance E): Long
    /** Returns a list iterator over the elements in this list (in proper sequence). */
    fun listIterator(): ListIterator<E>
    /** Returns a list iterator over the elements in this list (in proper sequence), starting at the specified [index]. */
    fun listIterator(index: Long): ListIterator<E>
    /**
     * Returns a view of the portion of this list between the specified [fromIndex] (inclusive) and [toIndex] (exclusive).
     * The returned list is backed by this list, so non-structural changes in the returned list are reflected in this list, and vice-versa.
     *
     * Structural changes in the base list make the behavior of the view undefined.
     */
    fun subList(fromIndex: Long, toIndex: Long): List64<E>
}
/**
 * A generic ordered collection of elements that supports adding and removing elements.
 * @param E the type of elements contained in the list. The mutable list is invariant in its element type.
 */
interface MutableList64<E> : List64<E>, MutableCollection64<E> {
    /**
     * Replaces the element at the specified position in this list with the specified element.
     * @return the element previously at the specified position.
     */
    operator fun set(index: Long, element: E): E
    /** Inserts an element into the list at the specified [index]. */
    fun add(index: Long, element: E)
    /**
     * Removes an element at the specified [index] from the list.
     * @return the element that has been removed.
     */
    fun removeAt(index: Long): E
}
val Collection64<*>.indices: LongRange
    get() = (0 until size)
