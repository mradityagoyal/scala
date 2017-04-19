package com.goyal.addy.pq

import scala.collection.mutable.ListBuffer
import scala.io.StdIn
import scala.collection.mutable.ArrayBuffer

case class MinHeap(val buffer: Array[Int]) {
  //parent is smaller than children. 
  def size = buffer.size
  val swaps: scala.collection.mutable.ArrayBuffer[(Int, Int)] = new ArrayBuffer()
  
  

  def shiftUp(i: Int): Unit = {
    var idx = i
    //while value of node is less than value of parent.. we do a swap
    while (idx > 0 && buffer(idx) < buffer(parentIdx(idx))) {
      swap(parentIdx(idx), idx)
      idx = parentIdx(idx)
    }
  }

  def swap(a: Int, b: Int): Unit = {
    swaps.append((a,b))
    val temp = buffer(a)
    buffer(a) = buffer(b)
    buffer(b) = temp
  }

  def parentIdx(i: Int) = if (i == 0) 0 else ((i - 1) / 2)
  def leftChildIdx(i: Int) = 2 * i + 1
  def rightChildIdx(i: Int) = 2 * i + 2

  def shiftDown(i: Int): Unit = {
    var minIdx = i
    val l = leftChildIdx(i)
    //if left is smaller than minIndex, minindex = l
    if (l < size && buffer(l) < buffer(minIdx)) {
      minIdx = l
    }
    val r = rightChildIdx(i)
    //if right is smaller than minIndex , minindex = r
    if (r < size && buffer(r) < buffer(minIdx)) {
      minIdx = r
    }
    if (i != minIdx) {
      swap(i, minIdx)
      shiftDown(minIdx)
    }

  }

//  def insert(p: Int): Unit = {
//    buffer(size) = p
//    shiftUp(size)
//    size = size + 1
//  }

  def getMin: Int = buffer(0)

//  def extractMin: Int = {
//    val result = getMin
//    buffer(0) = buffer(size - 1)
//    size = size - 1
//    shiftDown(0)
//    result
//  }

//  def remove(i: Int): Unit = {
//    buffer(i) = Int.MinValue
//    shiftUp(i)
//    extractMin
//  }

  def changePriority(i: Int, p: Int): Unit = {
    val oldP = buffer(i)
    buffer(i) = p
    if (p < oldP) shiftUp(i)
    else shiftDown(i)
  }

}

object MinHeap extends App{
  
  //ignore first line. 
  StdIn.readLine();
  val line2 = StdIn.readLine();
  val ints = line2.split(" ").map(_.toInt)
  val hp = buildHeap(ints)
  println(hp.swaps.length)
  for((i,j) <- hp.swaps){
    println(s"$i $j")
  }
  
//  def heapSort(arr: Array[Int]): Array[Int] = {
//    val arr2 = Array.fill(arr.size)(-1)
//    val heap = new MinHeap(arr2)
//    for (x <- arr) {
//      heap.insert(x)
//    }
//    for (i <- (0 until heap.size)) {
//      arr(i) = heap.extractMin
//    }
//    arr
//  }

  def buildHeap(arr: Array[Int]): MinHeap = {
    val hp = new MinHeap(arr)
//    hp.size = arr.size
    val startIdx = (arr.size - 1) / 2
    for (i <- startIdx to 0 by -1) {
      hp.shiftDown(i)
    }
    hp
  }

//  def inPlaceHeapSort(input: Array[Int]): Array[Int] = {
//    //build heap. 
//    val heap = new MinHeap(input)
//    heap.size = input.size
//    val startIdx = (input.size - 1) / 2
//    for (i <- startIdx to 0 by -1) {
//      heap.shiftDown(i)
//    }
//
//    for (i <- (heap.size - 1) until 0 by -1) {
//      heap.swap(0, i)
//      heap.size = heap.size - 1
//      heap.shiftDown(0)
//    }
//    heap.buffer
//  }
}