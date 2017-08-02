package edu.holycross.shot.scm

import edu.holycross.shot.cite._
import edu.holycross.shot.cex._


import scala.scalajs.js
import scala.scalajs.js.annotation._



/** Association of a CITE Collection with a citable
* data model.
*
* @param collection A CITE Collection following a specified data model.
* @param model Identifier for the data model.
* @param label Human-readable label.
* @param description Fuller description of the model, potentially with
* pointers to external documentation.
*/
@JSExportAll  case class  DataModel (
  collection: Cite2Urn,
  model: Cite2Urn,
  label: String,
  description: String
 )


 object DataModel {

   /** Create a data model from a CEX-formatted string.
   *
   * @param row A single entry from a CEX `datamodels` blocks.
   * @param delimiter Column delimiter.
   */
   def apply(row: String, delimiter: String = "#"): DataModel = {
     val columns = row.split(delimiter)
     DataModel(Cite2Urn(columns(0)), Cite2Urn(columns(1)), columns(2), columns(3))
   }

   /** Create a Vector of DataModels from a CEX source.
   *
   *  @param cexSrc CEX source including a `datamodels`block.
   */
   def vectorFromCex(cexSrc: String): Vector[DataModel] = {
     val cex = CexParser(cexSrc)
     val dmSource = cex.blockVector("datamodels")
     dmSource.flatMap( s => {
       val rows = s.split("\n").drop(1)
       rows.map(DataModel(_))
     } )

   }
 }
