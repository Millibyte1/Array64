package com.millibyte1.array64

import kotlin.random.Random

import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.TestMethodOrder
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

import kotlin.test.assertEquals
import kotlin.test.assertTrue

@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class ByteArray64Test {

    @ParameterizedTest
    @MethodSource("params")
    @Order(1)
    fun testConstructors(arrays: List<ByteArray64>) {
        for(array in arrays) {

        }
        TODO()
    }
    @ParameterizedTest
    @MethodSource("params")
    @Order(3)
    fun testCopy(arrays: List<ByteArray64>) {
        for(array in arrays) {
            val copy = array.copy()

        }
        TODO()
    }
    @ParameterizedTest
    @MethodSource("params")
    @Order(2)
    fun testGet(arrays: List<ByteArray64>) {
        for(array in arrays) {

        }
        TODO()
    }
    @ParameterizedTest
    @MethodSource("params")
    @Order(4)
    fun testSet(arrays: List<ByteArray64>) {
        for(array in arrays) {

        }
        TODO()
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
            val smallArrays = ArrayList<ByteArray64>(1000)
            for(i in smallArrays.indices) smallArrays[i] = ByteArray64(getRandomByteArray())
            return smallArrays
        }
        @JvmStatic
        fun bigArrays(): List<ByteArray64> {
            val bigArrays = ArrayList<ByteArray64>(1000)
            for(i in bigArrays.indices) bigArrays[i] = ByteArray64(getRandomByteArrays())
            return bigArrays
        }

        fun getRandomByteArrays(numArrays: Int = NUM_INNER_ARRAYS): List<ByteArray> {
            val arrays = ArrayList<ByteArray>(numArrays)
            for(i in arrays.indices) arrays[i] = getRandomByteArray()
            return arrays
        }
        fun getRandomByteArray(size: Int = Int.MAX_VALUE): ByteArray {
            return ByteArray(size) { random.nextInt().toByte() }
        }
    }

}