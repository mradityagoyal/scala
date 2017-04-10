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
   * @param n
   * @return  nXn matrix of random ints.
   */
  def genNxNMatrix(n: Int, limit: Int = 100): MatrixMult.Matrix = {
    def generateRow(n: Int): MatrixMult.Row = {
      (0 to n - 1).map(_ => Random.nextInt(limit).toDouble).toList
    }
    for (row <- (0 to n - 1).map(_ => generateRow(n)).toList) yield row
  }

  case class RichMatrix(m: Matrix) {
    def T = transpose(m)
    def *(that: RichMatrix) = mXm(this.m, that.m)

    /**
     * @param m1
     * @param m2
     * @return dot product of the two input matrices.
     */
    def mXm(m1: Matrix, m2: Matrix): Matrix = {
      //dot product on each row from first, and each row from transpose of second.. to get each element)
      for (m1row <- m1) yield for (m2Col <- transpose(m2)) yield dotProductRows(m1row, m2Col)
    }

    /**
     * @param a first Row
     * @param b second Row
     * @return dot product of two rows.
     */
    def dotProductRows(a: Row, b: Row): Double = {
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

    /**
     * @param that
     * @return uses strassen multiplication algorithm.
     */
    def strassenMult(that: RichMatrix): Matrix = {
      //can only deal with square matrices. 
      //if less than 2 X 2 matrix.. then do simple dot product. 
      if (this.m.length <= 2) {
        mXm(this.m, that.m)
      } else {

      }
      //TODO: Remove. 
      m
    }

    /**
     * @param m
     * @return the topLeft, topRight, bottomLeft, bottomRight of the matrix
     */
    def splitIn4: (Matrix, Matrix, Matrix, Matrix) = {
      val n = m.length
      if (n < 2) throw new NoSuchElementException("Split a mx of size less than 4")
      m.map(row => row.splitAt(row.length / 2)).unzip match {
        case (leftCols, rightCols) => {
          val (tl, bl) = leftCols.splitAt(leftCols.length / 2)
          val (tr, br) = rightCols.splitAt(rightCols.length / 2)
          (tl, tr, bl, br)
        }
      }
    }
  }

  implicit def pimp(m: Matrix) = new RichMatrix(m)

}