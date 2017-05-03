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
  def partition(arr: Array[Int], l: Int, r: Int): Int = {
    val pivot: Int = arr(l)
    var i = l + 1
    for (j <- l + 1 until r) {
      if (arr(j) < pivot) {
        swap(arr, i, j)
        i = i + 1
      }
    }
    swap(arr, l, i - 1)
    i - 1 //return the index of the pivot after the partitioning is over. 
  }

  def swap(arr: Array[Int], i: Int, j: Int): Unit = {
    val temp = arr(i)
    arr(i) = arr(j)
    arr(j) = temp
  }

  def sort(arr: Array[Int]): (Array[Int], Int) = {

    var comparisonCount = 0
    
    def sortHelper(startIdx: Int, endIdx: Int) {
      val length = endIdx - startIdx
      if (length > 1) {
        comparisonCount = comparisonCount + length -1
        val idx = partition(arr, startIdx, endIdx)
        sortHelper(startIdx, idx)
        sortHelper(idx + 1, endIdx)
      }
    }
    sortHelper(0, arr.length)

    (arr, comparisonCount)
  }

}