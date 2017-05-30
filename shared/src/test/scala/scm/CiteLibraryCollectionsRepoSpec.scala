package edu.holycross.shot.scm
import org.scalatest.FlatSpec

import edu.holycross.shot.cite._
import edu.holycross.shot.cex._
import edu.holycross.shot.citeobj._

class CiteLibraryCollectionsRepoSpec extends FlatSpec {

  "The CiteLibrary object" should "return None if no collection repo is configured" in {

    val noData = """
#!citelibrary
license#public domain
name#Demo library
urn#urn:cite2:cex:democex.2017a:test
"""
    val lib = CiteLibrary(noData, "#",",")
    assert(lib.collectionRepository == None)
  }




  it should "allow a catalog even if there is no content in collections data" in {
    val noData = """#!citelibrary
license#public domain
name#Demo library
urn#urn:cite2:cex:democex.2017a:test
#!citecatalog
# Text-bearing surfaces:
collection#urn:cite2:hmt:msA.v1:#Pages of the Venetus A manuscriptscript#urn:cite2:hmt:msA.v1.label:#urn:cite2:hmt:msA.v1.sequence:#CC-attribution-share-alike
property#urn:cite2:hmt:msA.v1.urn:#URN#Cite2Urn#
property#urn:cite2:hmt:msA.v1.label:#Label#String#
property#urn:cite2:hmt:msA.v1.siglum:#Manuscript siglum#String#
property#urn:cite2:hmt:msA.v1.sequence:#Page sequence#Number#
property#urn:cite2:hmt:msA.v1.rv:#Recto or Verso#String#recto,verso
property#urn:cite2:hmt:msA.v1.codex:#Codex URN#Cite2Urn#
# Documentary images:
collection#urn:cite2:hmt:vaimg.2017a:#Images of the Venetus A manuscriptscript#urn:cite2:hmt:vaimg.2017a.caption:##CC-attribution-share-alike
property#urn:cite2:hmt:vaimg.2017a.urn:#URN#Cite2Urn#
property#urn:cite2:hmt:vaimg.2017a.caption:#Caption#String#
property#urn:cite2:hmt:vaimg.2017a.rights:#Rights#String#
"""
    val lib = CiteLibrary(noData, "#",",")
    lib.collectionRepository match {
      case collOpt: Some[CiteCollectionRepository] => {
        val collRepo = collOpt.get
        assert ( collRepo.catalog.size == 2)
        assert(collRepo.data.size == 0)
      }
      case _ => fail("Did not instantiate CiteCollectionRepository")
    }



  }


  it should "throw an exception if citedata blocks are included without catalog" in {
    val noCatalog = """#!citelibrary
license#public domain
name#Demo library
urn#urn:cite2:cex:democex.2017a:test
#!citedata
siglum#sequence#urn#rv#label#codex
msA#1#urn:cite2:hmt:msA.v1:12r#recto#Marcianus Graecus Z. 454 (= 822) (Venetus A) folio 12 recto#urn:cite2:hmt:codex:msA
#!citedata
urn#caption#rights
urn:cite2:hmt:vaimg.2017a:VA012RN_0013.2017#Natural light photograph of Venetus A: Marcianus Graecus Z. 454 (= 822), folio 12, recto#This image was derived from an original ©2007, Biblioteca Nazionale Marciana, Venezie, Italia. The derivative image is ©2010, Center for Hellenic Studies. Original and derivative are licensed under the Creative Commons Attribution-Noncommercial-Share Alike 3.0 License. The CHS/Marciana Imaging Project was directed by David Jacobs of the British Library.
"""
    try {
      val lib = CiteLibrary(noCatalog, "#",",")
      fail("Should not have made CiteLibrary")
    } catch {
      case iae: IllegalArgumentException => assert(iae.getMessage() ==  "requirement failed: CITE Collection data must be documented in a citectalog block")
      case t: Throwable => fail("Should have thrown an IllegalArgument Exception: " + t)
    }

  }


  // THESE TWO REQUIREMENTS SHOULD BE ENFORCED IN CiteCollectionRepository.  TEST WHEN UPGRADED LIB version
  // DOES THIS PROPERLy.
  it should "throw an exception if collections data and catalog conflict" in pending
  /*{
    val conflicted = """
#!citelibrary
license#public domain
name#Demo library
urn#urn:cite2:cex:democex.2017a:test

#!citecatalog
collection#urn:cite2:hmt:vaimg.2017a:#Images of the Venetus A manuscriptscript#urn:cite2:hmt:vaimg.2017a.caption:##CC-attribution-share-alike
property#urn:cite2:hmt:vaimg.2017a.urn:#URN#Cite2Urn#
property#urn:cite2:hmt:vaimg.2017a.caption:#Caption#String#
property#urn:cite2:hmt:vaimg.2017a.rights:#Rights#String#

#!citedata
urn#caption#rights
urn:cite2:hmt:vaimg.2017a:VA012RN_0013.WRONGVERSION#Natural light photograph of Venetus A: Marcianus Graecus Z. 454 (= 822), folio 12, recto#This image was derived from an original ©2007, Biblioteca Nazionale Marciana, Venezie, Italia. The derivative image is ©2010, Center for Hellenic Studies. Original and derivative are licensed under the Creative Commons Attribution-Noncommercial-Share Alike 3.0 License. The CHS/Marciana Imaging Project was directed by David Jacobs of the British Library.
"""
    try {
      val lib = CiteLibrary(conflicted, "#",",")
      fail("Should not have made CiteLibrary")
    } catch {
      case iae: IllegalArgumentException => assert(iae.getMessage() ==  "requirement failed: ")
      case t: Throwable => fail("Should have thrown an IllegalArgument Exception: " + t)
    }
  }

  it should "return some collection if catalog and data are consistent" in {

    val legit = """#!citelibrary
license#public domain
name#Demo library
urn#urn:cite2:cex:democex.2017a:test
#!citecatalog
collection#urn:cite2:hmt:vaimg.2017a:#Images of the Venetus A manuscriptscript#urn:cite2:hmt:vaimg.2017a.caption:##CC-attribution-share-alike
property#urn:cite2:hmt:vaimg.2017a.urn:#URN#Cite2Urn#
property#urn:cite2:hmt:vaimg.2017a.caption:#Caption#String#
property#urn:cite2:hmt:vaimg.2017a.rights:#Rights#String#
#!citedata
urn#caption#rights
urn:cite2:hmt:vaimg.2017a:VA012RN_0013.2017a#Natural light photograph of Venetus A: Marcianus Graecus Z. 454 (= 822), folio 12, recto#This image was derived from an original ©2007, Biblioteca Nazionale Marciana, Venezie, Italia. The derivative image is ©2010, Center for Hellenic Studies. Original and derivative are licensed under the Creative Commons Attribution-Noncommercial-Share Alike 3.0 License. The CHS/Marciana Imaging Project was directed by David Jacobs of the British Library.
"""

      val lib = CiteLibrary(legit, "#",",")
      lib.collectionRepository match {
        case crOpt: Some[CiteCollectionRepository] => assert(true)
        case _ => fail("Should have found CiteCollectionRepository")
      }
    }
*/

}
