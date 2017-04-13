package edu.holycross.shot.scm
import org.scalatest.FlatSpec

class CiteLibrarySpec extends FlatSpec {

  "A CiteLibrary" should "support building a metadata set with no repositories" in {

    val cex = """#!citerepo
name#demo
version#2017.1
license#public domain
"""
    val citeRepo = CiteLibrary(cex,"#")
    assert(citeRepo.name == "demo")
    assert(citeRepo.version == "2017.1")
    assert(citeRepo.license == "public domain")
  }

  it should "support building a text repository from a CEX source string" in {

    val cex = """#!citerepo
name#demo
version#2017.1
license#public domain
#!ctscatalog
urn#citationScheme#groupName#workTitle#versionLabel#exemplarLabel#online
urn:cts:greekLit:tlg0016.tlg001.loebeng:#book/section#Herodotus#Histories#English. trans. Godley [1921]##true
#!ctsdata
urn:cts:greekLit:tlg0016.tlg001.loebeng:1.0#This is the Showing forth of the Inquiry of Herodotus of Halicarnassos, to the end that neither the deeds of men may be forgotten by lapse of time, nor the works great and marvellous, which have been produced some by Hellenes and some by Barbarians, may lose their renown; and especially that the causes may be remembered for which these waged war with one another.
"""


    val citeRepo = CiteLibrary(cex,"#")
    assert(citeRepo.hasTexts)
  }

  it should "handle missing metadata gracefully" in pending
  it should "ignore 'comment' lines in metadata" in pending
  // but how?



}
