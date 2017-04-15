# `scm`:  Scala CITE manager


## What it is

`scm` is a cross-platform library for managing an archive of CITE resources, including preparing content for serving with [Scala Cite Services (scs)](https://github.com/cite-architecture/scs).


## Current version: 2.1.0


Status:  **active development**. [Release notes](releases.md)

## License

[GPL 3.0](https://opensource.org/licenses/gpl-3.0.html)

## Using, building and testing

`scm` is compiled for both the JVM and ScalaJS using scala 2.11.  Binaries for all platforms are available from jcenter.  If you are using sbt, include `Resolver.jcenterRepo`in your list of resolvers

    resolvers += Resolver.jcenterRepo

and  add this to your library dependencies:

    "edu.holycross.shot.cite" %% "scm" % VERSION

For maven, ivy or gradle equivalents, refer to <https://bintray.com/neelsmith/maven/ohco2>.



To build from source and test, use normal sbt commands (`compile`, `test` ...)

Apply a task to all cross-compiled versions by preceding it with `+ `

## Using `scm`

Main classes:

1. a `CiteRepository` contains metadata about a repository, and may optionally contain `TextRepository` or other CITE repository objects.  Create a `CiteRepository` from text formatted in CITE Exchange (`cex`) format, or use a `CiteRepositorySource` to create a `CiteRepository` from a file in `.cex` format.
2. the `LocalFileConverter` can create CEX representations of repositories from local files.  For texts, the  `LocalFileConverter` can create either a `TextRepository` object or a CEX strings from XML text files cataloged with a CTS TextInventory and CitationConfiguration file.


To use these classes in your own encode, import both `edu.holycross.shot.ohco2._` (available from jcenter), and `edu.holycross.shot.hocuspocus._`, available from the nexus repository at beta.hppc.uh.edu.  E.g., with sbt, include these resolvers in your build file:

    resolvers += Resolver.jcenterRepo,
    resolvers += "beta" at "http://beta.hpcc.uh.edu/nexus/content/repositories/releases"
