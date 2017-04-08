package com.coursera.stanford.algorithms.wk2.matrix

/**
 * @author aditygoy
 * This one tries to use type classes for matrix multiplication
 *
 *
 */

object MatrixMult {

  type Row = List[Double]
  type Matrix = List[Row]

  /**
   * @param a first Row
   * @param b second Row
   * @return dot product of two rows.
   */
  def dotProduct(a: Row, b: Row): Double = {
    (a zip b).map { case (x, y) => x * y }.sum
  }

  def transpose(x: Matrix): Matrix = {
    x.map(_.head) :: transpose(x.map(_.tail))
  }

}