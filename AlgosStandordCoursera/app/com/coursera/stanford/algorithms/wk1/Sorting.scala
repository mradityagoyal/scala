package com.coursera.stanford.algorithms.wk1

import scala.util.control.TailCalls.TailRec
import scala.annotation.tailrec

object Sorting {

  /**
   * @param in the sequence to be sorted
   * @param ordering - defines the ordering on A
   * @return The sorted sequence
   */
  def selection_sort[A](in: Seq[A])(implicit ordering: Ordering[A]): Seq[A] = {

    /** recursively sorts the Sequence using selection sort. */
    @tailrec def selection_sort_recursive[A](acc: Seq[A], in: Seq[A])(implicit ordering: Ordering[A]): Seq[A] = in match {
      case head +: tail => {
        //find min of the sequnce. 
        lazy val min = in.min
        //recursively call itself with acc appended with min and in filterd with min 
        selection_sort_recursive(acc :+ min, in diff Seq(min) )
      }
      case _ => acc
    }
    selection_sort_recursive(Seq.empty, in)
  }


  /**
   * @param in
   * @param ordering
   * @return  minimun element  and rest of Seq
   */
  def minAndRest[A](in: Seq[A])(implicit ordering: Ordering[A]): Option[(A, Seq[A])] = in match {
    case x +: xs => {
      lazy val min = in.min
      Some(min, in diff Seq(min))
    }
    case _ => None
  }

  /**
 * @param first sorted seq
 * @param second sorted seq
 * @param ord
 * @return the merged sorted sequence
 */
def mergeSortedSeq[A](first: Seq[A], second: Seq[A])(implicit ord: Ordering[A]): Seq[A] = {
    @tailrec def merge_recursively(acc: Seq[A], first: Seq[A], second: Seq[A]): Seq[A] = {
      if (first.isEmpty && second.isEmpty) Seq.empty
      else if (first.isEmpty) acc ++ second
      else if (second.isEmpty) acc ++ first
      else if (ord.lt(first.head, second.head)) {
        merge_recursively(acc :+ first.head, first.tail, second)
      } else {
        merge_recursively(acc :+ second.head, first, second.tail)
      }
    }
    merge_recursively(Seq.empty, first, second)
  }

  def merge_sort[A](input: Seq[A])(implicit ord: Ordering[A]): Seq[A] = {
    //if input is empty or has only one element.. return as is. 
    if (input.isEmpty || input.length == 1) input
    else {
      val half = input.length /2
      mergeSortedSeq(merge_sort(input take half),
                     merge_sort(input drop half))
    }
  }

}