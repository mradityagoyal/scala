package com.coursera.stanford.algorithms.wk1

import scala.math.Ordering.IntOrdering

object SortingWS {
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet

  val sorted = Sorting.selection_sort(Seq("11","1","2","22","12"))
                                                  //> sorted  : Seq[String] = List(1, 11, 12, 2, 22)
  
  
 //val (min,rest) = Sorting.minAndRest(scala.math.Ordering.Int)(Vector(1,2, 11, 9, 12 , 1))
  
  Sorting.minAndRest(Vector(1,2))                 //> res0: Option[(Int, Seq[Int])] = None
  
  
   
  //val sorted_vs = Sorting.vector_selection_sort(Vector(-1))
  
  val sorted_s = Sorting.selection_sort(Vector(0, 1249825549, 1))
                                                  //> sorted_s  : Seq[Int] = List(0, 1, 1249825549)
  
  Sorting.mergeSortedVectors(Vector(), Vector(2,5,15))
                                                  //> res1: Vector[Int] = Vector(2, 5, 15)
  
  def sort(in: Vector[Int]): Vector[Int] = in.sorted
                                                  //> sort: (in: Vector[Int])Vector[Int]
  sort(Vector())                                  //> res2: Vector[Int] = Vector()
  
  Sorting.mergeSortedVectors(Vector(), Vector())(scala.math.Ordering.Int)
                                                  //> res3: Vector[Int] = Vector()
  
  
  
  
  
}