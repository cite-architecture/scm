package edu.holycross.shot.scm
import org.scalatest.FlatSpec
import edu.holycross.shot.cite._
import edu.holycross.shot.ohco2._
import edu.holycross.shot.citeobj._
import java.net.URI

class CiteLibraryJVM_CEXSpec extends FlatSpec {

  "A CiteLibrary" should "serialize to CEX" in   {

    val inputCex = """#!citelibrary
name#demo
urn#urn:cite2:cex:democex.2017_1:test
license#public domain
"""
    val citeLib = CiteLibrary(inputCex)
    assert(citeLib.name == "demo")
    assert(citeLib.urn == Cite2Urn("urn:cite2:cex:democex.2017_1:test"))
    assert(citeLib.license == "public domain")
    val outputCex: String = citeLib.cex("#") 

    val testLib = CiteLibrary(outputCex)
    assert(testLib.name == "demo")
    assert(testLib.urn == Cite2Urn("urn:cite2:cex:democex.2017_1:test"))
    assert(testLib.license == "public domain")

  }

  it should "serialize a library with namespaces to CEX" in {
    val bigCex = "jvm/src/test/resources/CEX_Tutorial/00_Text.cex"
    val citeLib = CiteLibrarySource.fromFile(bigCex,"#")
    val testCex: String = citeLib.cex("#")
    val newLib = CiteLibrary(testCex)
    lazy val nss = newLib.namespaces
    assert( nss.size == 2)
  }

  it should "serialize a library with a TextRepository to CEX" in {
    val bigCex = "jvm/src/test/resources/CEX_Tutorial/00_Text.cex"
    val citeLib = CiteLibrarySource.fromFile(bigCex,"#")
    val testCex: String = citeLib.cex("#")
    println("--------")
    println(testCex)
    println("--------")
    val newLib = CiteLibrary(testCex)
    lazy val tr: Option[TextRepository] = newLib.textRepository
    assert(tr.get.catalog.texts.size == 1)
  }

  it should "serialize a library with a TextRepository containing more than one text to CEX" in {
    val bigCex = "jvm/src/test/resources/CEX_Tutorial/0_Text_and_Translation.cex"
    val citeLib = CiteLibrarySource.fromFile(bigCex,"#")
    val testCex: String = citeLib.cex("#")
    //println("--------")
    //println(testCex)
    //println("--------")
    val newLib = CiteLibrary(testCex)
    lazy val tr: Option[TextRepository] = newLib.textRepository
    assert(tr.get.catalog.texts.size == 2)
  }

 it should "serialize a library with a TextRepository containing versions and exemplars to CEX" in {
    val bigCex = "jvm/src/test/resources/CEX_Tutorial/1_Citable_Tokens.cex"
    val citeLib = CiteLibrarySource.fromFile(bigCex,"#")
    val testCex: String = citeLib.cex("#")
    //println("--------")
    //println(testCex)
    //println("--------")
    val newLib = CiteLibrary(testCex)
    lazy val tr: Option[TextRepository] = newLib.textRepository
    assert(tr.get.catalog.texts.size == 4)
  }

 it should "serialize a library with a CollectionRepository to CEX" in {
    val bigCex = "jvm/src/test/resources/CEX_Tutorial/4_Image_Collection.cex"
    val citeLib = CiteLibrarySource.fromFile(bigCex)
    val testCex: String = citeLib.cex()
    /*
    println("--------")
    println(testCex)
    println("--------")
    */
    val newLib = CiteLibrary(testCex)
    lazy val cr: Option[CiteCollectionRepository] = newLib.collectionRepository
    assert(cr.get.collections.size == 2)
  }

  it should "serialize a library with a CiteRelationSet" in {
    val bigCex = "jvm/src/test/resources/CEX_Tutorial/7_DataModel_Commentary.cex"
    val citeLib = CiteLibrarySource.fromFile(bigCex)
    val testCex: String = citeLib.cex()
    /*
    println("--------")
    println(testCex)
    println("--------")
    */
    val newLib = CiteLibrary(testCex)
    val newRS = newLib.relationSet.get
    assert( newRS.size == 8)
  }

  it should "serialize a library with a CiteRelationSet, sorted by verb" in {
    val bigCex = "jvm/src/test/resources/CEX_Tutorial/9_Extended_String_Property_Types1.cex"
    val citeLib = CiteLibrarySource.fromFile(bigCex)
    val testCex: String = citeLib.cex()
    /*
    println("--------")
    println(testCex)
    println("--------")
    */
    val newLib = CiteLibrary(testCex)
    val newRS = newLib.relationSet.get
    val verbs: Vector[Cite2Urn] = {
        newRS.relations.toVector.map(_.relation).distinct
    }
    assert(verbs.size == 3)
  }

  it should "serialize a library with DataModels" in {
    val bigCex = "jvm/src/test/resources/CEX_Tutorial/9_Extended_String_Property_Types1.cex"
    val citeLib = CiteLibrarySource.fromFile(bigCex)
    val testCex: String = citeLib.cex()
    /*
    println("--------")
    println(testCex)
    println("--------")
    */
    val newLib = CiteLibrary(testCex)
    val newDM = newLib.dataModels.get
    assert(newDM.size == 4)
  }

  it should "round-trip a large, complex digital library" in {
    val bigCex = "jvm/src/test/resources/CEX_Tutorial/10_Extended_String_Property_Types2.cex"
    val citeLib = CiteLibrarySource.fromFile(bigCex)
    val testCex1: String = citeLib.cex()
    /*
    println("--------")
    println(testCex)
    println("--------")
    */
    val newLib = CiteLibrary(testCex1)
    val testCex2: String = newLib.cex()
    assert( testCex1 == testCex2 )
  }




}
