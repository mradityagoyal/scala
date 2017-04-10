package com.goyal.addy

import org.scalatest.FlatSpec
import org.scalatest.Matchers
import org.scalatest.prop.PropertyChecks

class BinaryTreeSpec extends FlatSpec with Matchers with PropertyChecks{
  
  "A leaf node" should "return height ==1 " in {
    val leaf = com.goyal.addy.Node("world")
    assert(leaf.height === 1)
    val root = Node("Hello", Some(Node("blah")), Some(Node("world")))
    assert(root.height === 2)
    assert(Node("hello", Some(root), None).height === 3)
  }
  
}