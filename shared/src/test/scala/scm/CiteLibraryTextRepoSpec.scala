package edu.holycross.shot.scm
import org.scalatest.FlatSpec

import edu.holycross.shot.cite._
import edu.holycross.shot.cex._
import edu.holycross.shot.ohco2._

class CiteLibraryTextRepoSpec extends FlatSpec {

  "The CiteLibrary object" should "return None for TextRepository if no text repository is configured" in {

    val noData = """
#!citelibrary
license#public domain
name#Demo library
urn#urn:cite2:cex:democex.2017a:test
"""
    val lib = CiteLibrary(noData,"#",",")
    lib.textRepository match {
      case None => assert(true)
      case _ => fail ("Should have returned None")
    }
  }

  it should "return None if there is no catalog content" in {
    val noCat = """
#!citelibrary
license#public domain
name#Demo library
urn#urn:cite2:cex:democex.2017a:test
#!ctscatalog

#!ctsdata
urn:cts:greekLit:tlg0016.tlg001.loebeng:1.0#This is the Showing forth of the Inquiry of Herodotus of Halicarnassos, to the end that neither the deeds of men may be forgotten by lapse of time, nor the works great and marvellous, which have been produced some by Hellenes and some by Barbarians, may lose their renown; and especially that the causes may be remembered for which these waged war with one another.

"""

    val lib = CiteLibrary(noCat,"#",",")
    assert(lib.textRepository == None)
  }
  it should "return None if there is no text data content" in {

    val noData = """
#!citelibrary
license#public domain
name#Demo library
urn#urn:cite2:cex:democex.2017a:test
#!ctscatalog
urn#citationScheme#groupName#workTitle#versionLabel#exemplarLabel#online
urn:cts:greekLit:tlg0016.tlg001.loebeng:#book/section#Herodotus#Histories#English. trans. Godley [1921]##true#eng
#!ctsdata

"""

    val lib = CiteLibrary(noData,"#",",")
    assert(lib.textRepository == None)
  }




  it should "throw an exception if data and catalog conflict" in {
    val conflicted = """
#!citelibrary
license#public domain
name#Demo library
urn#urn:cite2:cex:democex.2017a:test
#!ctscatalog
urn#citationScheme#groupName#workTitle#versionLabel#exemplarLabel#online
urn:cts:greekLit:tlg0016.tlg001.loebeng:#book/section#Herodotus#Histories#English. trans. Godley [1921]##true#eng


#!ctsdata
urn:cts:greekLit:tlg0016.tlg001.WRONGVERSION:1.0#This is the Showing forth of the Inquiry of Herodotus of Halicarnassos, to the end that neither the deeds of men may be forgotten by lapse of time, nor the works great and marvellous, which have been produced some by Hellenes and some by Barbarians, may lose their renown; and especially that the causes may be remembered for which these waged war with one another.

"""

    try {
      val lib = CiteLibrary(conflicted,"#",",")
      fail("Should not have created library")
    } catch {
      case iae: IllegalArgumentException => assert(iae.getMessage().contains( "requirement failed: Online catalog (1 texts) did not match works appearing in corpus (1 texts)"))
      case t: Throwable => fail("Should have thrown IllegalArgumentException but got " + t)
    }
  }

  it should "return Some[TextRepository] if data and catalog match" in {
    val cex = """
#!citelibrary
license#public domain
name#Demo library
urn#urn:cite2:cex:democex.2017a:test
#!ctscatalog
urn#citationScheme#groupName#workTitle#versionLabel#exemplarLabel#online
urn:cts:greekLit:tlg0016.tlg001.loebeng:#book/section#Herodotus#Histories#English. trans. Godley [1921]##true#eng


#!ctsdata
urn:cts:greekLit:tlg0016.tlg001.loebeng:1.0#This is the Showing forth of the Inquiry of Herodotus of Halicarnassos, to the end that neither the deeds of men may be forgotten by lapse of time, nor the works great and marvellous, which have been produced some by Hellenes and some by Barbarians, may lose their renown; and especially that the causes may be remembered for which these waged war with one another.

"""
    val lib = CiteLibrary(cex,"#",",")
    lib.textRepository match {
      case trOpt : Some[TextRepository] => {
        assert(trOpt.get.catalog.size == 1)
        assert(trOpt.get.corpus.size == 1)
      }
      case _ => fail("Should have found a text repository")
    }

  }

}
