package edu.holycross.shot.scm
import org.scalatest.FlatSpec
import scala.io.Source
import edu.holycross.shot.cite._

import java.net.URI

class DataModelSpec extends FlatSpec {


  val testCex = "shared/src/test/resources/datamodels.cex"
  val bigCex:String = Source.fromFile(testCex).getLines.mkString("\n")
  val bigCiteLib = CiteLibrary(bigCex,"#",",")

  "The DataModel object" should "create a DataModel from a single line of CEX data" in {
    val cex = "urn:cite2:hmt:dse.2017a:#urn:cite2:dse:datamodel.v1:#DSE model#Diplomatic Scholarly Edition (DSE) model.  See documentation at <https://github.com/cite-architecture/dse>."
    val dm = DataModel(cex)

    dm match {
      case dataModel: DataModel => assert(true)
      case _ => fail("Should have created a DataModel")
    }
  }

  it should "create a Vector of DataModel objects from a single CEX datamodels block" in {
    val block = """#!datamodels
Collection#Model#Label#Description
urn:cite2:hmt:dse.2017a:#urn:cite2:dse:datamodel.v1:#DSE model#Diplomatic Scholarly Edition (DSE) model.  See documentation at <https://github.com/cite-architecture/dse>.
"""
    val models = DataModel.vectorFromCex(block)
    assert(models.size == 1)

    val model1 = models(0)

    val expectedCollection = Cite2Urn("urn:cite2:hmt:dse.2017a:")
    assert(model1.collection == expectedCollection)

    val expectedModel = Cite2Urn("urn:cite2:dse:datamodel.v1:")
    assert(model1.model == expectedModel)
  }

  it should "create a Vector of DataModel objects from a single CEX datamodels block with more than one entry" in {
    val block = """#!datamodels
Collection#Model#Label#Description
urn:cite2:hmt:binaryimg.v1:#urn:cite2:cite:datamodels.v1:binaryimg#Binary image model#implementation of binary image retrieval using IIPServ.  See documentation at <TBA>.
urn:cite2:hmt:vaimg.2017a:#urn:cite2:cite:datamodels.v1:imagemodel#Citable image model#CITE architecture model of citable images.  See documentation at <http://cite-architecture.github.io/imagemodel/>.
"""
    val models = DataModel.vectorFromCex(block)
    assert(models.size == 2)

    val model0 = models(0)
    val model1 = models(1)

    val expectedCollection0 = Cite2Urn("urn:cite2:hmt:binaryimg.v1:")
    assert(model0.collection == expectedCollection0)

    val expectedModel0 = Cite2Urn("urn:cite2:cite:datamodels.v1:binaryimg")
    assert(model0.model == expectedModel0)

    val expectedCollection1 = Cite2Urn("urn:cite2:hmt:vaimg.2017a:")
    assert(model1.collection == expectedCollection1)

    val expectedModel1 = Cite2Urn("urn:cite2:cite:datamodels.v1:imagemodel")
    assert(model1.model == expectedModel1)
  }

  // The below rely on the CEX file in /shared/src/test/resources

  it should "read a big CEX file with datamodels" in {
    assert( bigCiteLib.hasDataModels )
  }

  it should "report which models apply to a given collection" in {
    val hasTwo:Cite2Urn = Cite2Urn("urn:cite2:hmt:vaimg.2017a:")
    val hasOne:Cite2Urn = Cite2Urn("urn:cite2:hmt:e4img.2017a:")

    assert( bigCiteLib.modelsForCollection(hasTwo).size == 2)
    assert( bigCiteLib.modelsForCollection(hasOne).size == 1)
  }

  it should "report which collection apply to a given model" in {
    val hasTwo:Cite2Urn = Cite2Urn("urn:cite2:cite:datamodels.v1:imagemodel")
    val hasOne:Cite2Urn = Cite2Urn("urn:cite2:cite:datamodels.v1:binaryimg")
    assert( bigCiteLib.collectionsForModel(hasTwo).size == 2)
    assert( bigCiteLib.collectionsForModel(hasOne).size == 1)
  }


}
