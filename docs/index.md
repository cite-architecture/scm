---
title: SCM home
layout: page
---

## CITE library manager: overview

A `CiteLibrary` contains metadata about a library, and may optionally contain a `TextRepository` a `CiteCollectionRepository` or other CITE repository objects.




1. [Creating a `CiteLibrary`](creating-a-library).
2. In the JVM subproject, the `LocalFileConverter` can create CEX representations of repositories from local files.  For texts, the  `LocalFileConverter` can create either a `TextRepository` object or a CEX string from XML text files cataloged with a CTS TextInventory and CitationConfiguration file. See [some examples](building-from-local-files)
3. To configure your sbt build to use `scm`, see [these guidelines](configuration).

## Other documentation

- [Current API docs](api/edu/holycross/shot/scm/index.html)
