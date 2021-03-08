A light-weight library for very large arrays which require more than 32 bits of indexing.

This library is a facade for the FastUtil BigArrays library, which is used in scientific computing.

## Features

This library mirrors the interface and feature set of Kotlin's standard library arrays.
Simply replace your Ints with Longs and you have working code.

- [x] Supports up to 2^58 elements for all types
- [x] Functional collections processing
- [x] Conversion to Java 8 Streams and ParallelStreams
- [x] Conversion to Kotlin Sequences
- [x] Primitive array types
- [x] Overloaded subscript operator for get/set