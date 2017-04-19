package com.goyal.addy.datastructures.and.algorithms.wk1

import org.scalatest.FlatSpec
import org.scalatest.Matchers
import org.scalatest.prop.PropertyChecks

import com.goyal.addy.datastructures.and.algorithms.wk1.BinaryTree

class BinaryTreeSpec extends FlatSpec with Matchers with PropertyChecks {
  
  "A Node's height operation " should "behave correctly " in {
    val leaf = BinaryTree("WWWW")
    assert(leaf.height === 1)
    val root = BinaryTree("PPPP", Some(BinaryTree("AAAA")), Some(BinaryTree("WWWW")))
    assert(root.height === 2)
    val error = intercept[IllegalArgumentException] {
      val illegal = BinaryTree("AAAA", Some(BinaryTree("BBBB")), None)
    }
    assert(!error.getMessage.isEmpty())
  }

  it should "have correct size method" in {
    val leaf = BinaryTree("world")
    leaf.size should ===(1)
    val sz3 = leaf.push("Hello").push("123")
    sz3.size should ===(3)
    sz3.push("3421").size should ===(4)
  }

  //TODO: ignored for now due to comparison check not implemented. 
//  it should "have correct nodesAtHeight() method" in {
//    val (leaf, blah, wrld) = (BinaryTree("Hello"), BinaryTree("blah"), BinaryTree("world"))
//    leaf.nodesAtHeight(1) should ===(List(leaf))
//    val sz3 = BinaryTree("Hello").push("some").push("other")
//    sz3.nodesAtHeight(2) should ===(List(blah, wrld))
//    BinaryTree("Hello", Some(blah), None).nodesAtHeight(2) should ===(List(blah))
//    sz3.nodesAtHeight(3).isEmpty should ===(true)
//  }
  
  val t1 = BinaryTree(5)
  val t2 = t1.push(9)
  val t3 = t2.push(2)
  val t4 = t3.push(11)
  
  "A BinrayTree" should "have correct push method " in {
    t2.size should === (t1.size + 1)
    t3.size should === (t2.size + 1)
  }
  it should " return Min correctly" in {
    t1.min should === (5)
    t2.min should === (5)
    t3.min should === (2)
    t4.min should === (2)
  }
  it should "return max correctly" in {
    t1.max should === (5)
    t2.max should === (9)
    t3.max should === (9)
    t4.max should === (11)
  }
  it should "add trees correctly" in {
    t1.add(t2).size should === (t1.size + t2.size)
    t2.add(t4).size should === (t2.size + t4.size)
  }
  
  it should "have correct inOrder method" in {
    val inOrd = t4.inOrder
    inOrd.size should === (t4.size)
    val sorted = inOrd.sorted
    inOrd should === (sorted)
  }

}