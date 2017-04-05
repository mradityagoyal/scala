package com.coursera.stanford.algorithms.wk1

import scala.util.control.TailCalls.TailRec
import scala.annotation.tailrec

object Sorting {

  /**
   * @param ordering - defines the ordering on A
   * @param in the sequence to be sorted
   * @return The sorted sequence
   */
  def selection_sort[A](ordering: Ordering[A])(in: Seq[A]): Seq[A] = {

    /** recursively sorts the Sequence using selection sort. */
    @tailrec def selection_sort_recursive[A](ordering: Ordering[A])(acc: Seq[A], in: Seq[A]): Seq[A] = in match {
      case head :: tail => {
        //find min of the sequnce. 
        lazy val min = in.min(ordering)
        //recursively call itself with acc appended with min and in filterd with min 
        selection_sort_recursive(ordering)(acc.:+(min), in.filterNot(_ == min))
      }
      case _ => acc
    }
    selection_sort_recursive(ordering)(Seq.empty, in)
  }

  def vector_selection_sort[A](ordering: Ordering[A])(in: Vector[A]): Vector[A] = {

    def vector_selection_recursive[A](ordering: Ordering[A])(acc: Vector[A], in: Vector[A]): Vector[A] = in match {
      case head +: tail => {
        lazy val maybeMinAndRest = minAndRest(ordering)(in)
        maybeMinAndRest match {
          case None => acc
          case Some((min, rest)) => vector_selection_recursive(ordering)(acc :+ min, rest)
        }
      }
      case _ => acc
    }
    vector_selection_recursive(ordering)(Vector.empty, in)
  }

  /**
 * @param ordering
 * @param in input vector 
 * @return minimun element  and rest of vector 
 */
def minAndRest[A](ordering: Ordering[A])(in: Vector[A]): Option[(A, Vector[A])] = in match {
    case x :+ xs => {
      lazy val min = in.min(ordering)
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