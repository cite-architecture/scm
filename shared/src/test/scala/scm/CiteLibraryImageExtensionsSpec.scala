package edu.holycross.shot.scm
import org.scalatest.FlatSpec

import edu.holycross.shot.cite._
import edu.holycross.shot.cex._
import edu.holycross.shot.citeobj._

class CiteLibraryImageExtensionsSpec extends FlatSpec {



  "The CiteLibrary object" should "return None if no image extensions  are configured" in {
    val nodata = """
#!citelibrary
name#Demo of DSE structure: Venetus A manuscript, folio 12 recto
urn#urn:cite2:dse:demo.2017a:va12r
license#public domain
"""

    val citeLib = CiteLibrary(nodata,"#",",")
    citeLib.imageExtensions match {
      case None => assert(true)
      case _ => fail("Whoops, found this for c")
    }
  }
  it should "return None if there is no content in image extensions" in {
    val emptydata = """
#!citelibrary
name#Demo of DSE structure: Venetus A manuscript, folio 12 recto
urn#urn:cite2:dse:demo.2017a:va12r
license#public domain

#!imagedata
"""
    val citeLib = CiteLibrary(emptydata,"#",",")
    citeLib.imageExtensions match {
      case None => assert(true)
      case _ => fail("Whoops, found this for c")
    }
  }



  it should "return None if there image extensions do not match collections data" in pending /* {

    val noMatch = """
#!citelibrary
name#Demo of DSE structure: Venetus A manuscript, folio 12 recto
urn#urn:cite2:dse:demo.2017a:va12r
license#public domain

#!imagedata
urn:cite2:hmt:vaimg.2017a:#local jpeg string#./#urn:cite2:hmt:vaimg.2017a.rights:

"""

    val citeLib = CiteLibrary(noMatch,"#",",")
    citeLib.imageExtensions match {
      case None => assert(true)
      case _ => fail("Whoops, found this for c")
    }

  }*/

  it should "return some collection if image extensions and collections are consistent" in {
    val legit = """
#!citelibrary
name#Demo of DSE structure: Venetus A manuscript, folio 12 recto
urn#urn:cite2:dse:demo.2017a:va12r
license#public domain

#!imagedata
urn:cite2:hmt:vaimg.2017a:#local jpeg string#./#urn:cite2:hmt:vaimg.2017a.rights:


#!citecatalog

collection#urn:cite2:hmt:vaimg.2017a:#Images of the Venetus A manuscriptscript#urn:cite2:hmt:vaimg.2017a.caption:##CC-attribution-share-alike

property#urn:cite2:hmt:vaimg.2017a.urn:#URN#Cite2Urn#
property#urn:cite2:hmt:vaimg.2017a.caption:#Caption#String#
property#urn:cite2:hmt:vaimg.2017a.rights:#Rights#String#

#!citedata
urn#caption#rights
urn:cite2:hmt:vaimg.2017a:VA012RN_0013.2017#Natural light photograph of Venetus A: Marcianus Graecus Z. 454 (= 822), folio 12, recto#This image was derived from an original ©2007, Biblioteca Nazionale Marciana, Venezie, Italia. The derivative image is ©2010, Center for Hellenic Studies. Original and derivative are licensed under the Creative Commons Attribution-Noncommercial-Share Alike 3.0 License. The CHS/Marciana Imaging Project was directed by David Jacobs of the British Library.

"""
    val ie = ImageExtensions(legit,"#")
    val citeLib = CiteLibrary(legit,"#",",")
    citeLib.imageExtensions match {
      case None => fail("Whoops, no image collection")
      case imgOpt: Some[ImageExtensions]=> assert(true)
    }
  }


}
