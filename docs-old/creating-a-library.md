---
title:  Creating a CITE library
layout: page
---


Import the library:

```scala
import edu.holycross.shot.scm._
```


The simplest way to create a `CiteLibrary` is from data in CITE Exchange format (CEX). If `cex` is a string of data in CEF format, then you can create a new library like this:


```scala
   val cex = "#!citelibrary\nname#demo\nurn#urn:cite2:cex:democex.2017_1:test\nlicense#public domain\n"

    val citeLibrary = CiteLibrary(cex, "#", ",")
```


This functionality is identical on Javascript and JVM platforms.

In the JVM only, you can directly create a library from a file using the `CiteLibrarySource` object. If `cexfilename` is the name of a file with data in CEX format, then you can create a `CiteLibrary` with the `fromFile` function like this:

    val val citeLibrary = CiteLibrarySource.fromFile(cexfilename)

## CEX and `CiteLibrary`

To quote the  CEX specification,

> The CITE Exchange format is a plain-text, line-oriented data format for serializing citable content following the models of the CITE Architecture. Distinct types of content are grouped in separate labelled blocks ... so that a single CEX source can integrate any content citable in the CITE Architecture.


CEX is a very flexible format:  all blocks are optional.   You can find the full CEX specification [here](https://cite-architecture.github.io/citedx/):  this page summarizes restrictions you must observe in your CEX data source in order to use it to create a `CiteLibrary` object.


1. The CEX source *must* include  a`#!citelibrary` block.
2. To include a TextRepository in the library, you must include *both* a `#!ctscatalog` and a `#ctsdata` block.
3.  To include a CiteCollectionRepository in the library, you must include *both* a `#!citecatalog` and a `#citedata` block.  (**Not yet implemnted in version 2.1**.)



## Examples of CEX files

The test data sources in the JVM subproject of this repository include examples of CEX files you can study: `jvm/src/test/resources/cex`
