package com.coursera.stanford.algorithms.wk3.rough

object QSTest extends App{
  
  val inputArray = Array(7086, -392152644, 0)
  
//  sorter.partition(0, inputArray.length)
//  
//  println(sorter.arr.mkString(","))
  
  val (sorted, count) = QuickSort.sort(inputArray)
  
  println(sorted.mkString(","))
  
  
}