package com.coursera.stanford.algorithms.wk2

import scala.annotation.tailrec

object CtInversion2 {
  
  @tailrec
  def mergeAndCountInversions(acc: Long,sorted: List[Int],  first: List[Int], second: List[Int]): (Long, List[Int]) = (first, second) match {
    case (Nil, ys) => (acc,  sorted ::: ys)
    case (xs, Nil) => (acc,  sorted ::: xs)
    case (x :: xs, y :: ys) => {
      if (x <= y) {
        mergeAndCountInversions(acc, sorted :+ x, xs, second)
      } else {
        mergeAndCountInversions(acc + first.length,sorted :+ y,  first, ys)
      }
    }
  }
  def addElementAtStart(element: Int)(tuple: (Int, List[Int])): (Int, List[Int]) = {
    (tuple._1, element :: tuple._2)
  }
  
  def countInversions(input: List[Int]): Long = {
    def countInvAndSort(input: List[Int]): (Long, List[Int]) = {
      val mid = input.length / 2
      if (mid == 0) (0, input)
      else {
        val (top, bottom) = input.splitAt(mid)
        val (inv_top, sorted_top) = countInvAndSort(top)
        val (inv_bottom, sorted_bottom) = countInvAndSort(bottom)
        mergeAndCountInversions(inv_top + inv_bottom, List(),  sorted_top, sorted_bottom)
      }
    }
    val (inversions, sorted) = countInvAndSort(input)
    inversions
  }
}