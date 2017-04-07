package com.coursera.stanford.algorithms.wk1

/**
 * @author agoyal
 *
 * Implementation of mergesort for wk1 of Algorithms couser from Stanford on Coursera
 *
 */
object MergeSort {

  def mergeSortedLists[A](first: List[A], second: List[A])(implicit cmp: Ordering[A]): List[A] = (first, second) match {
    case (Nil, ys) => ys
    case (xs, Nil) => xs
    case (x :: xs, y :: ys) => {
      if (cmp.lt(x, y)) x :: mergeSortedLists(xs, second)
      else y :: mergeSortedLists(first, ys)
    }
  }

  def mergeSort[A](in: List[A])(implicit cmp: Ordering[A]): List[A] = {
    val mid = in.length / 2
    if (mid == 0) in
    else {
      val (topHalf, bottomHalf) = in.splitAt(mid)
      mergeSortedLists(mergeSort(topHalf), mergeSort(bottomHalf))
    }
  }
  

}