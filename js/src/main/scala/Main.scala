package edu.holycross.shot.scm

import scala.scalajs.js
import edu.holycross.shot.citeobj._


object Main extends js.JSApp {
  def main(): Unit = {

        val cexSrc = """#!citelibrary
name#demo
urn#urn:cite2:cex:testcoll:hdt1node
license#public domain

#!ctscatalog

urn#citationScheme#groupName#workTitle#versionLabel#exemplarLabel#online
urn:cts:greekLit:tlg0016.tlg001.loebeng:#book/section#Herodotus#Histories#English. trans. Godley [1921]##true

#!ctsdata

urn:cts:greekLit:tlg0016.tlg001.loebeng:1.0#This is the Showing forth of the Inquiry of Herodotus of Halicarnassos, to the end that neither the deeds of men may be forgotten by lapse of time, nor the works great and marvellous, which have been produced some by Hellenes and some by Barbarians, may lose their renown; and especially that the causes may be remembered for which these waged war with one another.

#!citelibrary
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


#!imagedata

urn:cite2:hmt:vaimg.v1:#CITE image URL#http://www.homermultitext.org/hmtdigital/images?#urn:cite2:hmt:msA.v1.rights:
urn:cite2:hmt:vaimg.v1:#CITE image string#http://www.homermultitext.org/hmtdigital/images?#urn:cite2:hmt:msA.v1.rights:
urn:cite2:hmt:vaimg.v1:#local jpeg#file://./images#urn:cite2:hmt:msA.v1.rights:
urn:cite2:hmt:vaimg.v1:#local file string#./#urn:cite2:hmt:msA.v1.rights:
"""

      val citelib = CiteLibrary(cexSrc,"#",",")
      println("Made a CITE Library.")
      citelib.collectionRepository match {
        case None => println("But failed to make a collection repository fo it!")
        case cr : Option[CiteCollectionRepository] => {
          assert(cr.get.catalog.size == 1)
          assert(cr.get.data.size == 18)
          println(s"Its collection repository has ${cr.get.catalog.size} cataloged collection(s) and ${cr.get.data.size} property values." )
        }
      }

      citelib.imageExtensions match {
        case None => println("Failed to load image extensions")
        case imgOption: Option[ImageExtensions] => {
          val imgs = imgOption.get
          assert(imgs.protocolMap.size == 1)
          println(s"Mapped ${imgs.protocolMap.size} colleciton to binary image source")
        }
      }



  }
}
