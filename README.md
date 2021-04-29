## Array64
Array64 is a light-weight library for very large arrays with an interface closely mirroring that of Kotlin standard library arrays.

Array64 seeks to be three things: easy to use, easy to adapt existing logic to, and brutally efficient.

## Features
Array64 provides highly optimized implementations of 64-bit arrays for generic and primitive types, 
as well as utilities for IO and functional data processing (à la Java 8 Streams and Kotlin Sequences).

- [x] Array types which can store a virtually unlimited number of elements (2<sup>58</sup> or 2<sup>64</sup>-1 depending on the implementation)
- [x] Both resizable and fixed-size arrays (planned)
- [x] Both generic and primitive array types
- [x] Overloaded subscript operator for get/set
- [x] Efficient iterators and higher order functions for all types
- [x] Utilities for reading and writing to I/O streams and files

## API Overview

Two implementations - FastArray64 and UnsafeArray64 (planned).

FastArray64:
- Supports generic and primitive arrays.
- Internally uses a 2D array. Partially built on the functionality of [FastUtil's BigArrays module](https://fastutil.di.unimi.it/docs/it/unimi/dsi/fastutil/BigArrays.html).
- Highly optimized for sequential access. Still relatively fast for random access.
- In the unlikely event none of the provided functions quite suit your needs, the internal 2D array is accessible from public inline functions as part of
  the published API. Refer to the FastUtil documentation for usage notes.
  
UnsafeArray64 (planned):
- Supports only primitive arrays.
- Optimized for random access, but slow for sequential access.
- Stores contents in an off-heap array, meaning it is not limited by and does not contribute to the JVM heap space.
- Directly manipulates memory through the [sun.misc.Unsafe](https://www.baeldung.com/java-unsafe) class and handles garbage
  collection through the [java.lang.ref.Cleaner](https://docs.oracle.com/javase/9/docs/api/java/lang/ref/Cleaner.html) class.
  
## Important Usage and Performance Notes
- Direct iteration over the indices of an Array64 is inefficient (especially FastArray64). 
  Users should use either higher order extension functions like forEach/forEachInRange or iterators.
- Higher order extension functions for both the Array64 interface and all concrete types are provided. The extension functions
  for concrete types are able to apply implementation-specific optimizations and should be preferred.
- Generally speaking, the higher order extension functions for concrete types perform better than iterators.
  (A good rule of thumb is to choose higher order functions unless you need to wrap something in a run block, Runnable/Callable, etc.)
- When using iterators for primitive arrays, use the type-specific nextX/previousX/setX (nextByte, nextChar) methods rather than
  plain next/previous/set in order to avoid penalties from boxing primitives.
