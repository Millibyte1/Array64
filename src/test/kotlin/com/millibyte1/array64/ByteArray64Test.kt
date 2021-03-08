package com.millibyte1.array64

import it.unimi.dsi.fastutil.BigArrays

import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test

import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue
import kotlin.test.assertFalse

class ByteArray64Test {

    @Test
    @Order(1)
    fun testGet() {
        val array = ByteArray64(TEST_LARGE_ARRAY_SIZE) { i -> (i % 8).toByte() }
        for(i in array.indices) assertEquals(array[i], (i % 8).toByte())
    }

    @Test
    @Order(2)
    fun testSet() {
        val array = ByteArray64(TEST_LARGE_ARRAY_SIZE) { i -> (i % 8).toByte() }
        for(i in array.indices) {
            val temp = array[i]
            array[i] = (temp - 1).toByte()
            assertNotEquals(temp, array[i])
            array[i] = temp
        }
    }

    @Test
    @Order(3)
    fun testCopy() {
        val array = ByteArray64(TEST_SMALL_ARRAY_SIZE) { i -> (i % 8).toByte() }
        val copy = array.copy()
        assertEquals(array.size, copy.size)
        assertTrue(array.contentEquals(copy))
        copy[0] = (copy[0] - 1).toByte()
        assertFalse(array.contentEquals(copy))
        copy[0] = array[0]
        assertTrue(array.contentEquals(copy))
    }

    companion object {
        const val TEST_SMALL_ARRAY_SIZE = BigArrays.SEGMENT_SIZE.toLong()
        const val TEST_LARGE_ARRAY_SIZE = BigArrays.SEGMENT_SIZE.toLong() * 2
    }

}