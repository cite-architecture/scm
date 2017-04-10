package edu.holycross.shot.scm

import edu.holycross.shot.cite._
import edu.holycross.shot.ohco2._
import scala.language.postfixOps

import scala.io.Source

/** Utility class for reading CITE repositories stored in
* local files in various formats, and converting their data to
* universal CITE Exchange format.
*/
object  LocalFileConverter {

  def textRepoFromFiles(invFileName: String, configFileName: String, baseDirectoryName: String): TextRepository = {
    TextRepositorySource.fromFiles(invFileName,configFileName,baseDirectoryName)
  }

  def textRepoFromPropertiesFile(propertiesFile: String) : TextRepository = {
    val propLines = Source.fromFile(propertiesFile).getLines.toVector.filterNot(_.startsWith("#"))
    val kvMap: Map[String,String] = propLines.map(_.split("=")).map(arr => arr(0) -> arr(1)) toMap
    
    TextRepositorySource.fromFiles(kvMap("inventory"), kvMap("citationconfig"), kvMap("basedir"))
  }

  def textCexFromFiles(invFileName: String, configFileName: String, baseDirectoryName: String,outputDelimiter: String = "\t"): String = {
    textRepoFromFiles(invFileName,configFileName,baseDirectoryName).cex(outputDelimiter)
  }

  def textCexFromPropertiesFile(propertiesFile: String,outputDelimiter: String = "\t"): String = {
    textRepoFromPropertiesFile(propertiesFile).cex(outputDelimiter)
  }



}
