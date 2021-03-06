package edu.holycross.shot.scm
import org.scalatest.FlatSpec

import edu.holycross.shot.cite._
import edu.holycross.shot.cex._

class CiteLibraryObjectSpec extends FlatSpec {

  "The CiteLibrary object" should "require a citelibrary block in CEX source" in {
    val noLibrary = "#!ctsdata\nurn:cts:greekLit:tlg0012.tlg001.eng1:1.1#Sing, goddess, the wrath of Achilles, son of Peleus"
    try {
      val lib = CiteLibrary(noLibrary)
      fail ("Should not have created library")
    } catch {
      case cle: CiteLibraryException => assert(cle.message == "CEX source must include `citelibrary` block")

      case t: Throwable => fail("Should have thrown CiteLibraryException, but threw " + t)
    }
  }

  it should "require a name string in the citelibrary block" in {
    val noName = """
#!citelibrary
urn#urn:cite2:cex:democex.2017_1:test
license#public domain
"""
    try {
      val lib = CiteLibrary(noName)
      fail ("Should not have created library")
    } catch {
      case iae : IllegalArgumentException => assert(iae.getMessage() == "requirement failed: CEX `citelibrary` block must include value for library name")
      case t: Throwable => fail("Should have thrown an IllegalArgumentException, but threw " + t)
    }
  }

  it should "require a license string in the citelibrary block" in {
    val noName = """
#!citelibrary
urn#urn:cite2:cex:democex.2017_1:test
name#Demo library
"""
    try {
      val lib = CiteLibrary(noName)
      fail ("Should not have created library")
    } catch {
      case iae : IllegalArgumentException => assert(iae.getMessage() == "requirement failed: CEX `citelibrary` block must include a licensing statement")
      case t: Throwable => fail("Should have thrown an IllegalArgumentException, but threw " + t)
    }
  }

  it should "require a URN value the citelibrary block" in {
    val noName = """
#!citelibrary
license#public domain
name#Demo library
"""
    try {
      val lib = CiteLibrary(noName)
      fail ("Should not have created library")
    } catch {
      case iae : IllegalArgumentException => assert(iae.getMessage() == "requirement failed: CEX `citelibrary` block must include a URN")
      case t: Throwable => fail("Should have thrown an IllegalArgumentException, but threw " + t)
    }
  }


  it should "require a valid URN value" in {
    val badUrn = """
#!citelibrary
license#public domain
name#Demo library
urn#not a urn
"""
    try {
      val lib = CiteLibrary(badUrn)
      fail ("Should not have created library")
    } catch {
      case iae : IllegalArgumentException => assert(iae.getMessage() == "requirement failed: wrong number of components in  'not a urn' (1)")
      case t: Throwable => fail("Should have thrown an IllegalArgumentException, but threw " + t)
    }
  }


  it should "require a versioned URN value" in {
    val noVersion = """
#!citelibrary
license#public domain
name#Demo library
urn#urn:cite2:cex:democex:test
"""
    try {
      val lib = CiteLibrary(noVersion)
      fail ("Should not have created library")
    } catch {
      case cle : CiteLibraryException => assert(cle.message == "URN must include version identifier")
      case t: Throwable => fail("Should have thrown CiteLibraryException, but threw " + t)
    }
  }


  it should "create a vector of CiteNamespace objects from CEX source" in {
    val cex = """#!citelibrary
name#Iliadic Metrical Summaries
urn#urn:cite2:hmt:cex.2017_1:metsumm
license#Creative Commons Attribution, Non-Commercial 4.0 License <https://creativecommons.org/licenses/by-nc/4.0/>.
// Optional namespace definitions
namespace#hmt#http://www.homermultitext.org/citens/hmt
namespace#greekLit#http://chs.harvard.edu/ctsns/greekLit
"""
    val nsVect = CiteLibrary.namespacesFromCex(CexParser(cex),"#")
    assert(nsVect.size == 2)
  }

  it should "create an empty vector if no namespaces are defined" in {
    val cex = """#!citelibrary
name#Iliadic Metrical Summaries
urn#urn:cite2:hmt:cex.2017_1:metsumm
license#Creative Commons Attribution, Non-Commercial 4.0 License <https://creativecommons.org/licenses/by-nc/4.0/>.
"""
    val nsVect = CiteLibrary.namespacesFromCex(CexParser(cex),"#")
    assert(nsVect.isEmpty)
  }


  it should "create a map of configuration keys to values" in {
    val cex = """#!citelibrary
name#Iliadic Metrical Summaries
urn#urn:cite2:hmt:cex.2017_1:metsumm
license#Creative Commons Attribution, Non-Commercial 4.0 License <https://creativecommons.org/licenses/by-nc/4.0/>.
"""
   val conf = CiteLibrary.libConfigMapFromCex(CexParser(cex),"#")
   assert (conf.keySet == Set("urn", "name", "license"))
  }

  it should "return None for TextRepository if none is configured" in {
    val cex = """#!citelibrary
name#Iliadic Metrical Summaries
urn#urn:cite2:hmt:cex.2017_1:metsumm
license#Creative Commons Attribution, Non-Commercial 4.0 License <https://creativecommons.org/licenses/by-nc/4.0/>.
"""
  val repo = CiteLibrary.textRepoFromCex(CexParser(cex),"#")
  repo match {
    case None => assert(true)
    case _ => fail("Should have returned None")
  }
  }


  it should  "return None for CollectionRepository if none is configured" in {
    val cex = """#!citelibrary
name#Iliadic Metrical Summaries
urn#urn:cite2:hmt:cex.2017_1:metsumm
license#Creative Commons Attribution, Non-Commercial 4.0 License <https://creativecommons.org/licenses/by-nc/4.0/>.
"""
  val repo = CiteLibrary.collectionRepoFromCex(cex)
  repo match {
    case None => assert(true)
    case _ => fail("Should have returned None")
  }
  }

  it should "accept a conifgured library with no data contents" in {
    val noData = """
#!citelibrary
license#public domain
name#Demo library
urn#urn:cite2:cex:democex.2017a:test
"""

    val lib = CiteLibrary(noData)
    lib match {
      case citeLib : CiteLibrary => {
        assert(citeLib.textRepository == None)
        assert(citeLib.collectionRepository == None)
        assert(citeLib.relationSet == None)

      }
      case _ => fail("Should have created a CiteLibrary")
    }
  }


    it should "assume default values for delimiters" in {
    val minimum = """
#!citelibrary
license#public domain
name#Demo library
urn#urn:cite2:cex:democex.2017a:test
"""
      val lib = CiteLibrary(minimum)
      assert(lib.license ==  "public domain")
    }
}
