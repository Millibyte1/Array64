package com.millibyte1.array64.util

import sun.misc.Unsafe

/**
 * A simple wrapper for a reference to a sun.misc.Unsafe instance obtained through reflection hacking.
 * @property unsafe a reference to the sun.misc.Unsafe singleton
 */
object UnsafeUtils {

    val unsafe: Unsafe

    init {
        //Uses some reflection hacking to get an instance of the Unsafe class
        val f = Unsafe::class.java.getDeclaredField("theUnsafe")
        f.trySetAccessible()
        unsafe = f.get(null) as Unsafe
    }
}