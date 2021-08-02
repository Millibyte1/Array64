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
 * @return the value of arrayBaseOffset()
 */
inline fun <reified E> getArrayIndexScale(typeInstance: E): Int {
    val arrayClass = Array(1){typeInstance}::class.java
    return unsafe.arrayIndexScale(arrayClass)
}