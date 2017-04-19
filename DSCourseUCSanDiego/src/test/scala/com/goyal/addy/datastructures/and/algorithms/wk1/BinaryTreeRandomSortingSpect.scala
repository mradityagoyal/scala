package com.goyal.addy.datastructures.and.algorithms.wk1

import org.scalatest.FlatSpec
import org.scalatest.Matchers
import org.scalatest.prop.PropertyChecks
import scala.util.Random
import com.goyal.addy.datastructures.and.algorithms.wk1.BinaryTree

class BinaryTreeRandomSortingSpect extends FlatSpec with Matchers with PropertyChecks{
  
  val n = 10
  val rng = 0 to n
  val ints: List[Int]  = rng.map(_ => Random.nextInt(100)).toList
  val zero = BinaryTree(ints.head)
  val randomTree = ints.tail.foldLeft(zero)((acc, next)=> acc.push(next))
  
  "A random tree"  should "Behave properly " in {
    //min equal 
    randomTree.min should === (ints.min) 
    //max equal
    randomTree.max should === (ints.max)
    //inorder is correct
    randomTree.inOrder should === (ints.sorted)
  }
  
}