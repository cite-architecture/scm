package edu.holycross.shot.scm
import org.scalatest.FlatSpec

class CiteLibrarySourceSpec extends FlatSpec {

  "A CiteLibrary" should "build a repository from a .cex file" in {

    val fName = "jvm/src/test/resources/cex/millextract.cex"
    val citeRepo = CiteLibrarySource.fromFile(fName,"#")
    citeRepo match {
      case cr: CiteLibrary => assert(true)
      case _ => fail("Should have created a CiteLibrary")
    }
  }

  it should "build a repository from a .cex file delimited with tabs" in {

    val fName = "jvm/src/test/resources/cex/millextract-tabs.cex"
    val citeRepo = CiteLibrarySource.fromFile(fName,"\t")
    citeRepo match {
      case cr: CiteLibrary => assert(true)
      case _ => fail("Should have created a CiteLibrary")
    }
  }




}
