package edu.holycross.shot.scm

import edu.holycross.shot.cite._
import edu.holycross.shot.ohco2._
import edu.holycross.shot.citeobj._
import edu.holycross.shot.cex._
import edu.holycross.shot.citerelation._

import java.net.URI


import wvlet.log._
import wvlet.log.LogFormatter.SourceCodeLogFormatter


import scala.scalajs.js
import scala.scalajs.js.annotation._



/** A library of citable scholarly resources.
*
* @param name Name of the citable library.
* @param urn URN identifying the library.
* @param license Licensing and rights information.
* @param namespaces Possibly empty vector of [[CiteNamespace]]s.
* @param textRepository Optional, cataloged corpus of citable texts.
* @param collectionRepository Optional, cataloged set of CITE Collections. (Not used in current version.)
* @param relationSet Optional set of triple statements.
*/
@JSExportAll  case class  CiteLibrary(
  name: String,
  urn: Cite2Urn,
  license: String,
  namespaces: Vector[CiteNamespace],
  textRepository: Option[TextRepository] = None,
  collectionRepository: Option[CiteCollectionRepository] = None,
  relationSet: Option[CiteRelationSet] = None,
  dataModels: Option[Vector[DataModel]] = None
 )  extends LogSupport  {

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




  /** True if DataModels is instantiated.
  */
  def hasDataModels: Boolean = {
    dataModels match {
      case None => {
        false
      }
      case t:Some[Vector[DataModel]] if t.size > 0 => {
        true
      }
      case _ => {
        false
      }
    }
  }

  /** Returns true if a given model applies to a given URN
  * @param modelUrn
  * @param objectUrn
  */
  def modelApplies(modelUrn:Cite2Urn, objectUrn:Cite2Urn):Boolean = {
    if (this.hasDataModels){
      val c4m:Vector[Cite2Urn] = modelsForCollection(objectUrn)
      val doesItApply:Boolean = {
        if (c4m.size < 1) {
          false
        } else {
          if ( c4m.contains(modelUrn)) { true } else { false }
        }
      }
      doesItApply
    } else {
      false
    }
  }


  /** Returns a vector of datamodels that apply to a given collection URN
  * @param collUrn
  */
  def modelsForCollection(collUrn:Cite2Urn):Vector[Cite2Urn] = {
      val colls:Vector[Cite2Urn] = {
        if (this.hasDataModels){
          this.dataModels.get.filter( _.collection ~~ collUrn).map(m => m.model)
        } else {
          Vector()
        }
      }
      colls
  }

  /** Returns a [possibly empty] vector of collectionmodels that apply to a given data model
  * @param modelUrn
  */
  def collectionsForModel(modelUrn:Cite2Urn):Vector[Cite2Urn] = {
      val datamodels:Vector[Cite2Urn] = {
        if (this.hasDataModels) {
          this.dataModels.get.filter( _.model ~~ modelUrn).map(m => m.collection)
        } else {
          Vector()
        }
      }
      datamodels
  }


  /** True if CiteRelationSet is instantiated.
  */
  def hasIndexes : Boolean = {
    relationSet match {
      case None => false
      case t: Some[CiteRelationSet] => true
    }
  }

  /** Serialize a CiteLibrary to CEX
  *
  * @param delimiter  Column-delimiter used in CEX source.
  */
  def cex(delimiter: String = "#"): String = {

    // Header block
    val headerBlock: String = {
      Vector(
        "\n#!cexversion\n3.0.1\n\n#!citelibrary",
        s"name${delimiter}${this.name}",
        s"urn${delimiter}${this.urn}",
        s"license${delimiter}${this.license}",
      ).mkString("\n")
    }

    // Namespaces
    val namespaceBlock: String = {
      val commentLine = "// CITE namespace definitions"
      val nsVec: Vector[String] = this.namespaces.map( ns => {
        s"namespace${delimiter}${ns.abbreviation}${delimiter}${ns.uri}"
      })
      if (nsVec.size > 0) {
        (Vector(commentLine) ++ nsVec).mkString("\n") 
      } else {
        ""
      }
    }

    // TextRepository (catalog and data grouped by text)
    val textBlock: String = {

        this.textRepository match {
          case None => ""
          case Some(tr) => {
            val textBlocks: Vector[String] = tr.catalog.texts.map(tc => {
              val catBlock: String = s"""#!ctscatalog\nurn#citationScheme#groupName#workTitle#versionLabel#exemplarLabel#online#lang\n${tc.cex("#")}"""
              val corp: String = {
                (tr.corpus ~= tc.urn).cex("#")
              }
              val corpBlock: String = s"#!ctsdata\n${corp}"
              catBlock + "\n\n" + corpBlock
            })
            textBlocks.mkString("\n\n")
          }
        }
    }

    // CollectionRepository (by collection)
    val collectionBlock: String = {
      this.collectionRepository match {
        case None => ""
        case Some(cr) => {
          cr.cex()
        }
      }
    }

    // RelationSets (by verb)
    val relationsBlock: String = {
      this.relationSet match {
        case None => ""
        case Some(rs) => {
          val relationVec: Vector[CiteTriple] = rs.relations.toVector
          val byVerb: Vector[Vector[CiteTriple]] = {
            relationVec.groupBy(_.relation).toVector.map(_._2)
          }
          val cexChunks: Vector[String] = byVerb.map( vv => {
            val sortVec: Vector[CiteTriple] = vv.sortBy(_.urn1.toString)
            val commentLine: String = {
              s"// relations with: ${sortVec.head.relation}\n#!relations"
            }
            val otherLines: Vector[String] = sortVec.map(_.cex("#"))
            (commentLine +: otherLines).mkString("\n")
          })
          cexChunks.mkString("\n\n")
        }
      }
    }

    // DataModels (???)
    val dataModelBlock: String = {
      val headerLine = "#!datamodels\nCollection#Model#Label#Description"
      this.dataModels match {
        case None => ""
        case Some(dmv) => {
          val cexVec: Vector[String] = dmv.map(_.cex("#"))
          (headerLine +: cexVec).mkString("\n")
        }
      }
    }

    Vector(
      headerBlock,
      namespaceBlock,
      dataModelBlock,
      textBlock,
      collectionBlock,
      relationsBlock
    ).mkString("\n\n")

  }


}


/** Factory for creating [[CiteLibrary]] objects from
* a String in CEX format.
*/
object CiteLibrary extends LogSupport {



  /** Instantiate a [[CiteLibrary]] from its CEX serialization.
  *
  * @param cexString Data in CITE Exchange format.
  * @param delimiter String value delimiting columns of CEX data.
  * @param delimiter2 Secondary delimiter separating values within a  column of CEX data.
  */
  def cex(cexString: String, delimiter: String , delimiter2: String)  : CiteLibrary = {
    val cex = CexParser(cexString)
    val libMap = libConfigMapFromCex(cex, delimiter)
    val nsVector = namespacesFromCex(cex,delimiter)
    val dataModels = dataModelsFromCex(cexString,delimiter)

    info("Building text repo from cex ...")
    val textRepo = textRepoFromCex(cex, delimiter)

    info("Building collection repo from cex ...")
    val collectionRepo = collectionRepoFromCex(cexString,delimiter,delimiter2)

    info("Building relations from cex ...")
    val relationSet = relationsFromCex(cexString,delimiter)
    info("All library components built.")
    CiteLibrary(libMap("name"),Cite2Urn(libMap("urn")),libMap("license"),nsVector, textRepo,collectionRepo,relationSet,dataModels)
  }

  def apply(cexString: String) : CiteLibrary = {
    val delimiter: String = "#"
    val delimiter2: String = ","
    cex(cexString, delimiter, delimiter2)
  }

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

    // this fails.  It's really better handled in the cex lib's blockString fucntion though.
    // Fix there and update dep in scm.
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
    val collectionsBlocks =  cex.blockVector("citecollections")
    val propertiesBlocks = cex.blockVector("citeproperties")
    if ((collectionsBlocks.size > 0) && (propertiesBlocks.size > 0)) {
      val ccr = CiteCollectionRepository(cexString,delimiter,delimiter2)
      Some(ccr)
    } else {
      None
    }
  }

  /** Create optional CITE Collections repository from CEX source.
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


  /** Create option of vector of [[DataModel]]s from CEX source.
  *
  * @param cex Parsed CEX source.
  * @param delimiter  Column-delimiter used in CEX source.
  */
  def dataModelsFromCex(cexString: String, delimiter: String = "#"): Option[Vector[DataModel]] = {
    val dm:Vector[DataModel] = DataModel.vectorFromCex(cexString, delimiter)
    dm.size match {
      case 0 => None
      case _ => Some(dm)
    }
  }


  /** Create vector of [[CiteNamespace]]s from CEX source.
  *
  * @param cex Parsed CEX source.
  * @param delimiter  Column-delimiter used in CEX source.
  */
  def namespacesFromCex(cex: CexParser, delimiter: String = "#"): Vector[CiteNamespace] = {
    val libContent = cex.blockString("citelibrary").split("\n").toVector

    val tripleValues = libContent.map(_.split(delimiter)).filter(_.size == 3)
    val nsList = for (arr <- tripleValues) yield {
      CiteNamespace(arr(1), new URI(arr(2)))
    }
    nsList.toVector
  }





}
