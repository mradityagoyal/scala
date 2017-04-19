package com.goyal.addy.datastructures.and.algorithms.wk2

/**
 * @author agoyal
 *
 * This is a min priority queue. represented as a heap.
 * @param <T> the type in the queue.
 */
class PriorityQueue[T <% Ordered[T]](val buffer: Array[T]) {
  
  val maxSize = buffer.size

  //startup code.. build the heap from input array. 
  val startIdx = (buffer.size - 1) / 2
  for (i <- startIdx to 0 by -1) {
    shiftDown(i)
  }

  def shiftUp(i: Int): Unit = {
    var idx = i
    //while value of node is less than value of parent.. we do a swap
    while (idx > 0 && buffer(idx) < buffer(parentIdx(idx))) {
      swap(parentIdx(idx), idx)
      idx = parentIdx(idx)
    }
  }

  def parentIdx(i: Int) = (i - 1) / 2
  def leftChildIdx(i: Int) = 2 * i + 1
  def rightChildIdx(i: Int) = 2 * i + 2

  /** swaps the values at given indexes. */
  def swap(a: Int, b: Int): Unit = {
    val temp = buffer(a)
    buffer(a) = buffer(b)
    buffer(b) = temp
  }

  def shiftDown(i: Int): Unit = {
    var minIdx = i
    val l = leftChildIdx(i)
    //if left is smaller than minIndex, minindex = l
    if (l < buffer.size && buffer(l) < buffer(minIdx)) {
      minIdx = l
    }
    val r = rightChildIdx(i)
    //if right is smaller than minIndex , minindex = r
    if (r < buffer.size && buffer(r) < buffer(minIdx)) {
      minIdx = r
    }
    if (i != minIdx) {
      swap(i, minIdx)
      shiftDown(minIdx)
    }

  }

  def getMin: T = buffer(0)

  def change(i: Int, x: T): Unit = {
    val oldP: T = buffer(i)
    buffer(i) = x
    if (x < oldP) shiftUp(i)
    else shiftDown(i)
  }

}