package com.millibyte1.array64

import it.unimi.dsi.fastutil.BigArrays
import org.junit.jupiter.api.MethodOrderer

import kotlin.system.measureTimeMillis

import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import kotlin.random.Random
import kotlin.streams.asStream

import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue
import kotlin.test.assertFalse

@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class ByteArray64Test {

    @Test
    @Order(1)
    fun testGet() {
        val array = ByteArray64(TEST_MEDIUM_ARRAY_SIZE) { i -> (i % 8).toByte() }
        array.forEachIndexed { i, e -> assertEquals(e, (i % 8).toByte()) }
    }

    @Test
    @Order(2)
    fun testSet() {
        val array = ByteArray64(TEST_MEDIUM_ARRAY_SIZE) { i -> (i % 8).toByte() }
        array.forEachIndexed {
            i, e -> run {
                array[i] = (e - 1).toByte()
                assertNotEquals(e, array[i])
                array[i] = e
            }
        }
    }

    @Test
    @Order(3)
    fun testCopy() {
        val array = ByteArray64(TEST_MEDIUM_ARRAY_SIZE) { i -> (i % 8).toByte() }
        val copy = array.copy()
        assertEquals(array.size, copy.size)
        assertTrue(array.contentEquals(copy))
        copy[0] = (copy[0] - 1).toByte()
        assertFalse(array.contentEquals(copy))
        copy[0] = array[0]
        assertTrue(array.contentEquals(copy))
    }

    @Test
    @Order(4)
    fun testRandomAccessTimes() {
        val random = Random(-234013250)
        val array = ByteArray64(TEST_MEDIUM_ARRAY_SIZE) { i -> (i % 8).toByte() }
        val indices = LongArray(10000000) { random.nextLong(10000000) }
        val time1 = measureTimeMillis {
            for(i in indices) {
                assertTrue(array[i] >= 0)
            }
        }
        val array32 = ByteArray(TEST_MEDIUM_ARRAY_SIZE.toInt()) { i -> (i % 8).toByte() }
        val time2 = measureTimeMillis {
            for(i in indices) {
                assertTrue(array32[i.toInt()] >= 0)
            }
        }
        println("time1: $time1")
        println("time2: $time2")
    }
    @Test
    @Order(5)
    fun testSequentialAccessTimes() {
        //TODO: figure out exactly why 2D arrays work so much faster for large arrays
        val array = ByteArray64(TEST_MEDIUM_ARRAY_SIZE) { i -> (i % 8).toByte() }
        val time1 = measureTimeMillis {
            array.forEachIndexed { i, e -> assertEquals(e, (i % 8).toByte()) }
        }
        val array32 = ByteArray(TEST_MEDIUM_ARRAY_SIZE.toInt()) { i -> (i % 8).toByte() }
        val time2 = measureTimeMillis {
            array32.forEachIndexed { i, e -> assertEquals(e, (i % 8).toByte()) }
        }
        println("time1: $time1")
        println("time2: $time2")
    }

    companion object {
        const val TEST_SMALL_ARRAY_SIZE = BigArrays.SEGMENT_SIZE.toLong()
        const val TEST_MEDIUM_ARRAY_SIZE = BigArrays.SEGMENT_SIZE.toLong() * 8
        const val TEST_LARGE_ARRAY_SIZE = Int.MAX_VALUE.toLong() - 8
    }

}