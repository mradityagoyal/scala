package com.coursera.stanford.algorithms.wk2.matrix

import org.scalatest.FlatSpec
import org.scalatest.Matchers
import org.scalatest.prop.PropertyChecks
import scala.util.Random
import com.coursera.stanford.algorithms.wk2.matrix.MatrixMult.Matrix
import com.coursera.stanford.algorithms.wk2.matrix.MatrixMult.RichMatrix
import com.coursera.stanford.algorithms.wk2.matrix.MatrixMult.Matrix

class MatrixMultiplicationSpec extends FlatSpec with Matchers with PropertyChecks {

  implicit def pimp(m: Matrix): RichMatrix = new RichMatrix(m)

  "A Matrix " should "when transposed twice should be equal to the original matrix " in {
    (0 to 10).map { x =>
      val m: Matrix = MatrixMult.genNxNMatrix(x)
      val ttm = m.transpose.transpose
      assert(m === ttm)
    }
  }

  "matrix mult" should "work properly" in {
    val ones = List.fill(2)(1.0)
    val m1 = List(ones, ones)
    val m2 = List(ones, ones)

    val m3 = List(List.fill(2)(2.0), List.fill(2)(2.0))

    val multiplied = m1 * m2
    multiplied should ===(m3)
  }
  "A RichMatrix " should "when transposed twice should be equal to the original matrix " in {
    (0 to 10).map { x =>
      val mx: Matrix = MatrixMult.genNxNMatrix(x)
      assert(mx === mx.T.T)
    }
  }
  it should "transpose a matrix correctly" in {
    val M: Matrix = List(
      List(1.0, 2.0, 3.0),
      List(4.0, 5.0, 6.0))
    val MT: Matrix = List(
      List(1.0, 4.0),
      List(2.0, 5.0),
      List(3.0, 6.0))
    assert(M.T === MT)
  }

  it should "dot product two matrices correctly" in {
    val A = List(List(2.0, 0.0),
      List(3.0, -1.0),
      List(0.0, 1.0),
      List(1.0, 1.0))
    val B = List(List(1.0, 0.0, 2.0),
      List(4.0, -1.0, 0.0))
    val C = List(List(2.0, 0.0, 4.0),
      List(-1.0, 1.0, 6.0),
      List(4.0, -1.0, 0.0),
      List(5.0, -1.0, 2.0))
    assert(A * B === C)
  }

  "Splitin4 method" should "split correctly" in {
    val m = List(
      List(1.0, 2.0),
      List(3.0, 4.0))

    val expected = (List(List(1.0)),List(List(2.0)),List(List(3.0)),List(List(4.0)))
    assert(m.splitIn4 === expected)
    
     val A = List(List(2.0, 0.0),
      List(3.0, -1.0),
      List(0.0, 1.0),
      List(1.0, 1.0))
      
    val exp2 = List(
        List(2))
  }

}