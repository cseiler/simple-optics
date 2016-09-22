# simple-optics
A simple library for Lenses (and Views and OptionalLenses and OptionalViews) in Java

"A lens is basically a getter/setter that can be used for deep updates of immutable data." http://davids-code.blogspot.de/2014/02/immutable-domain-and-lenses-in-java-8.html  (Disclaimer: I am not the author of this post)

There are a couple of excellent Optics libraries in Functional Programming world. However to use these constructs in Java, 
you would usually have to use some larger framework or library, which in turn introduces more dependencies and generally 
requires a "buy in" in their models (often incompatible with the standard JDK framework) (YMMV).

The aim of this library is to have usable Optics in standard Java without any further dependencies.

The original Optic class is a *Lens* (as quoted "basically a getter/setter") which is composable and hence shines for 
nested data structures. However not all fields have both, getters and setters. Therefore a *View* is just the getter part of a lens. Every Lens is also a View.

The original post omitted the problem of *null* fields, so OptionalLenses (and OptionalViews) provide a safe way to access fields which might be null.

Note that the Optics herein work as well with mutable as with immutable data structures.

Also note that there are classes for the primite types of int, long and double. For some reasons there are no primitive Optionals for the other primitive types and creating OptionalBoolen etc. is beyond this lib.
