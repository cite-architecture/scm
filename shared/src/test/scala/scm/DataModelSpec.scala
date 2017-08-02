package edu.holycross.shot.scm
import org.scalatest.FlatSpec

import edu.holycross.shot.cite._

import java.net.URI

class DataModelSpec extends FlatSpec {

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


}
