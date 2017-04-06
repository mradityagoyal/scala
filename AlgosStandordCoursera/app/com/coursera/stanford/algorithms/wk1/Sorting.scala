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
      case head :: tail => {
        //find min of the sequnce. 
        lazy val min = in.min
        //recursively call itself with acc appended with min and in filterd with min 
        selection_sort_recursive(acc.:+(min), in.filterNot(_ == min))
      }
      case _ => acc
    }
    selection_sort_recursive(Seq.empty, in)
  }

  /**
 * @param in input vector
 * @param ordering for A
 * @return sorted vector
 */
def vector_selection_sort[A](in: Vector[A])(implicit ordering: Ordering[A]): Vector[A] = {

    def vector_selection_recursive[A](acc: Vector[A], in: Vector[A])(implicit ordering: Ordering[A]): Vector[A] = in match {
      case head +: tail => {
        minAndRest(in)((ordering)).map{case (min, rest) => vector_selection_recursive(acc:+ min, rest)}
                                .getOrElse(acc)
      }
      case _ => acc
    }
    vector_selection_recursive(Vector.empty, in)
  }

/**
 * @param in
 * @param ordering
 * @return  minimun element  and rest of vector 
 */
def minAndRest[A](in: Vector[A])(implicit ordering: Ordering[A]): Option[(A, Vector[A])] = in match {
    case x :+ xs => {
      lazy val min = in.min
      Some(min, in.diff(Vector(min)))
    }
    case _ => None
  }

def mergeSortedVectors(x: Vector[Int], y: Vector[Int]): Vector[Int] = {
  @tailrec def merge_recursively(acc: Vector[Int], x: Vector[Int], y: Vector[Int]): Vector[Int] = {
    if (x.isEmpty) acc ++ y
    else if (y.isEmpty) acc ++ x
    else if(x.head < y.head) {
      merge_recursively(acc :+ x.head, x.tail, y )
    }else {
      merge_recursively(acc :+ y.head, x, y.tail)
    }
  }
  merge_recursively(Vector.empty, x, y)
}

}