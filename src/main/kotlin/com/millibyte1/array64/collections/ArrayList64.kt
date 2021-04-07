package com.millibyte1.array64.collections

import com.millibyte1.array64.Array64

class ArrayList64<E> : MutableList64<E> {

    var capacity: Long
        private set
    override var size: Long
        private set
    private val array: Array64<Any?>

    constructor(initialCapacity: Long = 1) {
        capacity = initialCapacity
        size = 0
        array = Array64<Any?>(initialCapacity) { null }
    }
    constructor(collection: Collection<out E>) {
        capacity = collection.size.toLong()
        size = collection.size.toLong()
        array = Array64(collection.toTypedArray())
    }
    constructor(collection: Collection64<out E>) {
        capacity = collection.size
        size = collection.size
        //TODO
        array = Array64(5) { collection.first() }
        //array = Array64(collection.toTypedArray())
    }

    override fun iterator(): MutableIterator<E> {
        TODO("Not yet implemented")
    }

    override fun contains(element: E): Boolean {
        TODO("Not yet implemented")
    }

    override fun containsAll(element: Collection<E>): Boolean {
        TODO("Not yet implemented")
    }

    override fun containsAll(element: Collection64<E>): Boolean {
        TODO("Not yet implemented")
    }

    override fun isEmpty(): Boolean {
        TODO("Not yet implemented")
    }

    override fun add(element: E): Boolean {
        TODO("Not yet implemented")
    }

    override fun addAll(elements: Collection<E>): Boolean {
        TODO("Not yet implemented")
    }

    override fun addAll(elements: Collection64<E>): Boolean {
        TODO("Not yet implemented")
    }

    override fun remove(element: E): Boolean {
        TODO("Not yet implemented")
    }

    override fun removeAll(elements: Collection<E>): Boolean {
        TODO("Not yet implemented")
    }

    override fun removeAll(elements: Collection64<E>): Boolean {
        TODO("Not yet implemented")
    }

    override fun retainAll(elements: Collection<E>): Boolean {
        TODO("Not yet implemented")
    }

    override fun retainAll(elements: Collection64<E>): Boolean {
        TODO("Not yet implemented")
    }

    override fun clear() {
        TODO("Not yet implemented")
    }

    override fun get(index: Long): E {
        TODO("Not yet implemented")
    }

    override fun indexOf(element: E): Long {
        TODO("Not yet implemented")
    }

    override fun lastIndexOf(element: E): Long {
        TODO("Not yet implemented")
    }

    override fun listIterator(): ListIterator<E> {
        TODO("Not yet implemented")
    }

    override fun listIterator(index: Long): ListIterator<E> {
        TODO("Not yet implemented")
    }

    override fun subList(fromIndex: Long, toIndex: Long): List64<E> {
        TODO("Not yet implemented")
    }

    override fun set(index: Long, element: E): E {
        TODO("Not yet implemented")
    }

    override fun add(index: Long, element: E) {
        TODO("Not yet implemented")
    }

    override fun removeAt(index: Long): E {
        TODO("Not yet implemented")
    }

}