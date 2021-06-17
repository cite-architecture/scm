# `scm`:  Scala CITE manager


## What it is

`scm` is a cross-platform library for managing an archive of citable scholarly resources.

It provides facilities for reading source data from CEX and other formats, and for serializing citable data to CEX.  The Scala CITE manager simplifies integrating texts, collections, relations, and image extensions in a single environment, including preparing the contents of a digital library for serving with [Scala Cite Services (scs)](https://github.com/cite-architecture/scs).

## Current version: 7.4.1


Status:  **active development**. [Release notes](releases.md)

## License

[GPL 3.0](https://opensource.org/licenses/gpl-3.0.html)

## Using, building and testing

`scm` can be built for both the JVM and ScalaJS using any version of Scala from 2.11 onwards.  Binaries for all three versions are available from the Nexus repository on <terracotta.hpcc.uh.edu/nexus>.

If you are using sbt, include `Resolver.jcenterRepo` in your list of resolvers

```scala
	resolvers += "Nexus" at "https://terracotta.hpcc.uh.edu/nexus/repository/maven-releases/",
```

and  add this to your library dependencies:

    "edu.holycross.shot.cite" %% "scm" % VERSION

For maven, ivy or gradle equivalents, refer to <https://bintray.com/neelsmith/maven/ohco2>.



To build from source and test, use normal sbt commands (`compile`, `test` ...)

Apply a task to all cross-compiled versions by preceding it with `+ `.  For example, `sbt "+ test"`

## Documentation

- [User guide](https://cite-architecture.github.io/scm/)
- [API docs](https://cite-architecture.github.io/cite-api-docs/scm/api/edu/holycross/shot/scm/index.html)
