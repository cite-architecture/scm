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
* @param imageExtensions Optional list of binary image implementations.
* @param relationSet Optional set of triple statements.
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

  /** True if CiteCollectionRepository is instantiated.
  */
  def hasCollections: Boolean = {
    collectionRepository match {
      case None => false
      case t: Some[CiteCollectionRepository] => true
    }
  }


  /** True if ImageExtensions is instantiated.
  */
  def hasImageExtensions: Boolean = {
    imageExtensions match {
      case None => false
      case t: Some[ImageExtensions] => true
    }
  }


  /** True if CiteRelationSet is instantiated.
  */
  def hasIndexes : Boolean = {
    relationSet match {
      case None => false
      case t: Some[CiteRelationSet] => true
    }
  }

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
  * @param delimiter Column-delimiter used in CEX source.
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



  /** Create optional text repository from CEX source.
  *
  * @param cex Parsed CEX source.
  * @param delimiter  Column-delimiter used in CEX source.
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

  /** Create optional CITE Collecctions repository from CEX source.
  *
  * @param cex Parsed CEX source.
  * @param delimiter  Column-delimiter used in CEX source.
  * @param delimiter2  Item-delimiter used within a column in CEX source.
  */
  def collectionRepoFromCex(cexString: String, delimiter: String = "#", delimiter2 : String = ","): Option[CiteCollectionRepository] = {
    val cex = CexParser(cexString)
    val catalogCex = cex.blockVector("citecatalog")

    if (catalogCex.size < 1) {
      None
    } else {
      val ccr = CiteCollectionRepository(cexString,delimiter,delimiter2)
      Some(ccr)
    }
  }

  /** Create optional CITE Collecctions repository from CEX source.
  *
  * @param cex Parsed CEX source.
  * @param delimiter  Column-delimiter used in CEX source.
  * @param delimiter2  Item-delimiter used within a column in CEX source.
  */
  def relationsFromCex(cex: String, delimiter: String = "#"): Option[CiteRelationSet] = {
    val relations = CiteRelationSet(cex,delimiter)
    if (relations.size < 1) {
      None
    } else {
      Some(relations)
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
    val collectionRepo = collectionRepoFromCex(cexString,delimiter,delimiter2)
    val imgExtensions = ImageExtensions(cexString,delimiter)
    val relationSet = relationsFromCex(cexString,delimiter)

    CiteLibrary(libMap("name"),Cite2Urn(libMap("urn")),libMap("license"), textRepo,collectionRepo,imgExtensions,relationSet)
  }

}
