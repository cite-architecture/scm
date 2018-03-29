package edu.holycross.shot.scm
import org.scalatest.FlatSpec
import edu.holycross.shot.cite._
import java.net.URI

class CiteLibrarySpec extends FlatSpec {

  "A CiteLibrary" should "support building a metadata set with no repositories" in   {

    val cex = """#!citelibrary
name#demo
urn#urn:cite2:cex:democex.2017_1:test
license#public domain
"""
    val citeLib = CiteLibrary(cex,"#",",")
    assert(citeLib.name == "demo")
    assert(citeLib.urn == Cite2Urn("urn:cite2:cex:democex.2017_1:test"))
    assert(citeLib.license == "public domain")
  }


  it should "support building a text repository from a CEX source string" in  {

    val cex = """#!citelibrary
name#demo
urn#urn:cite2:cex:testcoll.2017a:hdt1node
license#public domain
#!ctscatalog
urn#citationScheme#groupName#workTitle#versionLabel#exemplarLabel#online
urn:cts:greekLit:tlg0016.tlg001.loebeng:#book/section#Herodotus#Histories#English. trans. Godley [1921]##true#eng
#!ctsdata
urn:cts:greekLit:tlg0016.tlg001.loebeng:1.0#This is the Showing forth of the Inquiry of Herodotus of Halicarnassos, to the end that neither the deeds of men may be forgotten by lapse of time, nor the works great and marvellous, which have been produced some by Hellenes and some by Barbarians, may lose their renown; and especially that the causes may be remembered for which these waged war with one another.
"""


    val citeRepo = CiteLibrary(cex,"#",",")
    assert(citeRepo.hasTexts)
  }


  it should "support building a CITE Collection repository from a CEX string" in {
    val cex = """#!citelibrary
name#demo
urn#urn:cite2:cex:testcoll.2017a:hdt1node
license#public domain

#!citecollections
URN#Description#Labelling property#Ordering property#License
urn:cite2:hmt:msA.v1:#Pages of the Venetus A manuscriptscript#urn:cite2:hmt:msA.v1.label:#urn:cite2:hmt:msA.v1.sequence:#CC-attribution-share-alike

#!citeproperties
Property#Label#Type#Authority list
urn:cite2:hmt:msA.v1.urn:#URN#Cite2Urn#
urn:cite2:hmt:msA.v1.label:#Label#String#
urn:cite2:hmt:msA.v1.siglum:#Manuscript siglum#String#
urn:cite2:hmt:msA.v1.sequence:#Page sequence#Number#
urn:cite2:hmt:msA.v1.rv:#Recto or Verso#String#recto,verso
urn:cite2:hmt:msA.v1.codex:#Codex URN#Cite2Urn#

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


  it should "happily build collections with multiple objects" in {
    val tinyCex = """#!citelibrary
name#demo
urn#urn:cite2:cex:testcoll.2017a:hdt1node
license#public domain

#!ctscatalog

urn#citationScheme#groupName#workTitle#versionLabel#exemplarLabel#online
urn:cts:greekLit:tlg0016.tlg001.loebeng:#book/section#Herodotus#Histories#English. trans. Godley [1921]##true#eng

#!ctsdata

urn:cts:greekLit:tlg0016.tlg001.loebeng:1.0#This is the Showing forth of the Inquiry of Herodotus of Halicarnassos, to the end that neither the deeds of men may be forgotten by lapse of time, nor the works great and marvellous, which have been produced some by Hellenes and some by Barbarians, may lose their renown; and especially that the causes may be remembered for which these waged war with one another.

#!citelibrary
name#demo
urn#urn:cite2:cex:testcoll.2017a:hdt1node
license#public domain

#!citecollections
URN#Description#Labelling property#Ordering property#License
urn:cite2:hmt:msA.v1:#Pages of the Venetus A manuscriptscript#urn:cite2:hmt:msA.v1.label:#urn:cite2:hmt:msA.v1.sequence:#CC-attribution-share-alike

#!citeproperties
Property#Label#Type#Authority list
urn:cite2:hmt:msA.v1.urn:#URN#Cite2Urn#
urn:cite2:hmt:msA.v1.label:#Label#String#
urn:cite2:hmt:msA.v1.siglum:#Manuscript siglum#String#
urn:cite2:hmt:msA.v1.sequence:#Page sequence#Number#
urn:cite2:hmt:msA.v1.rv:#Recto or Verso#String#recto,verso
urn:cite2:hmt:msA.v1.codex:#Codex URN#Cite2Urn#

#!citedata
siglum#sequence#urn#rv#label#codex
msA#1#urn:cite2:hmt:msA.v1:1r#recto#Marcianus Graecus Z. 454 (= 822) (Venetus A) folio 1r#urn:cite2:hmt:codex:msA
msA#2#urn:cite2:hmt:msA.v1:1v#verso#Marcianus Graecus Z. 454 (= 822) (Venetus A) folio 1v#urn:cite2:hmt:codex:msA
msA#3#urn:cite2:hmt:msA.v1:2r#recto#Marcianus Graecus Z. 454 (= 822) (Venetus A) folio 2r#urn:cite2:hmt:codex:msA
"""

    val citeLib = CiteLibrary(tinyCex,"#",",")
    val ccRepo = citeLib.collectionRepository.get

    // 3 objects with 6 properties each:
    val expectedProps = 3*6
    assert ( ccRepo.data.size == expectedProps)
  }




it should "build a vector of CiteNamespaces from CEX source" in {
  val cexSrc = """
#!citelibrary
name#demo
urn#urn:cite2:cex:testcoll.2017a:hdt1node
license#public domain

namespace#hmt#http://www.homermultitext.org/citens/hmt
"""
    val citeLib = CiteLibrary(cexSrc,"#",",")
    assert (citeLib.namespaces.size == 1)
    val ns = citeLib.namespaces(0)
    assert(ns.abbreviation == "hmt")
    assert(ns.uri == new URI("http://www.homermultitext.org/citens/hmt"))
}

it should "throw an exception if required metadata are missing" in {
val cexSrc = """
#!citelibrary
name#demo
urn#urn:cite2:cex:testcoll.2017a:hdt1node
"""
  try {
    val citeLib = CiteLibrary(cexSrc,"#",",")
    fail("Should not have created library")
  } catch {
    case iae: IllegalArgumentException => assert(iae.getMessage() == "requirement failed: CEX `citelibrary` block must include a licensing statement")
    case t: Throwable => fail("Should have thrown illegal argument exception.")
  }

}
it should "ignore 'comment' lines in metadata" in {
  val cexSrc = """
// Thoughfully annotated data...
#!citelibrary
name#demo
urn#urn:cite2:cex:testcoll.2017a:hdt1node
//
// license is never anything but a string:
license#public domain
"""
  val citeLib = CiteLibrary(cexSrc,"#",",")
  assert(citeLib.name == "demo")
  assert(citeLib.urn == Cite2Urn("urn:cite2:cex:testcoll.2017a:hdt1node"))
  assert(citeLib.license == "public domain")
}

it should "provide boolean reports on presence of optional components" in {
  val cex = """#!citelibrary
name#demo
urn#urn:cite2:cex:testcoll.2017a:hdt1node
license#public domain
#!ctscatalog
urn#citationScheme#groupName#workTitle#versionLabel#exemplarLabel#online
urn:cts:greekLit:tlg0016.tlg001.loebeng:#book/section#Herodotus#Histories#English. trans. Godley [1921]##true#eng
#!ctsdata
urn:cts:greekLit:tlg0016.tlg001.loebeng:1.0#This is the Showing forth of the Inquiry of Herodotus of Halicarnassos, to the end that neither the deeds of men may be forgotten by lapse of time, nor the works great and marvellous, which have been produced some by Hellenes and some by Barbarians, may lose their renown; and especially that the causes may be remembered for which these waged war with one another.


#!citecollections
URN#Description#Labelling property#Ordering property#License
urn:cite2:hmt:msA.v1:#Pages of the Venetus A manuscriptscript#urn:cite2:hmt:msA.v1.label:#urn:cite2:hmt:msA.v1.sequence:#CC-attribution-share-alike

#!citeproperties
Property#Label#Type#Authority list
urn:cite2:hmt:msA.v1.urn:#URN#Cite2Urn#
urn:cite2:hmt:msA.v1.label:#Label#String#
urn:cite2:hmt:msA.v1.siglum:#Manuscript siglum#String#
urn:cite2:hmt:msA.v1.sequence:#Page sequence#Number#
urn:cite2:hmt:msA.v1.rv:#Recto or Verso#String#recto,verso
urn:cite2:hmt:msA.v1.codex:#Codex URN#Cite2Urn#

#!citedata
siglum#sequence#urn#rv#label#codex
msA#1#urn:cite2:hmt:msA.v1:1r#recto#Marcianus Graecus Z. 454 (= 822) (Venetus A) folio 1r#urn:cite2:hmt:codex:msA
msA#2#urn:cite2:hmt:msA.v1:1v#verso#Marcianus Graecus Z. 454 (= 822) (Venetus A) folio 1v#urn:cite2:hmt:codex:msA
msA#3#urn:cite2:hmt:msA.v1:2r#recto#Marcianus Graecus Z. 454 (= 822) (Venetus A) folio 2r#urn:cite2:hmt:codex:msA
#!imagedata

urn:cite2:hmt:vaimg.v1:#CITE image URL#http://www.homermultitext.org/hmtdigital/images?#urn:cite2:hmt:msA.v1.rights:
urn:cite2:hmt:vaimg.v1:#CITE image string#http://www.homermultitext.org/hmtdigital/images?#urn:cite2:hmt:msA.v1.rights:
urn:cite2:hmt:vaimg.v1:#local jpeg#file://./images#urn:cite2:hmt:msA.v1.rights:
urn:cite2:hmt:vaimg.v1:#local file string#./#urn:cite2:hmt:msA.v1.rights:
"""
  val citeLib = CiteLibrary(cex,"#",",")
  assert(citeLib.hasCollections)
  assert(citeLib.hasTexts)

  val shortCex = """
#!citelibrary
name#demo
urn#urn:cite2:cex:testcoll.2017a:hdt1node
license#public domain
"""
  val noData = CiteLibrary(shortCex,"#", ",")
assert(noData.hasCollections == false)
assert(noData.hasTexts == false)
}

  it should "build a library with repeated ctscatalog blocks" in  {

    val twocats = """

#!citelibrary
name#demo
urn#urn:cite2:cex:testcoll.2017a:hdt1node
license#public domain

#!ctscatalog
urn#citationScheme#groupName#workTitle#versionLabel#exemplarLabel#online#lang
urn:cts:greekLit:tlg0012.tlg001.va_xml:#book,line#Homeric epic#Iliad#HMT project archival XML edition##true#grc

#!ctsdata
urn:cts:greekLit:tlg0012.tlg001.va_xml:1.5#<l n="5" xmlns:tei="http://www.tei-c.org/ns/1.0" xmlns="http://www.tei-c.org/ns/1.0">οἰωνοῖσί τε πᾶσι· <persName n="urn:cite2:hmt:pers.r1:pers8">Διὸς</persName> δ' ἐτελείετο βουλή· </l>

#!ctscatalog
urn#citationScheme#groupName#workTitle#versionLabel#exemplarLabel#online#lang
urn:cts:greekLit:tlg0012.tlg001.msA:#book,line#Homeric epic#Iliad#HMT project diplomatic edition##true#grc

#!ctsdata
urn:cts:greekLit:tlg0012.tlg001.msA:1.5#οἰωνοῖσί τε πᾶσι· Διὸς δ' ἐτελείετο βουλή·
"""
  val citeLib = CiteLibrary(twocats,"#",",")

  }

}
