package com.goyal.addy.pq

object MinHeapTest extends App{
  
//  val sorted = MinHeap.heapSort(Array(1,12,4,5,19, 0))
//  print(sorted.foldRight("")((a, next)=> a + "," + next))
  
  val input = Array[Long](1,12,4,5,19, 0)
  val sorted: Array[Long] = MinHeap.inPlaceHeapSort(input)
  
  
  
  print(sorted.foldRight("")((a, next)=> a + "," + next))
  
  
  
  
  
}