package com.goyal.addy.datastructures.and.algorithms.wk2.rough

class PQueue(maxSize: Int = 10) {
  var size = 0;
  val buffer: Array[Int] = Array.fill(maxSize+1)(-1)
  
  def shiftUp(i: Int): Unit = {
    var idx = i
    //while value of node is greater than value of parent.. we do a swap
    while (idx > 1 && buffer(parentIdx(idx)) < buffer(idx)) {
      swap(idx, parentIdx(idx))
      idx = parentIdx(idx)
    }
  }

  def swap(a: Int, b: Int): Unit = {
    val temp = buffer(a)
    buffer(a) = buffer(b)
    buffer(b) = temp
  }

  def parentIdx(i: Int) = i / 2
  def leftChildIdx(i: Int) = 2 * i
  def rightChildIdx(i: Int) = 2 * i + 1

  def shiftDown(i: Int): Unit = {
    var maxIndex = i
    val l = leftChildIdx(i)
    if (l <= size && buffer(l) > buffer(maxIndex)) {
      maxIndex = l
    }
    val r = rightChildIdx(i)
    if (r <= size && buffer(r) > buffer(maxIndex)) {
      maxIndex = r
    }
    if (i != maxIndex) {
      swap(i, maxIndex)
      shiftDown(maxIndex)
    }

  }
  
  def insert(p: Int): Unit = {
    if(size == maxSize) throw new RuntimeException("can't add more than max size")
    size = size + 1
    buffer(size) = p
    shiftUp(size)
  }
  
  def getMax: Int = buffer(1)
  
  def extractMax: Int = {
    val result = getMax
    buffer(1) = buffer(size)
    size = size -1
    shiftDown(1)
    result
  }
  
  def remove(i: Int): Unit = {
    buffer(i) = Int.MaxValue
    shiftUp(i)
    extractMax
  }
  
  def changePriority(i: Int,p: Int): Unit  ={
    val oldP = buffer(i)
    buffer(i) = p
    if (p > oldP) shiftUp(i)
    else shiftDown(i)
  }

}
