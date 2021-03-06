package edu.holycross.shot.scm

import scala.io.Source

/** Factory for [[CiteLibrary]] objects.
*/
object  CiteLibrarySource {



  /** Create [[CiteLibrary]] from file in CEX format.
  *
  * @param fileName File of CEX data.
  * @param delimiter String value delimiting columns of CEX data.
  */
  def fromFile(fileName: String, delimiter: String = "#", delimiter2: String = ",",
  encoding: String = "UTF-8"): CiteLibrary = {

    val cex = Source.fromFile(fileName, encoding).getLines.mkString("\n")
    CiteLibrary.cex(cex, delimiter, delimiter2)
  }

  def fromUrl(url: String, delimiter: String = "#", delimiter2: String = ",",
  encoding: String = "UTF-8"): CiteLibrary = {
    val cex = Source.fromURL(url, encoding).getLines.mkString("\n")
    CiteLibrary.cex(cex, delimiter, delimiter2)
  }

}
