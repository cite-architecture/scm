package edu.holycross.shot.scm


import scala.scalajs.js
import js.annotation.JSExport

import java.net.URI

/**  Pairing of abbreviation and namespace URI.
*
* @param abbreviation String used for CITE or CTS namespace.
* @param uri Unique URI for namespace.
*
*/
@JSExport  case class  CiteNamespace(abbreviation: String,uri: URI) {

}
