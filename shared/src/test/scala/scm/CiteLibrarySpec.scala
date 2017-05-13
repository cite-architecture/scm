package edu.holycross.shot.scm
import org.scalatest.FlatSpec

import edu.holycross.shot.cite._

class CiteLibrarySpec extends FlatSpec {

  "A CiteLibrary" should "support building a metadata set with no repositories" in {

    val cex = """#!citelibrary
name#demo
urn#urn:cite2:cex:democex.2017_1:test
license#public domain
"""
    val citeLib = CiteLibrary(cex,"#",",")
    assert(citeLib.name == "demo")
    //assert(citeLib.urn == Cite2Urn("urn:cite2:cex:democex.2017_1:test"))
    assert(citeLib.license == "public domain")
  }


  it should "support building a text repository from a CEX source string" in {

    val cex = """#!citelibrary
name#demo
urn#urn:cite2:cex:testcoll:hdt1node
license#public domain
#!ctscatalog
urn#citationScheme#groupName#workTitle#versionLabel#exemplarLabel#online
urn:cts:greekLit:tlg0016.tlg001.loebeng:#book/section#Herodotus#Histories#English. trans. Godley [1921]##true
#!ctsdata
urn:cts:greekLit:tlg0016.tlg001.loebeng:1.0#This is the Showing forth of the Inquiry of Herodotus of Halicarnassos, to the end that neither the deeds of men may be forgotten by lapse of time, nor the works great and marvellous, which have been produced some by Hellenes and some by Barbarians, may lose their renown; and especially that the causes may be remembered for which these waged war with one another.
"""


    val citeRepo = CiteLibrary(cex,"#",",")
    assert(citeRepo.hasTexts)
  }

  it should "handle missing metadata gracefully" in pending
  it should "ignore 'comment' lines in metadata" in pending
  // but how?

  it should "support building a CITE Collection repository from a CEX string" in {
    val cex = """#!citelibrary
name#demo
urn#urn:cite2:cex:testcoll:hdt1node
license#public domain

#!citecatalog
collection#urn:cite2:hmt:msA.v1:#Pages of the Venetus A manuscriptscript#urn:cite2:hmt:msA.v1.label:#urn:cite2:hmt:msA.v1.sequence:#CC-attribution-share-alike

property#urn:cite2:hmt:msA.v1.urn:#URN#Cite2Urn#
property#urn:cite2:hmt:msA.v1.label:#Label#String#
property#urn:cite2:hmt:msA.v1.siglum:#Manuscript siglum#String#
property#urn:cite2:hmt:msA.v1.sequence:#Page sequence#Number#
property#urn:cite2:hmt:msA.v1.rv:#Recto or Verso#String#recto,verso
property#urn:cite2:hmt:msA.v1.codex:#Codex URN#Cite2Urn#

#!citedata
siglum#sequence#urn#rv#label#codex
msA#1#urn:cite2:hmt:msA.v1:1r#recto#Marcianus Graecus Z. 454 (= 822) (Venetus A) folio 1r#urn:cite2:hmt:codex:msA
msA#2#urn:cite2:hmt:msA.v1:1v#verso#Marcianus Graecus Z. 454 (= 822) (Venetus A) folio 1v#urn:cite2:hmt:codex:msA
msA#3#urn:cite2:hmt:msA.v1:2r#recto#Marcianus Graecus Z. 454 (= 822) (Venetus A) folio 2r#urn:cite2:hmt:codex:msA
"""

    val citeRepo = CiteLibrary(cex,"#",",")
    val catalogedCollections = 1
    assert(citeRepo.collectionRepository.get.catalog.size == catalogedCollections)
    // 3 records, 6 properties
    assert(citeRepo.collectionRepository.get.data.size == 3*6)
  }



}
