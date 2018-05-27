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
@JSExportAll  case class  DSEModel (
  label: String,
  passagePropertyUrn: Cite2Urn,
  imagePropertyUrn: Cite2Urn,
  surfacePropertyUrn: Cite2Urn
 )


/*
 object DSEModel {

   /** Create a DSE Model from properties of a Cite Collection
   *
   * @param label The Label from the implementing Collection
   * @param passagePropertyUrn The URN to the property identifying passages of text in the Cite Collection
   * @param imagePropertyUrn The URN to the property identifying documentary images in the Cite Collection
   * @param surfacePropertyUrn The URN to the property identifying text bearing surfaces in the Cite Collection
   */
   def apply(label:String, passagePropertyUrn: Cite2Urn, imagePropertyUrn: Cite2Urn, surfacePropertyUrn: Cite2Urn): DataModel = {
     DSEModel(label, passagePropertyUrn, imagePropertyUrn, surfacePropertyUrn)
   }

 }
   */
