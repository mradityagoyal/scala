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

  def transpose(mx: Matrix): Matrix = {
    if (mx.isEmpty || mx.head.isEmpty) Nil
    else mx.map(_.head) :: transpose(mx.map(_.tail))
  }

  def genNxNMatrix(n: Int): MatrixMult.Matrix = {
    def generateRow(n: Int): MatrixMult.Row = {
      (0 to n - 1).map(_ => Random.nextInt(100).toDouble).toList
    }
    
    
    for(row <- (0 to n - 1).map(_ => generateRow(n)).toList) yield row
  }

}