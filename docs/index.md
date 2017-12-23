---
title: SCM home
layout: page
---

## CITE library manager: overview

1. A `CiteLibrary` contains metadata about a library, and may optionally contain a `TextRepository` a `CiteCollectionRepository` or other CITE repository objects.  Create a `CiteLibrary` from text formatted in CITE Exchange (`cex`) format, or, in a JVM environment, use a `CiteLibrarySource` to create a `CiteLibrary` from a file with data in `.cex` format.  See [some examples](creating-a-library).
2. In the JVM subproject, the `LocalFileConverter` can create CEX representations of repositories from local files.  For texts, the  `LocalFileConverter` can create either a `TextRepository` object or a CEX string from XML text files cataloged with a CTS TextInventory and CitationConfiguration file. See [some examples](building-from-local-files)
3. To configure your sbt build to use `scm`, see [these guidelines](configuration).
