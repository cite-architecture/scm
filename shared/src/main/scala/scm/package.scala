package edu.holycross.shot
import scala.language.postfixOps

/**
* Provides classes for managing a collection of citable resources.
*
* The [[CiteRepository]] class contains metadata about a repository, and may optionally contain `TextRepository` or other CITE repository objects.
*
* The [[LocalFileConverter]] object includes methods for creating CEX
* representations of repositories stored in various formats.
*/
package object scm {

  /** Read required metadata fields from `citerepo` block
  * of `.cex` file into a Map.
  *
  * @param cex Metadata block (`!#citerepo`) of CEX file.
  * @param delimiter String separating keys from values.
  */
  def cexData(cex: String, delimiter: String = "\t"): Map[String, String] = {
   val cols = cex.split("\n").toVector.map(_.split(delimiter)).filter(_.size == 2)
   cols.map{ ar => ar(0) -> ar(1)  } toMap
 }

}
