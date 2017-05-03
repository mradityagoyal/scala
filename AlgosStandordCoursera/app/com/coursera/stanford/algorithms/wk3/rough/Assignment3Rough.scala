package com.coursera.stanford.algorithms.wk3.rough

import scala.io.Source

object Assignment3Rough extends App{
  
  val filename = "/ssd/scala_github/scala/AlgosStandordCoursera/resources/QuickSort.txt"

  val ints: Array[Int] = Source.fromFile(filename).getLines().toList.map(_.toInt).toArray
  
  val (sorted, count) = QuickSort.sort(ints)
  println(count)
  
}