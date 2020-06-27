package edu.holycross.shot.scm
import edu.holycross.shot.cite._
import org.scalatest.FlatSpec

class EmptyTextNodeSpec extends FlatSpec {


  val libCex = "jvm/src/test/resources/empty-text-nodes.cex"


  "A CiteLibrary" should "build a repository from a .cex file with empty text nodes" in {
    val citeRepo = CiteLibrarySource.fromFile(libCex,"#")
  }

}
