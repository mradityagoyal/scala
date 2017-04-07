package com.coursera.stanford.algorithms.wk2

import scala.io.Source

object Assignment extends App {

  val filename = "/ssd/scala_github/scala/AlgosStandordCoursera/resources/IntegerArray.txt"

  val ints: List[Int] = Source.fromFile(filename).getLines().toList.map(_.toInt)
  
//  val intsTop10K = ints.take(10000)
  
  val inversions = CtInversion2.countInversions(ints)
  print (s"Number of Inversions: $inversions")

}