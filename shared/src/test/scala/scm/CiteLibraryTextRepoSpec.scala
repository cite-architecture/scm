package edu.holycross.shot.scm
import org.scalatest.FlatSpec

import edu.holycross.shot.cite._
import edu.holycross.shot.cex._

class CiteLibraryTextRepoSpec extends FlatSpec {

  "The CiteLibrary object" should "return None if no text repository is configured" in {

    val noData = """
#!citelibrary
license#public domain
name#Demo library
urn#urn:cite2:cex:democex.2017a:test
"""
    val lib = CiteLibrary(noData,"#",",")
    assert(lib.textRepository == None)
  }

  it should "return None if there is no catalog content" in pending
  it should "return None if there is no text data content" in pending
  it should "throw an excpetion if data and catalog conflict" in pending



}