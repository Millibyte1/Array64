package com.millibyte1.array64

import it.unimi.dsi.fastutil.bytes.ByteBigArrays
import kotlin.random.Random

import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import kotlin.system.measureTimeMillis

import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue
import kotlin.test.assertFalse

class ByteArray64Test {

    @Test
    @Order(1)
    fun testGet() {
        val array = ByteArray64(TEST_ARRAY_SIZE) { i -> (i % 8).toByte() }
        for(i in array.indices) assertEquals(array[i], (i % 8).toByte())
        array.free()
    }

    @Test
    @Order(2)
    fun testSet() {
        val array = getRandomByteArray(TEST_ARRAY_SIZE)
        for(i in array.indices) {
            val temp = array[i]
            array[i] = (temp - 1).toByte()
            assertNotEquals(temp, array[i])
            array[i] = temp
        }
        array.free()
    }

    @Test
    @Order(3)
    fun testCopy() {
        val array = getRandomByteArray(TEST_SMALL_ARRAY_SIZE)
        val copy = array.copy()
        assertEquals(array.size, copy.size)
        assertNotEquals(array.address, copy.address)
        for(i in array.indices) {
            assertTrue(array.contentEquals(copy))
            copy[i] = (copy[i] - 1).toByte()
            assertFalse(array.contentEquals(copy))
            copy[i] = array[i]
        }
        array.free()
        copy.free()
    }

    @Test
    @Order(4)
    fun testRepeatedAccessTimes() {
        val array64 = getRandomByteArray(TEST_MEDIUM_ARRAY_SIZE)
        val array64Time = measureTimeMillis {
            for(i in 0 until 100000000L) {
                val bar = array64[i]
            }
        }
        array64.free()
        val bigArray = ByteBigArrays.wrap(getRandomByteArray32(TEST_MEDIUM_ARRAY_SIZE.toInt()))
        val bigArrayTime = measureTimeMillis {
            for(i in 0 until 100000000L) {
                val bar = ByteBigArrays.get(bigArray, i)
            }
        }
        println("Array64 time: $array64Time")
        println("BigArray time: $bigArrayTime")
    }

    companion object {
        private const val TEST_ARRAY_SIZE = Int.MAX_VALUE.toLong() + 1
        private const val TEST_MEDIUM_ARRAY_SIZE = Int.MAX_VALUE.toLong() / 2
        private const val TEST_SMALL_ARRAY_SIZE = 10000L
        private val random = Random(1562357892234611111)
        private fun getRandomByteArray(size: Long): ByteArray64 = ByteArray64(size) { (random.nextInt() % 8).toByte() }
        private fun getRandomByteArray32(size: Int): ByteArray = ByteArray(size) { (random.nextInt() % 8).toByte() }
    }

}