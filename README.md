Array64 is a light-weight library for very large arrays with an interface closely mirroring that of Kotlin standard library arrays.

<!-- TODO: add badges for maven and begin using a CI tool -->

Array64 seeks to be three things: easy to use, easy to adapt existing logic to, and optimally efficient.

## Overview
<!-- TODO: Table of contents -->

## Essential Information
### Accessing the Documentation
The complete KDoc can be accessed at https://millibyte1.github.io. The complete JavaDoc (generated via the kotlin-as-java plugin for Dokka) can be accessed here as well.
### Binary Releases
Published releases are available on Maven Central.

    <dependency>
        <groupId>io.github.millibyte1</groupId>
        <artifactId>array64</artifactId>
        <version>1.0.2</version>
    </dependency>

A direct download is also available [here](https://repo1.maven.org/maven2/io/github/millibyte1/array64).
### Build
This project is written entirely in Kotlin and depends on both Kotlin JDK 7 and Kotlin JDK 8.
It also depends on Apache Commons IO and FastUtil, though these dependencies may be removed in a later version.
### License
This project uses the MIT License. See the 'LICENSE' file in the root directory of the project.
### Bug Reporting and Patches
Please report any bugs you encounter on the GitHub issue tracker for this repository. Patches can be submitted as pull requests
with a descriptive title.
### Contributing to the Project
If you are interested in contributing to the project, [send an email to the author](mailto:ebhall99@gmail.com?subject=[GitHub]%20Array64).

## Using the Library
### API Overview
#### Interfaces
The base Array64 interface extends Iterable and defines operations for copying, checking size, getting/setting (with 
subscript operator in Kotlin), and obtaining iterators.

For each primitive type X, a subinterface XArray64 extending Array64&lt;X&gt; is provided with overloaded operations
using primitives rather than boxed types and returning type-specific iterators rather than generic ones.

Resizable arrays are not included in this initial release but are planned for the next release. A ResizableArray64 subinterface of Array64
will be provided which defines operations for resizing an array with a default fill value and for adding and removing
elements. Subinterfaces for each primitive type will also be provided.

#### Iterators
LongIndexedIterator - a simple subinterface of java.util.Iterator which defines an operation for getting the current index.

The base Array64Iterator interface extends LongIndexedIterator and defines operations for traversing in both directions and
setting the element at the current index.

For each primitive type X, a subinterface XArray64Iterator extending Array64Iterator&lt;X&gt; is provided with overloaded operations
using primitives rather than boxed types. 

#### Implementation Classes

#### Higher Order Functions

#### IO Utilities


### Kotlin Examples
### Java Examples
### Important Performance Notes
- Direct iteration over the indices of an Array64 is inefficient (especially FastArray64).
  Users should use either higher order extension functions like forEach/forEachInRange or iterators (note that foreach *loops*
  implicitly use iterators).
- Higher order extension functions for both the Array64 interface and all concrete types are provided. The extension functions
  for concrete types are able to apply implementation-specific optimizations and should be preferred.
- Generally speaking, the higher order extension functions for concrete types perform better than iterators.
  (A good rule of thumb is to choose higher order functions unless you need to wrap something in a run block, Runnable/Callable, etc.)
- When using iterators for primitive arrays, use the type-specific nextX/previousX/setX (nextByte, nextChar) methods rather than
  plain next/previous/set in order to avoid performance penalties from boxing primitives.