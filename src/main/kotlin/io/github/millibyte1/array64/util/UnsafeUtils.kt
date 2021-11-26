@file:JvmName("UnsafeUtils")
package io.github.millibyte1.array64.util

import sun.misc.Unsafe

val unsafe: Unsafe = run {
    val f = Unsafe::class.java.getDeclaredField("theUnsafe")
    f.trySetAccessible()
    return@run f.get(null) as Unsafe
}

/**
 * Gets the value of Unsafe.arrayBaseOffset() for an array of type [E].
 * @param typeInstance an instance of type E (necessary in order to supply the Array<E> class)
 * @return the value of arrayBaseOffset()
 */
inline fun <reified E> getArrayBaseOffset(typeInstance: E): Int {
    val arrayClass = Array(1){typeInstance}::class.java
    return unsafe.arrayBaseOffset(arrayClass)
}

/**
 * Gets the value of Unsafe.arrayIndexScale() for an array of type [E].
 * @param typeInstance an instance of type E (necessary in order to supply the Array<E> class to Unsafe)
 * @return the value of arrayIndexScale()
 */
inline fun <reified E> getArrayIndexScale(typeInstance: E): Int {
    val arrayClass = Array(1){typeInstance}::class.java
    return unsafe.arrayIndexScale(arrayClass)
}

/**
 * Calculates the byte offset of an element within an array.
 * @param index the index of the element
 * @param shift the number of bits to shift the index left to calculate its offset
 * @param base the base offset of the array type
 */
inline fun byteOffset(index: Int, shift: Int, base: Int): Long = (index.toLong() shl shift) + base

/** Returns true if the system this JVM is running on is Big-Endian, otherwise false */
fun systemIsBigEndian(): Boolean = (java.nio.ByteOrder.nativeOrder() == java.nio.ByteOrder.BIG_ENDIAN)


/**
 * Performs a CAS operation on primitive Bytes using compareAndSwapInt and bitmasks
 * (refer to the OpenJDK implementation of sun.misc.Unsafe to decipher the magic).
 * @param o the base array or other object
 * @param offset the offset of the element from [o]
 * @param expected the expected value
 * @param new the value to set the element to
 * @return true if the element was able to be updated, otherwise false
 */
fun compareAndSwapByte(o: Any, offset: Long, expected: Byte, new: Byte): Boolean {
    val shift: Int = if(systemIsBigEndian()) (24 - (offset and 3.toLong()) shl 3).toInt() else ((offset and 3.toLong()) shl 3).toInt()
    val mask: Int = 0xFF shl shift
    val maskedExpected = (expected.toInt() and 0xFF) shl shift
    val maskedNew = (new.toInt() and 0xFF) shl shift

    var word: Int
    val wordOffset: Long = offset and 3.inv().toLong() // calculates the offset of the word this byte is in
    do {
        word = unsafe.getIntVolatile(o, wordOffset)
        if(word and mask != maskedExpected) return false
    }
    while(!unsafe.compareAndSwapInt(o, wordOffset, word, (word and mask.inv()) or maskedNew))
    return true
}
/**
 * Sets the value at the offset from [o] to [new] and returns the old value. Atomic.
 * @param o the base array or other object
 * @param offset the offset of the element from [o]
 * @param new the value to set the element to
 * @return the old value of the element
 */
fun getAndSetByte(o: Any, offset: Long, new: Byte): Byte {
    var old: Byte
    do old = unsafe.getByteVolatile(o, offset) while(!compareAndSwapByte(o, offset, old, new))
    return old
}