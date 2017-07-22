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




  it should "allow a catalog even if there is no content in collections data" in pending /*{
    val noData = """#!citelibrary
license#public domain
name#Demo library
urn#urn:cite2:cex:democex.2017a:test
#!citecollections
// Text-bearing surfaces:
urn:cite2:hmt:msA.v1:#Pages of the Venetus A manuscriptscript#urn:cite2:hmt:msA.v1.label:#urn:cite2:hmt:msA.v1.sequence:#CC-attribution-share-alike
// Documenary images:
urn:cite2:hmt:vaimg.2017a:#Images of the Venetus A

#!citeproperties
// pages
urn:cite2:hmt:msA.v1.urn:#URN#Cite2Urn#
urn:cite2:hmt:msA.v1.label:#Label#String#
urn:cite2:hmt:msA.v1.siglum:#Manuscript siglum#String#
urn:cite2:hmt:msA.v1.sequence:#Page sequence#Number#
urn:cite2:hmt:msA.v1.rv:#Recto or Verso#String#recto,verso
urn:cite2:hmt:msA.v1.codex:#Codex URN#Cite2Urn#
 manuscriptscript#urn:cite2:hmt:vaimg.2017a.caption:##CC-attribution-share-alike
// images
urn:cite2:hmt:vaimg.2017a.urn:#URN#Cite2Urn#
urn:cite2:hmt:vaimg.2017a.caption:#Caption#String#
urn:cite2:hmt:vaimg.2017a.rights:#Rights#String#
"""
    val lib = CiteLibrary(noData, "#",",")
    lib.collectionRepository match {
      case collOpt: Some[CiteCollectionRepository] => {
        val collRepo = collOpt.get
        assert ( collRepo.catalog.size == 2)
        assert(collRepo.data.size == 0)
      }
      case _ => fail("Did not instantiate CiteCollectionRepository: ${lib.colectionRepository}")
    }



  }

*/
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
      case iae: IllegalArgumentException => assert(iae.getMessage() ==  "requirement failed: CITE Collection data must be documented in a citecollections block")
      case t: Throwable => fail("Should have thrown an IllegalArgument Exception: " + t)
    }

  }

/*

    val orcacex = """
#!citelibrary
license#public domain
name#Demo library
urn#urn:cite2:cex:democex.2017a:test

#!citecollections

urn:cite2:hmt:clausereading.v1:#Clauses in text of the Iliad#urn:cite2:hmt:clausereading.v1.deformation:##CC-attribution-share-alike
#!citeproperties

urn:cite2:hmt:clausereading.v1.urn:#ORCA URN#Cite2Urn#
urn:cite2:hmt:clausereading.v1.passage:#Passage analyzed#CtsUrn#
urn:cite2:hmt:clausereading.v1.analysis:#Analysis#Cite2Urn#
urn:cite2:hmt:clausereading.v1.deformation:#Text deformation#String#

#!citedata


urn#passage#analysis#deformation
urn:cite2:hmt:clausereading.v1:clause1#urn:cts:greekLit:tlg0012.tlg001.fuPers:1.1-1.2@ν[2]#urn:cite2:hmt:iliadicClauses.v1:imperative#Μῆνιν ἄειδε θεὰ Πηληϊάδεω Ἀχιλῆος οὐλομένην
urn:cite2:hmt:clausereading.v1:clause2#urn:cts:greekLit:tlg0012.tlg001.fuPers:1.2@ἣ[1]-1.2@ε[2]#urn:cite2:hmt:iliadicClauses.v1:indicative#ἣ μυρί᾽ Ἀχαιοῖς ἄλγε᾽ ἔθηκε
urn:cite2:hmt:clausereading.v1:clause3#urn:cts:greekLit:tlg0012.tlg001.fuPers:1.3@π[1]-1.4@ν[1]#urn:cite2:hmt:iliadicClauses.v1:indicative#πολλὰς δ᾽ ἰφθίμους ψυχὰς Ἄϊδι προΐαψεν ἡρώων
urn:cite2:hmt:clausereading.v1:clause4#urn:cts:greekLit:tlg0012.tlg001.fuPers:1.4@α[1]-1.5@ι[1]#urn:cite2:hmt:iliadicClauses.v1:indicative#αὐτοὺς δὲ ἑλώρια τεῦχε κύνεσσιν οἰωνοῖσί τε πᾶσ
urn:cite2:hmt:clausereading.v1:clause5#urn:cts:greekLit:tlg0012.tlg001.fuPers:1.5@Δ[1]-1.5@ή[1]#urn:cite2:hmt:iliadicClauses.v1:indicative#Διὸς δ᾽ ἐτελείετο βουλή
urn:cite2:hmt:clausereading.v1:clause6#urn:cts:greekLit:tlg0012.tlg001.fuPers:1.6-1.7#urn:cite2:hmt:iliadicClauses.v1:subordinate#ἐξ οὗ δὴ τὰ πρῶτα διαστήτην ἐρίσαντε Ἀτρεΐδης τε ἄναξ ἀνδρῶν καὶ δῖος Ἀχιλλεύς.
urn:cite2:hmt:clausereading.v1:clause7#urn:cts:greekLit:tlg0012.tlg001.fuPers:1.8#urn:cite2:hmt:iliadicClauses.v1:interrogative#Τίς τάρ σφωε θεῶν ἔριδι ξυνέηκε μάχεσθαι;
urn:cite2:hmt:clausereading.v1:clause8#urn:cts:greekLit:tlg0012.tlg001.fuPers:1.9@Λ[1]-1.9@ς[3]#urn:cite2:hmt:iliadicClauses.v1:indicative#Λητοῦς καὶ Διὸς υἱός [ξυνέηκε]
urn:cite2:hmt:clausereading.v1:clause9#urn:cts:greekLit:tlg0012.tlg001.fuPers:1.9@ὃ[1]-1.10@ν[5]#urn:cite2:hmt:iliadicClauses.v1:indicative#ὃ γὰρ βασιλῆϊ χολωθεὶς νοῦσον ἀνὰ στρατὸν ὄρσε κακήν
urn:cite2:hmt:clausereading.v1:clause10#urn:cts:greekLit:tlg0012.tlg001.fuPers:1.10@ὀ[1]-1.10@ί[1]#urn:cite2:hmt:iliadicClauses.v1:indicative#ὀλέκοντο δὲ λαοί
urn:cite2:hmt:clausereading.v1:clause11#urn:cts:greekLit:tlg0012.tlg001.fuPers:2.1@οὐδέ[1]-2.1@ἐΐσης[1]#urn:cite2:hmt:iliadicClauses.v1:indicative#οὐδέ τι θυμὸς ἐδεύετο δαιτὸς ἐΐσης
"""
*/

    it should "match URNs in properties" in  pending /*{
      val orcaRepo = CiteCollectionRepository(orcacex,"#",",")
      val orcaObjects = orcaRepo.citableObjects
      val orca1 = orcaObjects(0)

      println("matches Iliad? " + orca1.urnMatch(iliad))
    }
    */
  it should "search a collection repository for matching URN values" in pending
  /*{
    val iliad = CtsUrn("urn:cts:greekLit:tlg0012.tlg001:")

    val lib = CiteLibrary(orcacex, "#",",")
    val colls = lib.collectionRepository.get
    val iliads = colls.urnMatch(iliad)
    val expectedIliads = 11
    assert(iliads.size == expectedIliads)


    val imperative = Cite2Urn("urn:cite2:hmt:iliadicClauses.v1:imperative")
    val imperatives = colls.urnMatch(imperative)
    val expectedImperatives = 1
    assert(imperatives.size == expectedImperatives)

  }

*/

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
*/
  it should "return some collection if catalog and data are consistent" in pending /* {

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
