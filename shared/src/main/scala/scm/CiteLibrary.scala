package edu.holycross.shot.scm

import edu.holycross.shot.cite._
import edu.holycross.shot.ohco2._
import edu.holycross.shot.citeobj._
import edu.holycross.shot.cex._
import edu.holycross.shot.citerelation._

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
@JSExport  case class  CiteLibrary (
  name: String,
  urn: Cite2Urn,
  license: String,
  textRepository: Option[TextRepository] = None,
  collectionRepository: Option[CiteCollectionRepository] = None,
  imageExtensions: Option[ImageExtensions] = None,
  relationSet: Option[CiteRelationSet] = None
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

  /** Create map of required library configuration values from CEX source.
  * CEX `citelibrary` block must include name and license configuration, and
  * a valid versioned URN identifying the library.
  *
  * @param cex Parsed CEX source
  * @param delimiter Column-delimeter used in CEX source.
  */
  def libConfigMapFromCex(cex: CexParser, delimiter: String): Map[String, String] = {
    val libContent = cex.blockString("citelibrary").split("\n").toVector
    if (libContent.size < 2) {
      throw CiteLibraryException("CEX source must include `citelibrary` block")
    } else {
      val libPairs = libContent.map(_.split(delimiter)).filter(_.size == 2).map(ar => ar(0) -> ar(1))
      val configMap = libPairs.toMap

      require(configMap.keySet.contains("name"), "CEX `citelibrary` block must include value for library name")
      require(configMap.keySet.contains("license"), "CEX `citelibrary` block must include a licensing statement")
      require(configMap.keySet.contains("urn"), "CEX `citelibrary` block must include a URN")

      val u = Cite2Urn(configMap("urn"))
      u.versionOption match {
        case None => throw CiteLibraryException("URN must include version identifier")
        case _ => configMap
      }
    }
  }

  def collectionRepoFromCex(cexString: String, delimiter: String = "#", delimiter2 : String = ","): Option[CiteCollectionRepository] = {
    val cex = CexParser(cexString)
    val catalogCex = cex.blockString("citecatalog")

    if (catalogCex.size < 1) {
      None
    } else {
      Some(CiteCollectionRepository(cexString,delimiter,delimiter2))
    }


  }

  /** Create optional text repository from CEX source.
  *
  * @param cex Parsed CEX source.
  * @param delimiter  Column-delimeter used in CEX source.
  */
  def textRepoFromCex(cex: CexParser, delimiter: String) : Option[TextRepository] = {

    val catalog = Catalog(cex.blockString("ctscatalog"),delimiter)
    val corpus = Corpus(cex.blockString("ctsdata"), delimiter)

    if ((catalog.size > 0) && (corpus.size > 0)) {
      Some(TextRepository(corpus,catalog))
    } else {
      None
    }
  }


  /** Create a [[CiteLibrary]].
  *
  * @param cexString Data in CITE Exchange format.
  * @param delimiter String value delimiting columns of CEX data.
  * @param delimiter2 Secondary delimiter separating values with a  column of CEX data.
  */
  def apply(cexString: String, delimiter: String, delimiter2: String)  : CiteLibrary = {
    val cex = CexParser(cexString)
    val libMap = libConfigMapFromCex(cex, delimiter)
    val textRepo = textRepoFromCex(cex, delimiter)
    val collectionRepo = None //collectionRepoFromCex(cexString,delimiter,delimiter2)
    val imgExtensions = None //ImageExtensions(cexString,delimiter)
    val relationSet = None

    CiteLibrary(libMap("name"),Cite2Urn(libMap("urn")),libMap("license"), textRepo,collectionRepo,imgExtensions)
  }

}
