package addy.datastructures.and.algorithms.wk3

import scala.annotation.tailrec

/**
  * Created by agoyal on 7/20/17.
  */
class BinaryMaxHeap(var size: Int,  val heap: Array[Int]) {
  assert(size <= heap.length)
  val MAX_SIZE = heap.length

  def parent(i: Int): Int = i / 2

  def leftChild(i: Int): Int = 2 * i

  def rightChild(i: Int): Int = leftChild(i) + 1

  def getMax: Int = if (size > 0) heap(1) else throw new RuntimeException("max of empty Tree. ") //heap starts with 1 based index to make parent and left and right child calculation work. it fails for 0

  //insert a new element in the heap.
  def insert(x: Int): Unit = {
    if (size == MAX_SIZE) { //TODO: Find scala way to handle these cases and dont throw exception.
      throw new RuntimeException("Added enough")
    } else {
      size = size + 1 //automatically ensures nothing is stored at index 0 in heap.
      heap(size) = x
      shiftUp(size)
    }
  }

  //extract max from the heap.
  def extractMax: Int = {
    val result = heap(1) //store max in temp.
    heap(1) = heap(size) //replace root with last leaf.
    size = size - 1
    shiftDown(1) //shiftDown to revalidate heap.
    result //return result.
  }

  def remove(i: Int): Unit = {
    heap(i) = Integer.MAX_VALUE
    shiftUp(i)
    extractMax
  }

  def changePriority(i: Int, p: Int): Unit = {
    val oldP = heap(i)
    heap(i) = p
    if (p > oldP) shiftUp(i) else shiftDown(i)
  }


  //swaps the values at x and y index in the heap.
  def swap(x: Int, y: Int): Unit = {
    val temp = heap(x)
    heap(x) = heap(y)
    heap(y) = temp
  }

  @tailrec
  final def shiftUp(i: Int): Unit = i match {
    case x if x > 1 => {
      val parentIdx = parent(i)
      if (heap(parentIdx) < heap(i)) {
        swap(parentIdx, i)
        shiftUp(parentIdx)
      }
    }
    case _ =>
  }

  @tailrec
  final def shiftDown(i: Int): Unit = {
    var maxIdx = i
    val l = leftChild(i)
    //check if left child is max.
    if (l <= size && heap(l) > heap(maxIdx)) {
      maxIdx = l
    }
    val r = rightChild(i)
    //check if right child is max.
    if (r <= size && heap(r) > heap(maxIdx)) {
      maxIdx = r
    }
    //this will happen only when root is not larger than the left and right child. so then swap and shiftDown.
    if (i != maxIdx) {
      swap(i, maxIdx)
      shiftDown(maxIdx)
    }
  }

}

object BinaryMaxHeap {

  // build a heap from an array.
  def buildHeap(arr: Array[Int]): BinaryMaxHeap = {
    val heap = -1 +: arr //adding a value at 0th position as our heap array starts at 1.
    val bmh = new BinaryMaxHeap(arr.length, heap)
    for (i <- heap.length/2 to 1 by -1){
      bmh.shiftDown(i)
    }
    bmh
  }

  //return an empty heap.
  def emptyHeap(maxSize: Int): BinaryMaxHeap  = new BinaryMaxHeap(0, new Array[Int](maxSize+1))

  def heapSort(in: Array[Int]): Array[Int] = {
    val bmh = buildHeap(in)
    for(i <- 0 until bmh.size){
      bmh.swap(1, bmh.size)
      bmh.size = bmh.size - 1
      bmh.shiftDown(1)
    }
    bmh.heap.tail
  }
}
