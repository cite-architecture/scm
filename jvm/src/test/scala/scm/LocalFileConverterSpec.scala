package edu.holycross.shot.scm
import org.scalatest.FlatSpec
import edu.holycross.shot.ohco2._

class LocalFileConverterSpec extends FlatSpec {

  "A LocalFileConverter" should "build a text repository from cataloged local XML files" in {
    val repo = LocalFileConverter.textRepoFromFiles("jvm/src/test/resources/xmlrepository/inventory.xml","jvm/src/test/resources/xmlrepository/citationconfig.xml", "jvm/src/test/resources/xmlrepository/texts")
    repo match {
      case tr : TextRepository => assert(true)
      case _ => fail ("Should have created a TextRepository")
    }
  }

  it should "build a text repository from cataloged local Markdown files" in {
    val repo = LocalFileConverter.textRepoFromFiles("jvm/src/test/resources/markdownrepository/inventory.xml","jvm/src/test/resources/markdownrepository/citationconfig.xml", "jvm/src/test/resources/markdownrepository/texts")
    repo match {
      case tr : TextRepository => assert(true)
      case _ => fail ("Should have created a TextRepository")
    }
  }


  it should "convert a text repository represented in local XML files to CEX" in {
    val cex =
    LocalFileConverter.textCexFromFiles("jvm/src/test/resources/xmlrepository/inventory.xml","jvm/src/test/resources/xmlrepository/citationconfig.xml", "jvm/src/test/resources/xmlrepository/texts")

    val lines = cex.split("\n")
    val seglabels = lines.filter(_.startsWith("#!cts"))
    assert(seglabels.toSet == Set("#!ctscatalog", "#!ctsdata"))
  }

  it should "build a text repository given a properties file for an XML repository" in {
    val propFile = "jvm/src/test/resources/xmlrepository.properties"
    val repo = LocalFileConverter.textRepoFromPropertiesFile(propFile)
    repo match {
      case tr : TextRepository => assert(true)
      case _ => fail ("Should have created a TextRepository")
    }
  }
  it should "convert a text repository of XML files to CEX given a properties file" in {
    val propFile = "jvm/src/test/resources/xmlrepository.properties"
    val cex = LocalFileConverter.textCexFromPropertiesFile(propFile)
    val lines = cex.split("\n")
    val seglabels = lines.filter(_.startsWith("#!cts"))
    assert(seglabels.toSet == Set("#!ctscatalog", "#!ctsdata"))
  }


}
