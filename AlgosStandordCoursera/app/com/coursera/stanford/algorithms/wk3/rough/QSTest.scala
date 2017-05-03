package com.coursera.stanford.algorithms.wk3.rough

object QSTest extends App{
  
  val inputArray = Array(1,5,11,0, 1 ,-100)
  
//  sorter.partition(0, inputArray.length)
//  
//  println(sorter.arr.mkString(","))
  
  val (sorted, count) = QuickSort.sort(inputArray)
  
  println(sorted.mkString(","))
  
  
}