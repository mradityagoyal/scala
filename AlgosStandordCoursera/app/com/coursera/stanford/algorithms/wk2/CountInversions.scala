package com.coursera.stanford.algorithms.wk2

object CountInversions {
  def mergeAndCountInversions(first: List[Int], second: List[Int]): (Int, List[Int]) = (first, second) match {
    case (Nil, ys) => (0, ys)
    case (xs, Nil) => (0, xs)
    case (x :: xs, y :: ys) => {
      if (x <= y) {
        mergeAndCountInversions(xs, second) match {
          case (count, rest) => (count, x :: rest)
        }
      } else {
        mergeAndCountInversions(first, ys) match {
          case (count, rest) => (count + first.length, y :: rest)
        }
      }
    }
  }

  def countInversions(input: List[Int]): Int = {
    def countInvAndSort(input: List[Int]): (Int, List[Int]) = {
      val mid = input.length / 2
      if (mid == 0) (0, input)
      else {
        val (top, bottom) = input.splitAt(mid)
        val (inv_top, sorted_top) = countInvAndSort(top)
        val (inv_bottom, sorted_bottom) = countInvAndSort(bottom)
        val (inv_split, total_sorted) = mergeAndCountInversions(sorted_top, sorted_bottom)
        (inv_top + inv_bottom + inv_split, total_sorted)
      }
    }
    val (inversions, sorted) = countInvAndSort(input)
    inversions
  }
}