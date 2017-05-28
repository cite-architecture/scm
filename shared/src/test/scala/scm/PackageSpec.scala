package edu.holycross.shot.scm
import org.scalatest.FlatSpec

class PackageSpec extends FlatSpec {

  "The package object" should "create a Map from CEX data" in {

    val cex = """name#demo
version#2017.1
license#public domain
"""
    val cexMap = cexData(cex,"#")
    assert(cexMap("name") == "demo")
    assert(cexMap("version" ) == "2017.1")
    assert(cexMap("license" ) == "public domain")
  }  
}
