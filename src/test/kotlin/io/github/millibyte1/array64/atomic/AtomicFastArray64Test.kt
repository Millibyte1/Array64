package io.github.millibyte1.array64.atomic

import io.github.millibyte1.array64.*
import it.unimi.dsi.fastutil.BigArrays
import kotlinx.coroutines.*

import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.junit.jupiter.api.MethodOrderer

import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue
import kotlin.test.assertFalse

@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class AtomicFastArray64Test {

    @Test
    @Order(1)
    fun testGet() {
        val array = FastArray64(TEST_SMALL_ARRAY_SIZE) { i -> (i % 8).toByte() }
        array.forEachIndexed { i, e -> assertEquals(e, (i % 8).toByte()) }
    }
    @Test
    @Order(2)
    fun testSet() {
        val array = AtomicFastArray64(TEST_SMALL_ARRAY_SIZE) { i -> (i % 8).toByte() }
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
        val array = AtomicFastArray64(TEST_MEDIUM_ARRAY_SIZE) { 0.toByte() }
        array[0] = 1
        array[5] = 1
        array[TEST_SMALL_ARRAY_SIZE + 3] = 1
        array[TEST_SMALL_ARRAY_SIZE + 4] = 1
        var count = 0
        array.forEachInRangeIndexed(1 until TEST_SMALL_ARRAY_SIZE + 4) { _, e -> if(e == 1.toByte()) count++ }
        assertEquals(count, 2)
        array.forEachInRange(FastByteArray64Test.TEST_SMALL_ARRAY_SIZE + 5 until FastByteArray64Test.TEST_MEDIUM_ARRAY_SIZE) { e -> assertEquals(e, 0.toByte()) }
    }

    @Test
    @Order(4)
    fun testCopy() {
        //I don't have the RAM to test copy with even one full inner array
        val array = AtomicFastArray64(10_000_000) { i -> (i % 8).toByte() }
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
    fun testGetAndSet() = runBlocking {
        val array = AtomicFastArray64(1) { 0 }
        val jobs: Array<Job> = Array(100) {
            launch(Dispatchers.Default) {
                delay(10_000)
                for(i in 0 until 1_000_000) array.getAndSet(0) { it + 1 }
            }
        }
        joinAll(*jobs)
        assertEquals(100_000_000, array[0])
    }

    companion object {
        const val TEST_SMALL_ARRAY_SIZE = BigArrays.SEGMENT_SIZE.toLong() + 1
        const val TEST_MEDIUM_ARRAY_SIZE = BigArrays.SEGMENT_SIZE.toLong() * 2
        const val TEST_LARGE_ARRAY_SIZE = Int.MAX_VALUE.toLong() - 8
    }
}