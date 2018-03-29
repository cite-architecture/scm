---
layout:  page
title:  Building a library from local files  
---


On the JVM platform, the `LocalFileConverter` object can convert online data in a variety of formats to repository objects you can include in a `CiteLibrary` or serialize directory to CEX.  The `scm` library supports building repositories of canonically citable texts from online sources.



## Note on versions

The following documentation applies to `scm` versions 2.1.1 through 4.2.0.  Beginning with version 5.0.0, `scm` and the included library `ohco2` no longer use XML files
to configure how local files should be converted to a `CiteLibrary`.

## Building a Text Repository from local files




To build a `TextRepository` object from local files, you must catalog your texts in two XML files:  a text inventory documents how the work hierarchy is labelled and cited;  a citation configuration file documents how the passage hierarchy is mapped onto a specific file format. These documents must validate against version 6.0 of the CTS schemas.

In version 2.1.1, local files may either be XML or Markdown.  For XML documents, you must specify what values in the document correspond to citation values.  For Markdown documents, the citation values are generated automatically from the document's structure.

To create a `TextRepository`, you specify the locations of these cataloging files and the root directory where your editions are found.  You may either pass these items directly to a constructor, like so:

    val textRepository = LocalFileConverter.textRepoFromFiles(
       "jvm/src/test/resources/markdownrepository/inventory.xml",
       "jvm/src/test/resources/markdownrepository/citationconfig.xml",
       "jvm/src/test/resources/markdownrepository/texts"
    )

or you may define them in a properties files, and construct the repository object with it

    val textRepository = LocalFileConverter.textRepoFromPropertiesFile(propertiesFile)

Of course you can serialize a `TextRepository` to CEX format, but as a shortcut, the `LocalFileConverter` offers functions to serialize a set of local files directly to CEX format.  Here too you may either specify the necessary parameters directly, like this:


    val cex = LocalFileConverter.textCexFromFiles(
       "jvm/src/test/resources/markdownrepository/inventory.xml",
       "jvm/src/test/resources/markdownrepository/citationconfig.xml",
       "jvm/src/test/resources/markdownrepository/texts"
    )

or with a reference to a properties file:


    val cex = LocalFileConverter.textCexFromPropertiesFile(propertiesFile)

You can find examples of online repositories with catalogs and properties files in the test data in these directories:

- `jvm/src/test/resources/xmlrepository`
- `jvm/src/test/resources/markdownrepository`
