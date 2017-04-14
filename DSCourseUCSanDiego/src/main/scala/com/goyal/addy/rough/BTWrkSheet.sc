package com.goyal.addy

import scala.util.Random


object BTWrkSheet {
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet

  val range = 0 to 10                             //> range  : scala.collection.immutable.Range.Inclusive = Range(0, 1, 2, 3, 4, 5
                                                  //| , 6, 7, 8, 9, 10)
  val ints = range.map(_ => Random.nextInt(100))  //> ints  : scala.collection.immutable.IndexedSeq[Int] = Vector(48, 3, 8, 27, 19
                                                  //| , 65, 1, 43, 19, 15, 89)
  
  val zero: BinaryTree[Int] = BinaryTree(ints.head)
                                                  //> zero  : com.goyal.addy.BinaryTree[Int] = BinaryTree(48,None,None)
  ints.tail.foldLeft(zero)((x,y) => x.push(y))    //> res0: com.goyal.addy.BinaryTree[Int] = BinaryTree(48,Some(BinaryTree(3,Some(
                                                  //| BinaryTree(1,None,None)),Some(BinaryTree(8,None,Some(BinaryTree(27,Some(Bina
                                                  //| ryTree(19,Some(BinaryTree(19,Some(BinaryTree(15,None,None)),None)),None)),So
                                                  //| me(BinaryTree(43,None,None)))))))),Some(BinaryTree(65,None,Some(BinaryTree(8
                                                  //| 9,None,None)))))
           



}