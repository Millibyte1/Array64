@file:JvmName("IOUtils")
package io.github.millibyte1.array64

import it.unimi.dsi.fastutil.BigArrays
import org.apache.commons.io.FileUtils
import org.apache.commons.io.IOUtils
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import kotlin.math.min

/**
 * Reads the contents of a [File] into a FastByteArray64.
 * @param file the file to read from
 * @return the file contents
 * @throws IOException if a problem occurs during IO
 */
@Throws(IOException::class)
fun readFileToByteArray64(file: File): FastByteArray64 {
    val stream = FileUtils.openInputStream(file)
    val retval = readInputStreamToByteArray64(stream)
    stream.close()
    return retval
}

/**
 * Writes the contents of a ByteArray64 to the given [File]
 * @param file the file to write to
 * @param array the array to write
 * @throws IOException if a problem occurs during IO
 */
@Throws(IOException::class)
fun writeByteArray64ToFile(file: File, array: FastByteArray64) {
    val stream = FileUtils.openOutputStream(file)
    val retval = writeByteArray64ToOutputStream(stream, array)
    stream.close()
    return retval
}
/**
 * Reads the contents of an [InputStream] into a FastByteArray64.
 * Assumes the provided stream will not have new content written to it during execution.
 * @param stream the stream to read from. If new content is written to the stream during execution, the resulting array will be unusable.
 * @return the stream contents
 * @throws IOException if a problem occurs during IO
 */
@Throws(IOException::class)
fun readInputStreamToByteArray64(stream: InputStream): FastByteArray64 {
    val arrays = ArrayList<ByteArray>()
    while(stream.available() > 0) {
        val bytes = ByteArray(min(BigArrays.SEGMENT_SIZE, stream.available()))
        IOUtils.read(stream, bytes)
        arrays.add(bytes)
    }
    return FastByteArray64(arrays.toTypedArray(), false)
}
/**
 * Writes the contents of a ByteArray64 to the given [OutputStream]
 * @param stream the file to write to
 * @param array the array to write
 * @throws IOException if a problem occurs during IO
 */
@Throws(IOException::class)
fun writeByteArray64ToOutputStream(stream: OutputStream, array: FastByteArray64) = array.array.forEach { segment -> IOUtils.write(segment, stream) }