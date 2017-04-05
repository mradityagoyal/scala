package com.coursera.stanford.algorithms.wk1

import scala.math.Ordering.IntOrdering

object SortingWS {
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet

  case class Box[T](value: T)

  class BoxOrdering[T](ordering: Ordering[T]) extends Ordering[Box[T]] {
    def compare(x: Box[T], y: Box[T]): Int = ordering.compare(x.value, y.value)
  }

  val ordering = new BoxOrdering(scala.math.Ordering.Int)
                                                  //> ordering  : com.coursera.stanford.algorithms.wk1.SortingWS.BoxOrdering[Int] 
                                                  //| = com.coursera.stanford.algorithms.wk1.SortingWS$$anonfun$main$1$BoxOrdering
                                                  //| $1@3532ec19
  val sorted = Sorting.selection_sort(scala.math.Ordering.String)(Seq("11","1","2","22","12"))
                                                  //> sorted  : Seq[String] = List(1, 11, 12, 2, 22)
  
  
 //val (min,rest) = Sorting.minAndRest(scala.math.Ordering.Int)(Vector(1,2, 11, 9, 12 , 1))
  
  val x = Sorting.minAndRest(scala.math.Ordering.Int)(Vector(1))
                                                  //> x  : Option[(Int, Vector[Int])] = Some((1,Vector()))
  
   
  val sorted_vs = Sorting.vector_selection_sort(scala.math.Ordering.Int)(Vector(1,4,2,7,11,32,-1, -1))
                                                  //> sorted_vs  : Vector[Int] = Vector(-1, -1, 1, 2, 4, 7, 11, 32)
  Vector()                                        //> res0: scala.collection.immutable.Vector[Nothing] = Vector()
  
  Sorting.mergeSortedVectors(Vector(1,2,11), Vector(2,5,15))
                                                  //> res1: Vector[Int] = Vector(1, 2, 2, 5, 11, 15)
  
  
  
}