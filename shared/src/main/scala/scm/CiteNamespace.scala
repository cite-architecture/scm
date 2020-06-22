package edu.holycross.shot.scm


import scala.scalajs.js
import scala.scalajs.js.annotation._

import java.net.URI

/**  Pairing of abbreviation and namespace URI.
*
* @param abbreviation String used for CITE or CTS namespace.
* @param uri Unique URI for namespace.
*
*/
@JSExportAll  case class  CiteNamespace(abbreviation: String, uri: URI) {

}
