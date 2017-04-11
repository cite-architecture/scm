package edu.holycross.shot.scm

import edu.holycross.shot.cite._
import edu.holycross.shot.ohco2._

import scala.scalajs.js
import js.annotation.JSExport



/** A repository of citable scholarly resources.
*
* @param name Name of the citable data set.
* @param version Version identifier.
* @param license Licensing of the data.
* @param textRepository Optional, cataloged corpus of citable texts.
*/
@JSExport  case class  CiteRepository (name: String, version: String, license: String, textRepository: Option[TextRepository] = None ) {

  /** True if TextRepository is instantiated.
  */
  def hasTexts: Boolean = {
    textRepository match {
      case None => false
      case t: Some[TextRepository] => true
    }
  }

}


/** Factory for creating [[CiteRepository]] objects from
* a String in CEX format.
*/
object CiteRepository {


  /** Create a [[CiteRepository]].
  *
  * @param cex Data in CITE Exchange format.
  * @param delimiter String value delimiting columns of CEX data.
  */
  def apply(cex: String, delimiter: String)  : CiteRepository = {
    val sections = cex.split("#!").filter(_.nonEmpty)
    val typeList = sections.map(_.split("\n")(0))

    val metadataIdx = typeList.indexOf("citerepo")
    val metadataBlock = sections(metadataIdx).drop(1)
    val metadataMap = cexData(metadataBlock,delimiter)

    val textRepo = {
      typeList.indexOf("ctscatalog") match {
        case -1 => None
        case _ =>  {
          // omit section labels
          val catalogString = sections(typeList.indexOf("ctscatalog"))
          val catalogData = catalogString.split("\n").drop(1).mkString("\n")
          val catalog = Catalog(catalogData,delimiter)

          val corpusString = sections(typeList.indexOf("ctsdata"))
          val corpusData = corpusString.split("\n").drop(1).mkString("\n")
          val corpus = Corpus(corpusData, delimiter)
          Some(TextRepository(corpus,catalog))
        }
      }
    }

    CiteRepository(metadataMap("name"),metadataMap("version"),metadataMap("license"), textRepo)
  }

}
