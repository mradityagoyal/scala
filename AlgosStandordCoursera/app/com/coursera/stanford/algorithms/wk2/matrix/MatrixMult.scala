package com.coursera.stanford.algorithms.wk2.matrix

import scala.util.Random

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

  /**
   * @param m a matrix
   * @return the transpose of m
   */
  def transpose(m: Matrix): Matrix = {
    if (m.isEmpty || m.head.isEmpty) Nil
    else m.map(_.head) :: transpose(m.map(_.tail))
  }

  def mXm(m1: Matrix, m2: Matrix): Matrix = {
    for (m1row <- m1) yield for (m2Col <- transpose(m2)) yield dotProduct(m1row, m2Col)
  }

  /**
   * @param n
   * @return  nXn matrix of random ints.
   */
  def genNxNMatrix(n: Int, limit: Int = 100): MatrixMult.Matrix = {
    def generateRow(n: Int): MatrixMult.Row = {
      (0 to n - 1).map(_ => Random.nextInt(limit).toDouble).toList
    }
    for (row <- (0 to n - 1).map(_ => generateRow(n)).toList) yield row
  }

}