package edu.holycross.shot.scm

import edu.holycross.shot.cite._
import edu.holycross.shot.ohco2._
import edu.holycross.shot.citeobj._
import edu.holycross.shot.cex._

import scala.scalajs.js
import js.annotation.JSExport



/** A library of citable scholarly resources.
*
* @param name Name of the citable library.
* @param urn URN identifying the library.
* @param license Licensing and rights information.
* @param textRepository Optional, cataloged corpus of citable texts.
* @param collectionRepository Optional, cataloged set of CITE Collections. (Not used in current version.)
*/
@JSExport  case class  CiteLibrary (name: String, urn: Cite2Urn, license: String, textRepository: Option[TextRepository] = None,
collectionRepository: Option[CiteCollectionRepository] = None
 ) {

  /** True if TextRepository is instantiated.
  */
  def hasTexts: Boolean = {
    textRepository match {
      case None => false
      case t: Some[TextRepository] => true
    }
  }

/*
  def find(s: String): Corpus = {
    textRepository match {
      case None => Corpus(Vector.empty)
      case _ => Corpus(textRepository.find(s))
    }
  }
  */

}


/** Factory for creating [[CiteLibrary]] objects from
* a String in CEX format.
*/
object CiteLibrary {


  /** Create a [[CiteLibrary]].
  *
  * @param cexString Data in CITE Exchange format.
  * @param delimiter String value delimiting columns of CEX data.
  */
  def apply(cexString: String, delimiter: String)  : CiteLibrary = {
    val cex = CexParser(cexString)


    val libContent = cex.block("citelibrary").flatMap(_.split("\n")) //.getOrElse("").split("\n").toVector
    val libPairs = libContent.map(_.split(delimiter)).filter(_.size == 2).map(ar => ar(0) -> ar(1))
    val libMap = libPairs.toMap

    val catalog = Catalog(cex.block("ctscatalog").mkString("\n"),delimiter)
    val corpus = Corpus(cex.block("ctsdata").mkString("\n"), delimiter)

    val textRepo = {
      if ((catalog.size > 0) && (corpus.size > 0)) {
        Some(TextRepository(corpus,catalog))
      } else {
        None
      }
    }

    val collectionRepo = None
    CiteLibrary(libMap("name"),Cite2Urn(libMap("urn")),libMap("license"), textRepo,collectionRepo)
  }

}
