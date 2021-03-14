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

## Important Notes
- Never directly iterate over the indices or elements of an Array64.
Instead use forEach and forEachInRange.
- Use the provided higher order functions for collections processing when possible.
- In the unlikely event none of the provided functions suit your needs, the internal 2D array is accessible as part of
the published API. Refer to the [FastUtil documentation](https://fastutil.di.unimi.it/docs/it/unimi/dsi/fastutil/BigArrays.html)
for usage notes.
