package com.coursera.stanford.algorithms.wk1

import scala.math.Ordering.IntOrdering

object SortingWS {
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet

  val sorted = Sorting.selection_sort(Seq("11","1","2","22","12"))
                                                  //> sorted  : Seq[String] = List(1, 11, 12, 2, 22)
  
  
 //val (min,rest) = Sorting.minAndRest(scala.math.Ordering.Int)(Vector(1,2, 11, 9, 12 , 1))
  
  val x = Sorting.minAndRest(Vector(1))           //> x  : Option[(Int, Vector[Int])] = Some((1,Vector()))
  
  Util.isSorted(List(1, 4, 2))                    //> res0: Boolean = false
  
   
  val sorted_vs = Sorting.vector_selection_sort(Vector(1,4,2,7,11,32,-1, -1))
                                                  //> sorted_vs  : Vector[Int] = Vector(-1, -1, 1, 2, 4, 7, 11, 32)
  Vector()                                        //> res1: scala.collection.immutable.Vector[Nothing] = Vector()
  
  Sorting.mergeSortedVectors(Vector(1,2,11), Vector(2,5,15))
                                                  //> res2: Vector[Int] = Vector(1, 2, 2, 5, 11, 15)
  
  
  
}