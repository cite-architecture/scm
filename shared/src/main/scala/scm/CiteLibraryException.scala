package edu.holycross.shot




package scm {

  import scala.scalajs.js
  import js.annotation.JSExport

  /** Extension for exceptions specific to the `citeobj` package.
  */
  @JSExport  case class CiteLibraryException(message: String = "", cause: Option[Throwable] = None) extends Exception(message) {
    cause.foreach(initCause)
  }

}
