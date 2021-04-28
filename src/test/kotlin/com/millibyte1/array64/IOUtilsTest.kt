package com.millibyte1.array64

import java.io.File

import it.unimi.dsi.fastutil.BigArrays

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.junit.jupiter.api.MethodOrderer

@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class IOUtilsTest {

    @Test
    @Order(1)
    fun testReadFromFile() {
        val array = readFileToByteArray64(File("src/test/resources/bigfile.db"))
        var sum = 0L
        for(i in 0 until array.array.lastIndex) {
            val segmentSize = array.array[i].size
            assertEquals(array.array[i].size, BigArrays.SEGMENT_SIZE)
            sum += segmentSize
        }
        sum += array.array.last().size
        assertEquals(sum, array.size)
    }

    @Test
    @Order(2)
    fun testWriteToFile() {
        val array = FastByteArray64(BigArrays.SEGMENT_SIZE.toLong() + 1)
        val file = File("src/test/resources/bigfileout.db")
        file.delete()
        file.createNewFile()
        writeByteArray64ToFile(file, array)
        val copy = readFileToByteArray64(file)
        assertEquals(array, copy)
    }
}