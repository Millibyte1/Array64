@file:JvmName("FastArrays")
package io.github.millibyte1.array64

import it.unimi.dsi.fastutil.BigArrays

// CONSTANTS

/** The theoretical maximum number of elements that can fit in a FastArray64. */
const val MAX_SIZE = BigArrays.SEGMENT_SIZE.toLong() * Int.MAX_VALUE

// HELPERS

/** Creates a generic [FastArray64] with the given size and initializer. */
inline fun <reified E> makeTypedFastArray64(size: Long, crossinline init: (Long) -> E): FastArray64<E> = FastArray64(makeTyped2DArray(size, init))

// MISC EXTENSION FUNCTIONS

/**
 * Creates a new array of the specified [size], with all elements initialized according to the given [init] function.
 *
 * This is a Kotlin pseudo-constructor. Reified type parameters are needed for generic 2D array creation but aren't possible
 * with real constructors, so an inlined operator function is used to act like a constructor.
 *
 * @throws IllegalArgumentException if [size] is not between 1 and [MAX_SIZE]
 */
inline operator fun <reified E> FastArray64<E>.invoke(size: Long, crossinline init: (Long) -> E): FastArray64<E> = makeTypedFastArray64(size, init)
/**
 * Creates a new array of the specified [size], with all elements initialized according to the given [init] function.
 *
 * This is a Kotlin pseudo-constructor. Reified type parameters are needed for generic 2D array creation but aren't possible
 * with real constructors, so an inlined operator function is used to act like a constructor.
 *
 * @throws IllegalArgumentException if [size] is not between 1 and [MAX_SIZE]
 */
inline operator fun FastBooleanArray64.invoke(size: Long, init: (Long) -> Boolean): FastBooleanArray64 {
    val retval = FastBooleanArray64(size)
    //initializes the elements of the array using cache-aware iteration as per FastUtil specification
    var index = 0L
    for(inner in retval.array) {
        var innerIndex = 0
        while(innerIndex < inner.size) {
            inner[innerIndex] = init(index)
            innerIndex++
            index++
        }
    }
    return retval
}
/**
 * Creates a new array of the specified [size], with all elements initialized according to the given [init] function.
 *
 * This is a Kotlin pseudo-constructor. Reified type parameters are needed for generic 2D array creation but aren't possible
 * with real constructors, so an inlined operator function is used to act like a constructor.
 *
 * @throws IllegalArgumentException if [size] is not between 1 and [MAX_SIZE]
 */
inline operator fun FastByteArray64.invoke(size: Long, init: (Long) -> Byte): FastByteArray64 {
    val retval = FastByteArray64(size)
    //initializes the elements of the array using cache-aware iteration as per FastUtil specification
    var index = 0L
    for(inner in retval.array) {
        var innerIndex = 0
        while(innerIndex < inner.size) {
            inner[innerIndex] = init(index)
            innerIndex++
            index++
        }
    }
    return retval
}
/**
 * Creates a new array of the specified [size], with all elements initialized according to the given [init] function.
 *
 * This is a Kotlin pseudo-constructor. Reified type parameters are needed for generic 2D array creation but aren't possible
 * with real constructors, so an inlined operator function is used to act like a constructor.
 *
 * @throws IllegalArgumentException if [size] is not between 1 and [MAX_SIZE]
 */
inline operator fun FastCharArray64.invoke(size: Long, init: (Long) -> Char): FastCharArray64 {
    val retval = FastCharArray64(size)
    //initializes the elements of the array using cache-aware iteration as per FastUtil specification
    var index = 0L
    for(inner in retval.array) {
        var innerIndex = 0
        while(innerIndex < inner.size) {
            inner[innerIndex] = init(index)
            innerIndex++
            index++
        }
    }
    return retval
}
/**
 * Creates a new array of the specified [size], with all elements initialized according to the given [init] function.
 *
 * This is a Kotlin pseudo-constructor. Reified type parameters are needed for generic 2D array creation but aren't possible
 * with real constructors, so an inlined operator function is used to act like a constructor.
 *
 * @throws IllegalArgumentException if [size] is not between 1 and [MAX_SIZE]
 */
inline operator fun FastDoubleArray64.invoke(size: Long, init: (Long) -> Double): FastDoubleArray64 {
    val retval = FastDoubleArray64(size)
    //initializes the elements of the array using cache-aware iteration as per FastUtil specification
    var index = 0L
    for(inner in retval.array) {
        var innerIndex = 0
        while(innerIndex < inner.size) {
            inner[innerIndex] = init(index)
            innerIndex++
            index++
        }
    }
    return retval
}
/**
 * Creates a new array of the specified [size], with all elements initialized according to the given [init] function.
 *
 * This is a Kotlin pseudo-constructor. Reified type parameters are needed for generic 2D array creation but aren't possible
 * with real constructors, so an inlined operator function is used to act like a constructor.
 *
 * @throws IllegalArgumentException if [size] is not between 1 and [MAX_SIZE]
 */
inline operator fun FastFloatArray64.invoke(size: Long, init: (Long) -> Float): FastFloatArray64 {
    val retval = FastFloatArray64(size)
    //initializes the elements of the array using cache-aware iteration as per FastUtil specification
    var index = 0L
    for(inner in retval.array) {
        var innerIndex = 0
        while(innerIndex < inner.size) {
            inner[innerIndex] = init(index)
            innerIndex++
            index++
        }
    }
    return retval
}
/**
 * Creates a new array of the specified [size], with all elements initialized according to the given [init] function.
 *
 * This is a Kotlin pseudo-constructor. Reified type parameters are needed for generic 2D array creation but aren't possible
 * with real constructors, so an inlined operator function is used to act like a constructor.
 *
 * @throws IllegalArgumentException if [size] is not between 1 and [MAX_SIZE]
 */
inline operator fun FastIntArray64.invoke(size: Long, init: (Long) -> Int): FastIntArray64 {
    val retval = FastIntArray64(size)
    //initializes the elements of the array using cache-aware iteration as per FastUtil specification
    var index = 0L
    for(inner in retval.array) {
        var innerIndex = 0
        while(innerIndex < inner.size) {
            inner[innerIndex] = init(index)
            innerIndex++
            index++
        }
    }
    return retval
}
/**
 * Creates a new array of the specified [size], with all elements initialized according to the given [init] function.
 *
 * This is a Kotlin pseudo-constructor. Reified type parameters are needed for generic 2D array creation but aren't possible
 * with real constructors, so an inlined operator function is used to act like a constructor.
 *
 * @throws IllegalArgumentException if [size] is not between 1 and [MAX_SIZE]
 */
inline operator fun FastLongArray64.invoke(size: Long, init: (Long) -> Long): FastLongArray64 {
    val retval = FastLongArray64(size)
    //initializes the elements of the array using cache-aware iteration as per FastUtil specification
    var index = 0L
    for(inner in retval.array) {
        var innerIndex = 0
        while(innerIndex < inner.size) {
            inner[innerIndex] = init(index)
            innerIndex++
            index++
        }
    }
    return retval
}
/**
 * Creates a new array of the specified [size], with all elements initialized according to the given [init] function.
 *
 * This is a Kotlin pseudo-constructor. Reified type parameters are needed for generic 2D array creation but aren't possible
 * with real constructors, so an inlined operator function is used to act like a constructor.
 *
 * @throws IllegalArgumentException if [size] is not between 1 and [MAX_SIZE]
 */
inline operator fun FastShortArray64.invoke(size: Long, init: (Long) -> Short): FastShortArray64 {
    val retval = FastShortArray64(size)
    //initializes the elements of the array using cache-aware iteration as per FastUtil specification
    var index = 0L
    for(inner in retval.array) {
        var innerIndex = 0
        while(innerIndex < inner.size) {
            inner[innerIndex] = init(index)
            innerIndex++
            index++
        }
    }
    return retval
}

// HIGHER ORDER FUNCTIONS
// TODO: consider removing due to limited performance benefit over iterator-based methods

/** Performs the given [action] on each element. */
inline fun <E> FastArray64<E>.forEach(action: (E) -> Unit) {
    for(inner in this.array) {
        for(innerIndex in inner.indices) {
            action(inner[innerIndex])
        }
    }
}
/** Performs the given [action] on each element. */
inline fun FastBooleanArray64.forEach(action: (Boolean) -> Unit) {
    for(inner in this.array) {
        for(innerIndex in inner.indices) {
            action(inner[innerIndex])
        }
    }
}
/** Performs the given [action] on each element. */
inline fun FastByteArray64.forEach(action: (Byte) -> Unit) {
    for(inner in this.array) {
        for(innerIndex in inner.indices) {
            action(inner[innerIndex])
        }
    }
}
/** Performs the given [action] on each element. */
inline fun FastCharArray64.forEach(action: (Char) -> Unit) {
    for(inner in this.array) {
        for(innerIndex in inner.indices) {
            action(inner[innerIndex])
        }
    }
}
/** Performs the given [action] on each element. */
inline fun FastDoubleArray64.forEach(action: (Double) -> Unit) {
    for(inner in this.array) {
        for(innerIndex in inner.indices) {
            action(inner[innerIndex])
        }
    }
}
/** Performs the given [action] on each element. */
inline fun FastFloatArray64.forEach(action: (Float) -> Unit) {
    for(inner in this.array) {
        for(innerIndex in inner.indices) {
            action(inner[innerIndex])
        }
    }
}
/** Performs the given [action] on each element. */
inline fun FastIntArray64.forEach(action: (Int) -> Unit) {
    for(inner in this.array) {
        for(innerIndex in inner.indices) {
            action(inner[innerIndex])
        }
    }
}
/** Performs the given [action] on each element. */
inline fun FastLongArray64.forEach(action: (Long) -> Unit) {
    for(inner in this.array) {
        for(innerIndex in inner.indices) {
            action(inner[innerIndex])
        }
    }
}
/** Performs the given [action] on each element. */
inline fun FastShortArray64.forEach(action: (Short) -> Unit) {
    for(inner in this.array) {
        for(innerIndex in inner.indices) {
            action(inner[innerIndex])
        }
    }
}

/** Performs the given [action] on each element, providing sequential index with the element. */
inline fun <E> FastArray64<E>.forEachIndexed(action: (index: Long, E) -> Unit) {
    var index = 0L
    for(inner in this.array) {
        for(innerIndex in inner.indices) {
            action(index, inner[innerIndex])
            index++
        }
    }
}
/** Performs the given [action] on each element, providing sequential index with the element. */
inline fun FastBooleanArray64.forEachIndexed(action: (index: Long, Boolean) -> Unit) {
    var index = 0L
    for(inner in this.array) {
        for(innerIndex in inner.indices) {
            action(index, inner[innerIndex])
            index++
        }
    }
}
/** Performs the given [action] on each element, providing sequential index with the element. */
inline fun FastByteArray64.forEachIndexed(action: (index: Long, Byte) -> Unit) {
    var index = 0L
    for(inner in this.array) {
        for(innerIndex in inner.indices) {
            action(index, inner[innerIndex])
            index++
        }
    }
}
/** Performs the given [action] on each element, providing sequential index with the element. */
inline fun FastCharArray64.forEachIndexed(action: (index: Long, Char) -> Unit) {
    var index = 0L
    for(inner in this.array) {
        for(innerIndex in inner.indices) {
            action(index, inner[innerIndex])
            index++
        }
    }
}
/** Performs the given [action] on each element, providing sequential index with the element. */
inline fun FastDoubleArray64.forEachIndexed(action: (index: Long, Double) -> Unit) {
    var index = 0L
    for(inner in this.array) {
        for(innerIndex in inner.indices) {
            action(index, inner[innerIndex])
            index++
        }
    }
}
/** Performs the given [action] on each element, providing sequential index with the element. */
inline fun FastFloatArray64.forEachIndexed(action: (index: Long, Float) -> Unit) {
    var index = 0L
    for(inner in this.array) {
        for(innerIndex in inner.indices) {
            action(index, inner[innerIndex])
            index++
        }
    }
}
/** Performs the given [action] on each element, providing sequential index with the element. */
inline fun FastIntArray64.forEachIndexed(action: (index: Long, Int) -> Unit) {
    var index = 0L
    for(inner in this.array) {
        for(innerIndex in inner.indices) {
            action(index, inner[innerIndex])
            index++
        }
    }
}
/** Performs the given [action] on each element, providing sequential index with the element. */
inline fun FastLongArray64.forEachIndexed(action: (index: Long, Long) -> Unit) {
    var index = 0L
    for(inner in this.array) {
        for(innerIndex in inner.indices) {
            action(index, inner[innerIndex])
            index++
        }
    }
}
/** Performs the given [action] on each element, providing sequential index with the element. */
inline fun FastShortArray64.forEachIndexed(action: (index: Long, Short) -> Unit) {
    var index = 0L
    for(inner in this.array) {
        for(innerIndex in inner.indices) {
            action(index, inner[innerIndex])
            index++
        }
    }
}

/** Performs the given [action] on each element within the [range]. */
inline fun <E> FastArray64<E>.forEachInRange(range: LongRange, action: (E) -> Unit) {
    if(range.first < 0 || range.last >= this.size) throw NoSuchElementException()
    //calculates the indices of the first and last elements in the range
    val outerIndexOfFirst = BigArrays.segment(range.first)
    val outerIndexOfLast = BigArrays.segment(range.last)
    val innerIndexOfFirst = BigArrays.displacement(range.first)
    val innerIndexOfLast = BigArrays.displacement(range.last)
    //performs the iteration
    for(outerIndex in outerIndexOfFirst..outerIndexOfLast) {
        val inner = this.array[outerIndex]
        val startingInnerIndex = if(outerIndex == outerIndexOfFirst) innerIndexOfFirst else 0
        val endingInnerIndex = if(outerIndex == outerIndexOfLast) innerIndexOfLast else BigArrays.SEGMENT_SIZE - 1
        for(innerIndex in startingInnerIndex..endingInnerIndex) {
            action(inner[innerIndex])
        }
    }
}
/** Performs the given [action] on each element within the [range]. */
inline fun FastBooleanArray64.forEachInRange(range: LongRange, action: (Boolean) -> Unit) {
    if(range.first < 0 || range.last >= this.size) throw NoSuchElementException()
    //calculates the indices of the first and last elements in the range
    val outerIndexOfFirst = BigArrays.segment(range.first)
    val outerIndexOfLast = BigArrays.segment(range.last)
    val innerIndexOfFirst = BigArrays.displacement(range.first)
    val innerIndexOfLast = BigArrays.displacement(range.last)
    //performs the iteration
    for(outerIndex in outerIndexOfFirst..outerIndexOfLast) {
        val inner = this.array[outerIndex]
        val startingInnerIndex = if(outerIndex == outerIndexOfFirst) innerIndexOfFirst else 0
        val endingInnerIndex = if(outerIndex == outerIndexOfLast) innerIndexOfLast else BigArrays.SEGMENT_SIZE - 1
        for(innerIndex in startingInnerIndex..endingInnerIndex) {
            action(inner[innerIndex])
        }
    }
}
/** Performs the given [action] on each element within the [range]. */
inline fun FastByteArray64.forEachInRange(range: LongRange, action: (Byte) -> Unit) {
    if(range.first < 0 || range.last >= this.size) throw NoSuchElementException()
    //calculates the indices of the first and last elements in the range
    val outerIndexOfFirst = BigArrays.segment(range.first)
    val outerIndexOfLast = BigArrays.segment(range.last)
    val innerIndexOfFirst = BigArrays.displacement(range.first)
    val innerIndexOfLast = BigArrays.displacement(range.last)
    //performs the iteration
    for(outerIndex in outerIndexOfFirst..outerIndexOfLast) {
        val inner = this.array[outerIndex]
        val startingInnerIndex = if(outerIndex == outerIndexOfFirst) innerIndexOfFirst else 0
        val endingInnerIndex = if(outerIndex == outerIndexOfLast) innerIndexOfLast else BigArrays.SEGMENT_SIZE - 1
        for(innerIndex in startingInnerIndex..endingInnerIndex) {
            action(inner[innerIndex])
        }
    }
}
/** Performs the given [action] on each element within the [range]. */
inline fun FastCharArray64.forEachInRange(range: LongRange, action: (Char) -> Unit) {
    if(range.first < 0 || range.last >= this.size) throw NoSuchElementException()
    //calculates the indices of the first and last elements in the range
    val outerIndexOfFirst = BigArrays.segment(range.first)
    val outerIndexOfLast = BigArrays.segment(range.last)
    val innerIndexOfFirst = BigArrays.displacement(range.first)
    val innerIndexOfLast = BigArrays.displacement(range.last)
    //performs the iteration
    for(outerIndex in outerIndexOfFirst..outerIndexOfLast) {
        val inner = this.array[outerIndex]
        val startingInnerIndex = if(outerIndex == outerIndexOfFirst) innerIndexOfFirst else 0
        val endingInnerIndex = if(outerIndex == outerIndexOfLast) innerIndexOfLast else BigArrays.SEGMENT_SIZE - 1
        for(innerIndex in startingInnerIndex..endingInnerIndex) {
            action(inner[innerIndex])
        }
    }
}
/** Performs the given [action] on each element within the [range]. */
inline fun FastDoubleArray64.forEachInRange(range: LongRange, action: (Double) -> Unit) {
    if(range.first < 0 || range.last >= this.size) throw NoSuchElementException()
    //calculates the indices of the first and last elements in the range
    val outerIndexOfFirst = BigArrays.segment(range.first)
    val outerIndexOfLast = BigArrays.segment(range.last)
    val innerIndexOfFirst = BigArrays.displacement(range.first)
    val innerIndexOfLast = BigArrays.displacement(range.last)
    //performs the iteration
    for(outerIndex in outerIndexOfFirst..outerIndexOfLast) {
        val inner = this.array[outerIndex]
        val startingInnerIndex = if(outerIndex == outerIndexOfFirst) innerIndexOfFirst else 0
        val endingInnerIndex = if(outerIndex == outerIndexOfLast) innerIndexOfLast else BigArrays.SEGMENT_SIZE - 1
        for(innerIndex in startingInnerIndex..endingInnerIndex) {
            action(inner[innerIndex])
        }
    }
}
/** Performs the given [action] on each element within the [range]. */
inline fun FastFloatArray64.forEachInRange(range: LongRange, action: (Float) -> Unit) {
    if(range.first < 0 || range.last >= this.size) throw NoSuchElementException()
    //calculates the indices of the first and last elements in the range
    val outerIndexOfFirst = BigArrays.segment(range.first)
    val outerIndexOfLast = BigArrays.segment(range.last)
    val innerIndexOfFirst = BigArrays.displacement(range.first)
    val innerIndexOfLast = BigArrays.displacement(range.last)
    //performs the iteration
    for(outerIndex in outerIndexOfFirst..outerIndexOfLast) {
        val inner = this.array[outerIndex]
        val startingInnerIndex = if(outerIndex == outerIndexOfFirst) innerIndexOfFirst else 0
        val endingInnerIndex = if(outerIndex == outerIndexOfLast) innerIndexOfLast else BigArrays.SEGMENT_SIZE - 1
        for(innerIndex in startingInnerIndex..endingInnerIndex) {
            action(inner[innerIndex])
        }
    }
}
/** Performs the given [action] on each element within the [range]. */
inline fun FastIntArray64.forEachInRange(range: LongRange, action: (Int) -> Unit) {
    if(range.first < 0 || range.last >= this.size) throw NoSuchElementException()
    //calculates the indices of the first and last elements in the range
    val outerIndexOfFirst = BigArrays.segment(range.first)
    val outerIndexOfLast = BigArrays.segment(range.last)
    val innerIndexOfFirst = BigArrays.displacement(range.first)
    val innerIndexOfLast = BigArrays.displacement(range.last)
    //performs the iteration
    for(outerIndex in outerIndexOfFirst..outerIndexOfLast) {
        val inner = this.array[outerIndex]
        val startingInnerIndex = if(outerIndex == outerIndexOfFirst) innerIndexOfFirst else 0
        val endingInnerIndex = if(outerIndex == outerIndexOfLast) innerIndexOfLast else BigArrays.SEGMENT_SIZE - 1
        for(innerIndex in startingInnerIndex..endingInnerIndex) {
            action(inner[innerIndex])
        }
    }
}
/** Performs the given [action] on each element within the [range]. */
inline fun FastLongArray64.forEachInRange(range: LongRange, action: (Long) -> Unit) {
    if(range.first < 0 || range.last >= this.size) throw NoSuchElementException()
    //calculates the indices of the first and last elements in the range
    val outerIndexOfFirst = BigArrays.segment(range.first)
    val outerIndexOfLast = BigArrays.segment(range.last)
    val innerIndexOfFirst = BigArrays.displacement(range.first)
    val innerIndexOfLast = BigArrays.displacement(range.last)
    //performs the iteration
    for(outerIndex in outerIndexOfFirst..outerIndexOfLast) {
        val inner = this.array[outerIndex]
        val startingInnerIndex = if(outerIndex == outerIndexOfFirst) innerIndexOfFirst else 0
        val endingInnerIndex = if(outerIndex == outerIndexOfLast) innerIndexOfLast else BigArrays.SEGMENT_SIZE - 1
        for(innerIndex in startingInnerIndex..endingInnerIndex) {
            action(inner[innerIndex])
        }
    }
}
/** Performs the given [action] on each element within the [range]. */
inline fun FastShortArray64.forEachInRange(range: LongRange, action: (Short) -> Unit) {
    if(range.first < 0 || range.last >= this.size) throw NoSuchElementException()
    //calculates the indices of the first and last elements in the range
    val outerIndexOfFirst = BigArrays.segment(range.first)
    val outerIndexOfLast = BigArrays.segment(range.last)
    val innerIndexOfFirst = BigArrays.displacement(range.first)
    val innerIndexOfLast = BigArrays.displacement(range.last)
    //performs the iteration
    for(outerIndex in outerIndexOfFirst..outerIndexOfLast) {
        val inner = this.array[outerIndex]
        val startingInnerIndex = if(outerIndex == outerIndexOfFirst) innerIndexOfFirst else 0
        val endingInnerIndex = if(outerIndex == outerIndexOfLast) innerIndexOfLast else BigArrays.SEGMENT_SIZE - 1
        for(innerIndex in startingInnerIndex..endingInnerIndex) {
            action(inner[innerIndex])
        }
    }
}


/** Performs the given [action] on each element within the [range], providing sequential index with the element. */
inline fun <E> FastArray64<E>.forEachInRangeIndexed(range: LongRange, action: (index: Long, E) -> Unit) {
    if(range.first < 0 || range.last >= this.size) throw NoSuchElementException()
    //calculates the indices of the first and last elements in the range
    val outerIndexOfFirst = BigArrays.segment(range.first)
    val outerIndexOfLast = BigArrays.segment(range.last)
    val innerIndexOfFirst = BigArrays.displacement(range.first)
    val innerIndexOfLast = BigArrays.displacement(range.last)
    //performs the iteration
    var index = range.first
    for(outerIndex in outerIndexOfFirst..outerIndexOfLast) {
        val inner = this.array[outerIndex]
        val startingInnerIndex = if(outerIndex == outerIndexOfFirst) innerIndexOfFirst else 0
        val endingInnerIndex = if(outerIndex == outerIndexOfLast) innerIndexOfLast else BigArrays.SEGMENT_SIZE - 1
        for(innerIndex in startingInnerIndex..endingInnerIndex) {
            action(index, inner[innerIndex])
            index++
        }
    }
}
/** Performs the given [action] on each element within the [range], providing sequential index with the element. */
inline fun FastBooleanArray64.forEachInRangeIndexed(range: LongRange, action: (index: Long, Boolean) -> Unit) {
    if(range.first < 0 || range.last >= this.size) throw NoSuchElementException()
    //calculates the indices of the first and last elements in the range
    val outerIndexOfFirst = BigArrays.segment(range.first)
    val outerIndexOfLast = BigArrays.segment(range.last)
    val innerIndexOfFirst = BigArrays.displacement(range.first)
    val innerIndexOfLast = BigArrays.displacement(range.last)
    //performs the iteration
    var index = range.first
    for(outerIndex in outerIndexOfFirst..outerIndexOfLast) {
        val inner = this.array[outerIndex]
        val startingInnerIndex = if(outerIndex == outerIndexOfFirst) innerIndexOfFirst else 0
        val endingInnerIndex = if(outerIndex == outerIndexOfLast) innerIndexOfLast else BigArrays.SEGMENT_SIZE - 1
        for(innerIndex in startingInnerIndex..endingInnerIndex) {
            action(index, inner[innerIndex])
            index++
        }
    }
}
/** Performs the given [action] on each element within the [range], providing sequential index with the element. */
inline fun FastByteArray64.forEachInRangeIndexed(range: LongRange, action: (index: Long, Byte) -> Unit) {
    if(range.first < 0 || range.last >= this.size) throw NoSuchElementException()
    //calculates the indices of the first and last elements in the range
    val outerIndexOfFirst = BigArrays.segment(range.first)
    val outerIndexOfLast = BigArrays.segment(range.last)
    val innerIndexOfFirst = BigArrays.displacement(range.first)
    val innerIndexOfLast = BigArrays.displacement(range.last)
    //performs the iteration
    var index = range.first
    for(outerIndex in outerIndexOfFirst..outerIndexOfLast) {
        val inner = this.array[outerIndex]
        val startingInnerIndex = if(outerIndex == outerIndexOfFirst) innerIndexOfFirst else 0
        val endingInnerIndex = if(outerIndex == outerIndexOfLast) innerIndexOfLast else BigArrays.SEGMENT_SIZE - 1
        for(innerIndex in startingInnerIndex..endingInnerIndex) {
            action(index, inner[innerIndex])
            index++
        }
    }
}
/** Performs the given [action] on each element within the [range], providing sequential index with the element. */
inline fun FastCharArray64.forEachInRangeIndexed(range: LongRange, action: (index: Long, Char) -> Unit) {
    if(range.first < 0 || range.last >= this.size) throw NoSuchElementException()
    //calculates the indices of the first and last elements in the range
    val outerIndexOfFirst = BigArrays.segment(range.first)
    val outerIndexOfLast = BigArrays.segment(range.last)
    val innerIndexOfFirst = BigArrays.displacement(range.first)
    val innerIndexOfLast = BigArrays.displacement(range.last)
    //performs the iteration
    var index = range.first
    for(outerIndex in outerIndexOfFirst..outerIndexOfLast) {
        val inner = this.array[outerIndex]
        val startingInnerIndex = if(outerIndex == outerIndexOfFirst) innerIndexOfFirst else 0
        val endingInnerIndex = if(outerIndex == outerIndexOfLast) innerIndexOfLast else BigArrays.SEGMENT_SIZE - 1
        for(innerIndex in startingInnerIndex..endingInnerIndex) {
            action(index, inner[innerIndex])
            index++
        }
    }
}
/** Performs the given [action] on each element within the [range], providing sequential index with the element. */
inline fun FastDoubleArray64.forEachInRangeIndexed(range: LongRange, action: (index: Long, Double) -> Unit) {
    if(range.first < 0 || range.last >= this.size) throw NoSuchElementException()
    //calculates the indices of the first and last elements in the range
    val outerIndexOfFirst = BigArrays.segment(range.first)
    val outerIndexOfLast = BigArrays.segment(range.last)
    val innerIndexOfFirst = BigArrays.displacement(range.first)
    val innerIndexOfLast = BigArrays.displacement(range.last)
    //performs the iteration
    var index = range.first
    for(outerIndex in outerIndexOfFirst..outerIndexOfLast) {
        val inner = this.array[outerIndex]
        val startingInnerIndex = if(outerIndex == outerIndexOfFirst) innerIndexOfFirst else 0
        val endingInnerIndex = if(outerIndex == outerIndexOfLast) innerIndexOfLast else BigArrays.SEGMENT_SIZE - 1
        for(innerIndex in startingInnerIndex..endingInnerIndex) {
            action(index, inner[innerIndex])
            index++
        }
    }
}
/** Performs the given [action] on each element within the [range], providing sequential index with the element. */
inline fun FastFloatArray64.forEachInRangeIndexed(range: LongRange, action: (index: Long, Float) -> Unit) {
    if(range.first < 0 || range.last >= this.size) throw NoSuchElementException()
    //calculates the indices of the first and last elements in the range
    val outerIndexOfFirst = BigArrays.segment(range.first)
    val outerIndexOfLast = BigArrays.segment(range.last)
    val innerIndexOfFirst = BigArrays.displacement(range.first)
    val innerIndexOfLast = BigArrays.displacement(range.last)
    //performs the iteration
    var index = range.first
    for(outerIndex in outerIndexOfFirst..outerIndexOfLast) {
        val inner = this.array[outerIndex]
        val startingInnerIndex = if(outerIndex == outerIndexOfFirst) innerIndexOfFirst else 0
        val endingInnerIndex = if(outerIndex == outerIndexOfLast) innerIndexOfLast else BigArrays.SEGMENT_SIZE - 1
        for(innerIndex in startingInnerIndex..endingInnerIndex) {
            action(index, inner[innerIndex])
            index++
        }
    }
}
/** Performs the given [action] on each element within the [range], providing sequential index with the element. */
inline fun FastIntArray64.forEachInRangeIndexed(range: LongRange, action: (index: Long, Int) -> Unit) {
    if(range.first < 0 || range.last >= this.size) throw NoSuchElementException()
    //calculates the indices of the first and last elements in the range
    val outerIndexOfFirst = BigArrays.segment(range.first)
    val outerIndexOfLast = BigArrays.segment(range.last)
    val innerIndexOfFirst = BigArrays.displacement(range.first)
    val innerIndexOfLast = BigArrays.displacement(range.last)
    //performs the iteration
    var index = range.first
    for(outerIndex in outerIndexOfFirst..outerIndexOfLast) {
        val inner = this.array[outerIndex]
        val startingInnerIndex = if(outerIndex == outerIndexOfFirst) innerIndexOfFirst else 0
        val endingInnerIndex = if(outerIndex == outerIndexOfLast) innerIndexOfLast else BigArrays.SEGMENT_SIZE - 1
        for(innerIndex in startingInnerIndex..endingInnerIndex) {
            action(index, inner[innerIndex])
            index++
        }
    }
}
/** Performs the given [action] on each element within the [range], providing sequential index with the element. */
inline fun FastLongArray64.forEachInRangeIndexed(range: LongRange, action: (index: Long, Long) -> Unit) {
    if(range.first < 0 || range.last >= this.size) throw NoSuchElementException()
    //calculates the indices of the first and last elements in the range
    val outerIndexOfFirst = BigArrays.segment(range.first)
    val outerIndexOfLast = BigArrays.segment(range.last)
    val innerIndexOfFirst = BigArrays.displacement(range.first)
    val innerIndexOfLast = BigArrays.displacement(range.last)
    //performs the iteration
    var index = range.first
    for(outerIndex in outerIndexOfFirst..outerIndexOfLast) {
        val inner = this.array[outerIndex]
        val startingInnerIndex = if(outerIndex == outerIndexOfFirst) innerIndexOfFirst else 0
        val endingInnerIndex = if(outerIndex == outerIndexOfLast) innerIndexOfLast else BigArrays.SEGMENT_SIZE - 1
        for(innerIndex in startingInnerIndex..endingInnerIndex) {
            action(index, inner[innerIndex])
            index++
        }
    }
}
/** Performs the given [action] on each element within the [range], providing sequential index with the element. */
inline fun FastShortArray64.forEachInRangeIndexed(range: LongRange, action: (index: Long, Short) -> Unit) {
    if(range.first < 0 || range.last >= this.size) throw NoSuchElementException()
    //calculates the indices of the first and last elements in the range
    val outerIndexOfFirst = BigArrays.segment(range.first)
    val outerIndexOfLast = BigArrays.segment(range.last)
    val innerIndexOfFirst = BigArrays.displacement(range.first)
    val innerIndexOfLast = BigArrays.displacement(range.last)
    //performs the iteration
    var index = range.first
    for(outerIndex in outerIndexOfFirst..outerIndexOfLast) {
        val inner = this.array[outerIndex]
        val startingInnerIndex = if(outerIndex == outerIndexOfFirst) innerIndexOfFirst else 0
        val endingInnerIndex = if(outerIndex == outerIndexOfLast) innerIndexOfLast else BigArrays.SEGMENT_SIZE - 1
        for(innerIndex in startingInnerIndex..endingInnerIndex) {
            action(index, inner[innerIndex])
            index++
        }
    }
}