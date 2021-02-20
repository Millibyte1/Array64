package com.millibyte1.array64

import org.junit.jupiter.api.Test

import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class ByteArray64Test {

    @Test
    fun testBasic() {
        val foo = ByteArray64(Int.MAX_VALUE.toLong() - 10) { i -> (i % 8).toByte() }
        assertEquals(foo[0], 0)
        assertNotEquals(foo[0], 1)
    }
    @Test
    fun testStandardArray() {
        val foo = ByteArray(Int.MAX_VALUE - 10) { i -> (i % 8).toByte() }
        assertEquals(foo[0], 0)
        assertNotEquals(foo[0], 1)
    }
}