# Releases

**5.1.1**:  Incorporate bug fixes in library dependencies.

**5.1.0**:  Adds `DataModel` class and support for CEX `datamodels` block.

**5.0.1**: Harmonizes versions of all dependencies so support ScalaJS 0.6.18 and Scala 2.12.3.

**5.0.0**: Update to version 10.1.0  of `ohco2` introduces API-breaking changes.  Updated build now compiles for all versions of scala 2.11 or higher.

**4.2.0**: Update to `citeobj` library adds new implementations of `BinaryImageSource`.

**4.1.1**: Update `cex` library with bug fix.

**4.1.0**: Update `cex` library to support version 3.0 of CEX specification.

**4.0.1**: Update `citeobj` library with fixes to bugs in searching citable objects and repositories.

**4.0.0**: Add support for CITE namespace information in CEX 2.0 sources.

**3.7.0**:  Update to `ocho2` library now supports version 2.0 of CEX format.

**3.6.0**: Add support for CITE relations (indexes).  Updated dependency on `citeobj` library adds repository-level
querying on CITE Collections data.

**3.5.0**: Updated dependency on `citeobj` library adds repository-level `~~` function.

**3.4.0**: Updated dependency on `citeobj` library adds access to CITE property definitions as part of `CiteObject`.

**3.3.0**: Updated dependency on `citeobj` library adds functionality to `ImageExtensions` to select by protocol, or for individual collections.

**3.2.1**: Updated dependency on `citeobj` library fixes bugs in processing CEX data for image extensions and for CITE Collection catalogs.

**3.2.0**:  Add image extensions to `CiteLibrary` class.

**3.1.0**: Updates to `citeobj` library add new functionality.

**3.0.2**:  Updates dependency to `citeobj` library, and adds tests on use of `citeobj` in correctly exported ScalaJS environment.

**3.0.1**:  Corrects an error in exposing classes to javascript environment.

**3.0.0**: Supports version 3.0 of CEX specification, including creation of `CiteCollectionRepository`s.  Constructing a `CiteLibary` now requires specification of a secondary delimiter for possible use in controlled vocabulary lists in addition to the primary delimiting string in CEX sources.

**2.1.2**:  Adds tests for generating corpus from markdown source.

**2.1.1**: Updates `ohco2` dependency to version 8.0.1.  Documents test data sets more thoroughly in coordination with github project wiki.

**2.1.0**: Updates `ohco2` dependency to version 8.0.  This adds new functionality to the library's text repository especially related to tokenization of text contents.  Adds placeholder for planned CiteCollectionRepository option.

**2.0.2**: Corrects yet another linking error in API documentation.

**2.0.1**: Corrects an error in API documentation.

**2.0.0**:  API breaking change in name of`CiteLibrary` class.  Support for version 7.4 of `ohco2` library and version 2.0 of `cex` parsing library simpifies access to data in CEX format, and improves validation of TextRepository contents.


**1.3.3**: Fixes a bug where correct delimiter was not being passed to Catalog constructor, and adds unit tests.

**1.3.2**: Fixes some formatting in API documentation.

**1.3.1**: Minor adjustments of imports.  Adds complete API documentation.

**1.3.0**:  Adds option of using `LocalFileConverter` with a single properties file.

**1.2**:  Adds `LocalFileConverter` for instantiating repository objects and generating CEX Strings from data in local files in a variety of formats.

**1.1**: Adds `CiteRepositorySource` for reading `.cex` data and instantiating a `CiteRepository` from a local file.


**1.0**: Initial release supports creating a CITE Repository from a `.cex` file including data for building a text repository.
