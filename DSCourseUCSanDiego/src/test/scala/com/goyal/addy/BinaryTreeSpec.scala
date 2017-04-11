package com.goyal.addy

import org.scalatest.FlatSpec
import org.scalatest.Matchers
import org.scalatest.prop.PropertyChecks

class BinaryTreeSpec extends FlatSpec with Matchers with PropertyChecks{
  
  "A Node's height operation " should "behave correctly " in {
    val leaf = com.goyal.addy.Node("world")
    assert(leaf.height === 1)
    val root = Node("Hello", Some(Node("blah")), Some(Node("world")))
    assert(root.height === 2)
    assert(Node("hello", Some(root), None).height === 3)
  }
  
  it should "have correct size method" in {
    val leaf = com.goyal.addy.Node("world")
    leaf.size should === (1)
    val sz3 = Node("Hello", Some(Node("blah")), Some(Node("world")))
    sz3.size should === (3)
    Node("hello", Some(sz3), None).size should === (4)
  }
  
  it should "have correct nodesAtHeight() method" in {
    val (leaf, blah, wrld)  = (Node("Hello"), Node("blah"), Node ("world"))
    leaf.nodesAtHeight(1) should === (List(leaf))
    val sz3 = Node("Hello", Some(blah), Some(wrld))
    sz3.nodesAtHeight(2) should === (List(blah, wrld))
    Node("Hello", Some(blah), None).nodesAtHeight(2) should === (List(blah))
    sz3.nodesAtHeight(3).isEmpty should === (true)
  }
  
  
  
}