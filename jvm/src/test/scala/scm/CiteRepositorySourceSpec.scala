package edu.holycross.shot.scm
import org.scalatest.FlatSpec

class CiteRepositorySourceSpec extends FlatSpec {

  "A CiteRepository" should "build a repository from a .cex file" in {

    val fName = "jvm/src/test/resources/millextract.cex"
    val citeRepo = CiteRepositorySource.fromFile(fName,"#")
    citeRepo match {
      case cr: CiteRepository => assert(true)
      case _ => fail("Should have created a CiteRepository")
    }
  }

  it should "build a repository from a .cex file delimited with tabs" in {

    val fName = "jvm/src/test/resources/millextract-tabs.cex"
    val citeRepo = CiteRepositorySource.fromFile(fName,"\t")
    citeRepo match {
      case cr: CiteRepository => assert(true)
      case _ => fail("Should have created a CiteRepository")
    }
  }




}
