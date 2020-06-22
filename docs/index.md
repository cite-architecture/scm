---
layout: page
title: Scala CITE manager
---

**Version 7.3.0**.


A `CiteLibrary` contains required metadata about a library, and may optionally contain a `TextRepository`, a `CiteCollectionRepository` or other CITE repository objects.


## Required metadata

Every `CiteLibrary` has a human-readable name, a URN identifying the library, a license statement, and a list of `CiteNamespaces`s, which map the abbreviated CITE namespaces of CITE URNs to full URIs.

This illustrates a minimal library: one with metadata only, but no contents. In addition to importing `scm`, we need to be able to create a `Cite2Urn` and a `URI`, so we'll have three import statements.

```scala
import edu.holycross.shot.scm._
import edu.holycross.shot.cite._
import java.net.URI

val name= "Absolute minimum CITE library"
// name: String = "Absolute minimum CITE library"
val urn = Cite2Urn("urn:cite2:scm:guideexamples.v1:minimum")
// urn: Cite2Urn = Cite2Urn("urn:cite2:scm:guideexamples.v1:minimum")
val license = "Public domain"
// license: String = "Public domain"
val citeNamespaces = Vector(
    CiteNamespace("scm", new URI("http://cite-architecture.org/demons/scm"))
  )
// citeNamespaces: Vector[CiteNamespace] = Vector(
//   CiteNamespace("scm", http://cite-architecture.org/demons/scm)
// )
```

This is enough to create a syntactically valid `CiteLibrary`.

```scala
val library =  CiteLibrary(name, urn, license, citeNamespaces)
```


Alternatively, the `CiteLibrary` object can create a library from a String in CEX format defining the required metadata in a `citelibrary` block.

Here's an equivalent library created from a CEX string.

```scala

val cexString = """
#!citelibrary
name#Absolute minimum CITE library
urn#urn:cite2:scm:guideexamples.v1:minimum
license#Public domain
namespace#scm#http://cite-architecture.org/demons/scm
"""
val citeLibrary = CiteLibrary(cexString)
```
