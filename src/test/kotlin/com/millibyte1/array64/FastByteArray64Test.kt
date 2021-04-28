package com.millibyte1.array64

import it.unimi.dsi.fastutil.BigArrays

import kotlin.system.measureTimeMillis
import kotlin.random.Random

import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.junit.jupiter.api.MethodOrderer

import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue
import kotlin.test.assertFalse

@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class FastByteArray64Test {

    @Test
    @Order(0)
    fun testGet() {
        val array = FastByteArray64(TEST_MEDIUM_ARRAY_SIZE) { i -> (i % 8).toByte() }
        array.forEachIndexed { i, e -> assertEquals(e, (i % 8).toByte()) }
    }
    @Test
    @Order(1)
    fun testSet() {
        val array = FastByteArray64(TEST_MEDIUM_ARRAY_SIZE) { i -> (i % 8).toByte() }
        array.forEachIndexed {
            i, e -> run {
                array[i] = (e - 1).toByte()
                assertNotEquals(e, array[i])
                array[i] = e
            }
        }
    }
    @Test
    @Order(2)
    fun testForEachInRange() {
        val array = FastByteArray64(TEST_MEDIUM_ARRAY_SIZE) { 0 }
        array[0] = 1
        array[5] = 1
        array[TEST_SMALL_ARRAY_SIZE + 3] = 1
        array[TEST_SMALL_ARRAY_SIZE + 4] = 1
        var count = 0
        /*
        val iterator = array.iterator(1)
        while(iterator.index < TEST_SMALL_ARRAY_SIZE + 4) {
            if(iterator.nextByte() == 1.toByte()) count++
        }
         */
        array.forEachInRangeIndexed(1 until TEST_SMALL_ARRAY_SIZE + 4) { _, e -> if(e == 1.toByte()) count++ }
        assertEquals(count, 2)
    }

    @Test
    @Order(3)
    fun testCopy() {
        val array = FastByteArray64(TEST_MEDIUM_ARRAY_SIZE) { i -> (i % 8).toByte() }
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
        val array = FastByteArray64(TEST_MEDIUM_ARRAY_SIZE) { i -> (i % 8).toByte() }
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
        val array = FastByteArray64(TEST_MEDIUM_ARRAY_SIZE) { i -> (i % 8).toByte() }
        val time1 = measureTimeMillis {
            array.forEachIndexed { i, e -> assertEquals(e, (i % 8).toByte()) }
        }
        val array32 = ByteArray(TEST_MEDIUM_ARRAY_SIZE.toInt()) { i -> (i % 8).toByte() }
        val time2 = measureTimeMillis {
            array32.forEachIndexed { i, e -> assertEquals(e, (i % 8).toByte()) }
        }
        val time3 = measureTimeMillis {
            val foo = array[0]
            for(i in 0 until array.size) {
                assertEquals(foo, 0)
            }
        }
        println("time1: $time1")
        println("time2: $time2")
        println("time3: $time3")
    }

    companion object {
        const val TEST_SMALL_ARRAY_SIZE = BigArrays.SEGMENT_SIZE.toLong() + 1
        const val TEST_MEDIUM_ARRAY_SIZE = BigArrays.SEGMENT_SIZE.toLong() * 2
        const val TEST_LARGE_ARRAY_SIZE = Int.MAX_VALUE.toLong() - 8
    }

}