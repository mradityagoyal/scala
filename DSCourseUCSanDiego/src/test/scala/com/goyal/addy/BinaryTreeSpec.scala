package com.goyal.addy

import org.scalatest.FlatSpec
import org.scalatest.Matchers
import org.scalatest.prop.PropertyChecks

class BinaryTreeSpec extends FlatSpec with Matchers with PropertyChecks {

  "A Node's height operation " should "behave correctly " in {
    val leaf = com.goyal.addy.BinaryTree("WWWW")
    assert(leaf.height === 1)
    val root = BinaryTree("PPPP", Some(BinaryTree("AAAA")), Some(BinaryTree("WWWW")))
    assert(root.height === 2)
    val error = intercept[IllegalArgumentException] {
      val illegal = BinaryTree("AAAA", Some(BinaryTree("BBBB")), None)
    }
    assert(!error.getMessage.isEmpty())
  }

  it should "have correct size method" in {
    val leaf = com.goyal.addy.BinaryTree("world")
    leaf.size should ===(1)
    val sz3 = leaf.add("Hello").add("123")
    sz3.size should ===(3)
    sz3.add("3421").size should ===(4)
  }

  //TODO: ignored for now due to comparison check not implemented. 
  it should "have correct nodesAtHeight() method" in {
    val (leaf, blah, wrld) = (BinaryTree("Hello"), BinaryTree("blah"), BinaryTree("world"))
    leaf.nodesAtHeight(1) should ===(List(leaf))
    val sz3 = BinaryTree("Hello").add("some").add("other")
    sz3.nodesAtHeight(2) should ===(List(blah, wrld))
    BinaryTree("Hello", Some(blah), None).nodesAtHeight(2) should ===(List(blah))
    sz3.nodesAtHeight(3).isEmpty should ===(true)
  }

}