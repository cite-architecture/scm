package edu.holycross.shot.scm

import scala.io.Source

/** Factory for [[CiteRepository]] objects.
*/
object  CiteRepositorySource {

  /** Create [[CiteRepository]] from file in CEX format.
  *
  * @param fileName File of CEX data.
  * @param delimiter String value delimiting columns of CEX data.
  */
  def fromFile(fileName: String, delimiter: String = "\t"): CiteRepository = {
    val cex = Source.fromFile(fileName).getLines.mkString("\n")
    CiteRepository(cex, delimiter)
  }
  
}
