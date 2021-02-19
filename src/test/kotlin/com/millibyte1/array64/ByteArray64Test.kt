package com.millibyte1.array64

import kotlin.random.Random

import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.assertFalse

@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class ByteArray64Test {

    @ParameterizedTest
    @MethodSource("params")
    @Order(1)
    fun testConstructors(arrays: List<ByteArray64>) {
        for(array in arrays) {

        }
    }
    @ParameterizedTest
    @MethodSource("params")
    @Order(3)
    fun testCopy(arrays: List<ByteArray64>) {
        for(array in arrays) {
            val copy = array.copy()
            assertTrue(copy contentEquals array)
            copy[copy.lastIndex]++
            assertFalse(copy contentEquals array)
        }
    }
    @ParameterizedTest
    @MethodSource("params")
    @Order(2)
    fun testGet(arrays: List<ByteArray64>) {
        for(array in arrays) {
            for(i in array.indices) {

            }
        }
    }
    @ParameterizedTest
    @MethodSource("params")
    @Order(4)
    fun testSet(arrays: List<ByteArray64>) {
        for(array in arrays) {

        }
    }

    @Test
    fun testBasicGet() {

    }
    @Test
    fun testIndexing() {
        var index = ByteArray64.MAX_PARTIAL_INDEX
        var innerIndex = ByteArray64.getInnerIndex(index)
        var outerIndex = ByteArray64.getOuterIndex(index)
        assertEquals(innerIndex, ByteArray64.MAX_PARTIAL_INDEX.toInt())
        assertEquals(outerIndex, 0)
        index = ByteArray64.MAX_PARTIAL_INDEX + 1
        innerIndex = ByteArray64.getInnerIndex(index)
        outerIndex = ByteArray64.getOuterIndex(index)
        assertEquals(innerIndex, 0)
        assertEquals(outerIndex, 1)
        index = ByteArray64.MAX_PARTIAL_INDEX + 2
        innerIndex = ByteArray64.getInnerIndex(index)
        outerIndex = ByteArray64.getOuterIndex(index)
        assertEquals(innerIndex, 1)
        assertEquals(outerIndex, 1)
        index = ByteArray64.MAX_PARTIAL_INDEX + 3
        innerIndex = ByteArray64.getInnerIndex(index)
        outerIndex = ByteArray64.getOuterIndex(index)
        assertEquals(innerIndex, 2)
        assertEquals(outerIndex, 1)
    }

    companion object {

        private val random: Random = Random(-23401203532)
        private const val NUM_INNER_ARRAYS = 2

        @JvmStatic
        fun params(): List<List<ByteArray64>> {
            return listOf(smallArrays(), bigArrays())
        }
        @JvmStatic
        fun smallArrays(): List<ByteArray64> {
            val smallArrays = ArrayList<ByteArray64>(3)
            //for(i in 0 until 3) smallArrays[i] = ByteArray64(getSimpleByteArray())
            for(i in 0 until 3) smallArrays[i] = ByteArray64(100)
            return smallArrays
        }
        @JvmStatic
        fun bigArrays(): List<ByteArray64> {
            val bigArrays = ArrayList<ByteArray64>(3)
            //for(i in 0 until 3) bigArrays[i] = ByteArray64(getSimpleByteArrays())
            for(i in 0 until 3) bigArrays[i] = ByteArray64(100)
            return bigArrays
        }

        fun getSimpleByteArrays(numArrays: Int = NUM_INNER_ARRAYS): List<ByteArray> {
            val arrays = ArrayList<ByteArray>(numArrays)
            for(i in arrays.indices) arrays[i] = getSimpleByteArray()
            return arrays
        }
        fun getSimpleByteArray(size: Int = 100): ByteArray {
            return ByteArray(size) { i -> (i % 8).toByte() }
        }
        fun getRandomByteArrays(numArrays: Int = NUM_INNER_ARRAYS): List<ByteArray> {
            val arrays = ArrayList<ByteArray>(numArrays)
            for(i in arrays.indices) arrays[i] = getRandomByteArray()
            return arrays
        }
        fun getRandomByteArray(size: Int = ByteArray64.MAX_PARTIAL_SIZE.toInt()): ByteArray {
            return ByteArray(size) { random.nextInt().toByte() }
        }
    }

}