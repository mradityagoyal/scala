package com.coursera.stanford.algorithms.wk3.rough

object QuickSort {

  /**
   * @return the index of the pivot
   */
  def getPivotElementIndex: Int = 0 //TODO: Implement

  /**
   * Partitions the inputArr around the pivotIndex
   * @param pivotIndex
   *
   */

  def sort(arr: Array[Int]): (Array[Int], Int) = {

    var comparisonCount = 0

    def sortHelper(startIdx: Int, endIdx: Int) {
      val length = endIdx - startIdx
      if (length > 1) {
        comparisonCount = comparisonCount + length - 1
        preprocess(startIdx, endIdx)
        val idx = partition(startIdx, endIdx)
        sortHelper(startIdx, idx)
        sortHelper(idx + 1, endIdx)
      }
    }

    def preprocess(startIdx: Int, endIdx: Int) = {
      val left = arr(startIdx)
      val right = arr(endIdx -1)
      val len = endIdx - startIdx
      
      val middle = arr(startIdx + len/2 ) 
      
      val pivot = medianOf3(left, right, middle)
      if(pivot == left) {}
      else if (pivot == right) swap(startIdx, endIdx -1)
      else if (pivot == middle) swap(startIdx, startIdx + len/2)
    }

    def partition(l: Int, r: Int): Int = {
      val pivot: Int = arr(l)
      var i = l + 1
      for (j <- l + 1 until r) {
        if (arr(j) < pivot) {
          swap(i, j)
          i = i + 1
        }
      }
      swap(l, i - 1)
      i - 1 //return the index of the pivot after the partitioning is over. 
    }

    def swap(i: Int, j: Int): Unit = {
      val temp = arr(i)
      arr(i) = arr(j)
      arr(j) = temp
    }

    sortHelper(0, arr.length)

    (arr, comparisonCount)
  }

  def medianOf3(a: Int, b: Int, c: Int): Int = {
    if (( a - b) * (c - a)  >= 0) a
    else if ((b - a) * (c - b) >= 0) b
    else c
  }

}