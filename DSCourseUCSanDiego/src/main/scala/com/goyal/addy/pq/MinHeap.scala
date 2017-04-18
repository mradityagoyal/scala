package com.goyal.addy.pq

import javax.swing.text.html.MinimalHTMLWriter

class MinHeap(val buffer: Array[Long]) {
  //parent is smaller than children. 
  var size = 0;

  def shiftUp(i: Int): Unit = {
    var idx = i
    //while value of node is less than value of parent.. we do a swap
    while (idx > 0 && buffer(idx) < buffer(parentIdx(idx))) {
      swap(parentIdx(idx), idx)
      idx = parentIdx(idx)
    }
  }

  def swap(a: Int, b: Int): Unit = {
    val temp = buffer(a)
    buffer(a) = buffer(b)
    buffer(b) = temp
  }

  def parentIdx(i: Int) = if (i == 0) 0 else ((i - 1) / 2)
  def leftChildIdx(i: Int) = 2 * i + 1
  def rightChildIdx(i: Int) = 2 * i + 2

  def shiftDown(i: Int): Unit = {
    var mindIndex = i
    val l = leftChildIdx(i)
    //if left is smaller than minIndex, minindex = l
    if (l < size && buffer(l) < buffer(mindIndex)) {
      mindIndex = l
    }
    val r = rightChildIdx(i)
    //if right is smaller than minIndex , minindex = r
    if (r < size && buffer(r) < buffer(mindIndex)) {
      mindIndex = r
    }
    if (i != mindIndex) {
      swap(i, mindIndex)
      shiftDown(mindIndex)
    }

  }

  def insert(p: Long): Unit = {
    buffer(size) = p
    shiftUp(size)
    size = size + 1
  }

  def getMin: Long = buffer(0)

  def extractMin: Long = {
    val result = getMin
    buffer(0) = buffer(size - 1)
    size = size - 1
    shiftDown(0)
    result
  }

  def remove(i: Int): Unit = {
    buffer(i) = Int.MinValue
    shiftUp(i)
    extractMin
  }

  def changePriority(i: Int, p: Long): Unit = {
    val oldP = buffer(i)
    buffer(i) = p
    if (p < oldP) shiftUp(i)
    else shiftDown(i)
  }

}

object MinHeap {
  def heapSort(arr: Array[Long]): Array[Long] = {
    val arr2 = Array.fill(arr.size)(-1L)
    val heap = new MinHeap(arr2)
    for (x <- arr) {
      heap.insert(x)
    }
    for (i <- (0 until heap.size)) {
      arr(i) = heap.extractMin
    }
    arr
  }

  def buildHeap(arr: Array[Long]): MinHeap = {
    val hp = new MinHeap(arr)
    hp.size = arr.size
    val startIdx = (arr.size - 1) / 2
    for (i <- startIdx to 0 by -1) {
      hp.shiftDown(i)
    }
    hp
  }

  def inPlaceHeapSort(input: Array[Long]): Array[Long] = {
    //build heap. 
    val heap = new MinHeap(input)
    heap.size = input.size
    val startIdx = (input.size - 1) / 2
    for (i <- startIdx to 0 by -1) {
      heap.shiftDown(i)
    }

    for (i <- (heap.size - 1) until 0 by -1) {
      heap.swap(0, i)
      heap.size = heap.size - 1
      heap.shiftDown(0)
    }
    heap.buffer
  }
}