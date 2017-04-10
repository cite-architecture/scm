package edu.holycross.shot.scm

import edu.holycross.shot.ohco2._
import scala.language.postfixOps
import scala.io.Source

/** Utility class for reading CITE repositories stored in
* local files in various formats, and converting their data to
* universal CITE Exchange format.
*/
object  LocalFileConverter {

  /** Create a [[TextRepository]] from cataloged local files.
  *
  * @param invFileName Name of XML file validating against CTS TextInventory schema.
  * @param configFileName Name of XML file validating against CTS CitationConfiguration schema.
  * @param baseDirectoryName Root directory for locally stored files.  References in the citation configuration file are relative to this directory.
  */
  def textRepoFromFiles(invFileName: String, configFileName: String, baseDirectoryName: String): TextRepository = {
    TextRepositorySource.fromFiles(invFileName,configFileName,baseDirectoryName)
  }


  /** Create a [[TextRepository]] from cataloged local files
  * identified with a single property file.
  *
  * @param propertiesFile File in java property file format
  * identifying text inventory, citation configuration, and base
  * directory for local files.
  */
  def textRepoFromPropertiesFile(propertiesFile: String) : TextRepository = {
    val propLines = Source.fromFile(propertiesFile).getLines.toVector.filterNot(_.startsWith("#"))
    val kvMap: Map[String,String] = propLines.map(_.split("=")).map(arr => arr(0) -> arr(1)) toMap

    TextRepositorySource.fromFiles(kvMap("inventory"), kvMap("citationconfig"), kvMap("basedir"))
  }



  /** Create CEX data representing a text repository
  * from cataloged local files.
  *
  * @param invFileName Name of XML file validating against CTS TextInventory schema.
  * @param configFileName Name of XML file validating against CTS CitationConfiguration schema.
  * @param baseDirectoryName Root directory for locally stored files.  References in the citation configuration file are relative to this directory.
  */
  def textCexFromFiles(invFileName: String, configFileName: String, baseDirectoryName: String,outputDelimiter: String = "\t"): String = {
    textRepoFromFiles(invFileName,configFileName,baseDirectoryName).cex(outputDelimiter)
  }



  /** Create CEX data representing a text repository from
  * cataloged local files identified with a single property file.
  *
  * @param propertiesFile File in java property file format
  * identifying text inventory, citation configuration, and base
  * directory for local files.
  */
  def textCexFromPropertiesFile(propertiesFile: String,outputDelimiter: String = "\t"): String = {
    textRepoFromPropertiesFile(propertiesFile).cex(outputDelimiter)
  }

}
