# Releases


**2.0.0**:  API breaking change in name of`CiteLibrary` class.  Support for version 7.4 of `ohco2` library and version 2.0 of `cex` parsing library simpifies access to data in CEX format, and improves validation of TextRepository contents.


**1.3.3**: Fixes a bug where correct delimiter was not being passed to Catalog constructor, and adds unit tests.

**1.3.2**: Fixes some formatting in API documentation.

**1.3.1**: Minor adjustments of imports.  Adds complete API documentation.

**1.3.0**:  Adds option of using `LocalFileConverter` with a single properties file.

**1.2**:  Adds `LocalFileConverter` for instantiating repository objects and generating CEX Strings from data in local files in a variety of formats.

**1.1**: Adds `CiteRepositorySource` for reading `.cex` data and instantiating a `CiteRepository` from a local file.


**1.0**: Initial release supports creating a CITE Repository from a `.cex` file including data for building a text repository.
