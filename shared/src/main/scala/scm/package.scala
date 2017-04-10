package edu.holycross.shot
import scala.language.postfixOps

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
