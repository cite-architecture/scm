package edu.holycross.shot.scm

import edu.holycross.shot.cite._
import edu.holycross.shot.ohco2._

import scala.io.Source

/** Factory for [[CiteRepository]] objects.
*/
object  CiteRepositorySource {

  def fromFile(fileName: String, delimiter: String = "\t"): CiteRepository = {
    val cex = Source.fromFile(fileName).getLines.mkString("\n")
    CiteRepository(cex, delimiter)
  }
}
