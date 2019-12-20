package edu.holycross.shot.scm
import edu.holycross.shot.cite._
import org.scalatest.FlatSpec

class CiteLibrarySourceSpec extends FlatSpec {


  val dataModelCex = "jvm/src/test/resources/datamodels.cex"


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
    val citeRepo = CiteLibrarySource.fromFile(dataModelCex,"#")
    assert(citeRepo.hasDataModels)
    val dms = citeRepo.dataModels.get
    val expectedModels = 3
    assert(dms.size == 3)
  }


  it should "find models applying to a given collection" in {
    val citeRepo = CiteLibrarySource.fromFile(dataModelCex,"#")

    val vaimg = Cite2Urn("urn:cite2:hmt:vaimg.2017a:")
    val vaimgModels = citeRepo.modelsForCollection(vaimg)
    val expectedVaimg = 2
    assert(vaimgModels.size == expectedVaimg)


    val e4img = Cite2Urn("urn:cite2:hmt:e4img.2017a:")
    val e4imgModels = citeRepo.modelsForCollection(e4img)
    val expectedE4img = 1
    assert(e4imgModels.size == expectedE4img)

  }
  it should "find collections i a given implementing a give model" in {
    val citeRepo = CiteLibrarySource.fromFile(dataModelCex,"#")

    val binaryImgModel = Cite2Urn("urn:cite2:cite:datamodels.v1:binaryimg")
    val expectedBinaryImgCollections = 1
    assert(citeRepo.collectionsForModel(binaryImgModel).size == expectedBinaryImgCollections)

    val citableImgModel = Cite2Urn("urn:cite2:cite:datamodels.v1:imagemodel")
    val expectedCitableImgCollections = 2
    assert(citeRepo.collectionsForModel(citableImgModel).size == expectedCitableImgCollections)
  }

  it should "determine whether a given model applies to a given object" in {
    val citeRepo = CiteLibrarySource.fromFile(dataModelCex,"#")
    val binaryImgModel = Cite2Urn("urn:cite2:cite:datamodels.v1:binaryimg")
    val sampleImage = Cite2Urn("urn:cite2:hmt:vaimg.2017a:123@0.1,0.2,0.3,0.4")
    assert (citeRepo.modelApplies(binaryImgModel,sampleImage))

   val samplePage = Cite2Urn("urn:cite2:hmt:msA.v1:1r")
   assert(citeRepo.modelApplies(binaryImgModel,samplePage) == false)
  }

  it should "build a library from a URL referring to CEX source" in {
    println("BUILD A LIBRARY FROM URL")
    val releaseUrl = "https://raw.githubusercontent.com/homermultitext/hmt-archive/master/releases-cex/hmt-2018e.cex"

    val lib = CiteLibrarySource.fromUrl(releaseUrl)
    println("BUILT IT")
  }

}
