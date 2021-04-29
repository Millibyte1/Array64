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
    fun testReadAndWrite() {
        // writes an array to a file then reads it back, checks they're identical. fails if read isn't working
        val array = FastByteArray64(BigArrays.SEGMENT_SIZE.toLong() + 1) { 17 }
        val file = File("src/test/resources/bigfile.db")
        file.delete()
        file.createNewFile()
        writeByteArray64ToFile(file, array)
        val copy = readFileToByteArray64(file)
        assertEquals(array, copy)
    }
}