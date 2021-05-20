package io.github.millibyte1.array64

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
class FastArray64Test {

    @Test
    @Order(1)
    fun testGet() {
        val array = FastArray64(TEST_SMALL_ARRAY_SIZE) { i -> (i % 8).toByte() }
        array.forEachIndexed { i, e -> assertEquals(e, (i % 8).toByte()) }
    }
    @Test
    @Order(2)
    fun testSet() {
        val array = FastArray64(TEST_SMALL_ARRAY_SIZE) { i -> (i % 8).toByte() }
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
    fun testForEachInRange() {
        val array = FastArray64(TEST_MEDIUM_ARRAY_SIZE) { 0.toByte() }
        array[0] = 1
        array[5] = 1
        array[TEST_SMALL_ARRAY_SIZE + 3] = 1
        array[TEST_SMALL_ARRAY_SIZE + 4] = 1
        var count = 0
        array.forEachInRangeIndexed(1 until TEST_SMALL_ARRAY_SIZE + 4) { _, e -> if(e == 1.toByte()) count++ }
        assertEquals(count, 2)
    }

    @Test
    @Order(4)
    fun testCopy() {
        //I don't have the RAM to test copy with even one full inner array
        val array = FastArray64(10000000) { i -> (i % 8).toByte() }
        val copy = array.copy()
        assertEquals(array.size, copy.size)
        assertTrue(array.contentEquals(copy))
        copy[0] = (copy[0] - 1).toByte()
        assertFalse(array.contentEquals(copy))
        copy[0] = array[0]
        assertTrue(array.contentEquals(copy))
    }

    @Test
    @Order(5)
    fun testRandomAccessTimes() {
        val random = Random(-234013250)
        val array = FastArray64(TEST_MEDIUM_ARRAY_SIZE) { i -> (i % 8).toByte() }
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
    @Order(6)
    fun testSequentialAccessTimes() {
        val array = FastArray64(10000000) { i -> (i % 8).toByte() }
        val time1 = measureTimeMillis {
            array.forEachIndexed { i, e -> assertEquals(e, (i % 8).toByte()) }
        }
        val array32 = Array(10000000) { i -> (i % 8).toByte() }
        val time2 = measureTimeMillis {
            array32.forEachIndexed { i, e -> assertEquals(e, (i % 8).toByte()) }
        }
        println("time1: $time1")
        println("time2: $time2")
    }

    companion object {
        const val TEST_SMALL_ARRAY_SIZE = BigArrays.SEGMENT_SIZE.toLong() + 1
        const val TEST_MEDIUM_ARRAY_SIZE = BigArrays.SEGMENT_SIZE.toLong() * 2
        const val TEST_LARGE_ARRAY_SIZE = Int.MAX_VALUE.toLong() - 8
    }
}