package edu.holycross.shot.scm
import edu.holycross.shot.cite._
import org.scalatest.FlatSpec

class CiteLibrarySourceSpec extends FlatSpec {


  val dmCex = "jvm/src/test/resources/datamodels.cex"


  "A CiteLibrary" should "build a repository from a .cex file" in {
    val fName = "jvm/src/test/resources/cex/quransample-pounds.cex"
    val citeRepo = CiteLibrarySource.fromFile(fName,"#")
    citeRepo match {
      case cr: CiteLibrary => assert(true)
      case _ => fail("Should have created a CiteLibrary")
    }
  }

  it should "build a repository from a .cex file delimited with tabs" in {
    val fName = "jvm/src/test/resources/cex/quransample-tabs.cex"
    val citeRepo = CiteLibrarySource.fromFile(fName,"\t")
    citeRepo match {
      case cr: CiteLibrary => assert(true)
      case _ => fail("Should have created a CiteLibrary")
    }
  }

  it should "correctly create an option of data models" in {
    val citeRepo = CiteLibrarySource.fromFile(dmCex,"#")
    assert(citeRepo.hasDataModels)
    val dms = citeRepo.dataModels.get
    val expectedModels = 3
    assert(dms.size == 3)
  }


  it should "find models applying to a given collection" in {
    val citeRepo = CiteLibrarySource.fromFile(dmCex,"#")

    val vaimg = Cite2Urn("urn:cite2:hmt:vaimg.2017a:")
    val vaimgModels = citeRepo.modelsForCollection(vaimg)
    val expectedVaimg = 2
    assert(vaimgModels.size == expectedVaimg)


    val e4img = Cite2Urn("urn:cite2:hmt:e4img.2017a:")
    val e4imgModels = citeRepo.modelsForCollection(e4img)
    val expectedE4img = 1
    assert(e4imgModels.size == expectedE4img)

  }



}
