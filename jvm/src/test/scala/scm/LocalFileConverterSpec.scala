package edu.holycross.shot.scm
import org.scalatest.FlatSpec
import edu.holycross.shot.ohco2._

class LocalFileConverterSpec extends FlatSpec {

  "A LocalFileConverter" should "build a text repository from XML files cataloging local files" in {
    val repo = LocalFileConverter.textRepoFromFiles("jvm/src/test/resources/textrepository/inventory.xml","jvm/src/test/resources/textrepository/citationconfig.xml", "jvm/src/test/resources/textrepository/texts")
    repo match {
      case tr : TextRepository => assert(true)
      case _ => fail ("Should have created a TextRepository")
    }
  }

  it should "convert a text repository represented in local files to CEX" in {
    val cex =
    LocalFileConverter.textCexFromFiles("jvm/src/test/resources/textrepository/inventory.xml","jvm/src/test/resources/textrepository/citationconfig.xml", "jvm/src/test/resources/textrepository/texts")

    val lines = cex.split("\n")
    val seglabels = lines.filter(_.startsWith("#!cts"))
    assert(seglabels.toSet == Set("#!ctscatalog", "#!ctsdata"))
  }

  it should "build a text repository given a properties file" in {
    val propFile = "jvm/src/test/resources/textrepoprops.txt"
    val repo = LocalFileConverter.textRepoFromPropertiesFile(propFile)
    repo match {
      case tr : TextRepository => assert(true)
      case _ => fail ("Should have created a TextRepository")
    }
  }
  it should "convert a text repository to CEX given a properties file" in {
    val propFile = "jvm/src/test/resources/textrepoprops.txt"
    val cex = LocalFileConverter.textCexFromPropertiesFile(propFile)
    val lines = cex.split("\n")
    val seglabels = lines.filter(_.startsWith("#!cts"))
    assert(seglabels.toSet == Set("#!ctscatalog", "#!ctsdata"))
  }


}
