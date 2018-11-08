package edu.holycross.shot.scm
import org.scalatest.FlatSpec
import edu.holycross.shot.cite._
import java.net.URI
import scala.io._

class HmtLibrarySpec extends FlatSpec {

  "A CiteLibrary" should "support building a metadata set with no repositories" in   {
    val cex = Source.fromFile("jvm/src/test/resources/hmt-2018e.cex").getLines.toVector.mkString("\n")


    // This is not a unit test:  this is a stress test.
    // It doesn't belong in this unit testing section of the code tree.
    //val citeLib = CiteLibrary(cex,"#",",")

  }


}
