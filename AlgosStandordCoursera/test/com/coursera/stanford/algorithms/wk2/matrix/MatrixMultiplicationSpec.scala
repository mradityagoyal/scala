package com.coursera.stanford.algorithms.wk2.matrix

import org.scalatest.FlatSpec
import org.scalatest.Matchers
import org.scalatest.prop.PropertyChecks
import scala.util.Random

class MatrixMultiplicationSpec extends FlatSpec with Matchers with PropertyChecks {

  "A Matrix " should "when transposed twice should be equal to the original matrix " in {
    forAll("n") { (n: Int) =>
      val mx = genNxNMatrix(n)
      val tmx = MatrixMult.transpose(mx)
      val ttmx = MatrixMult.transpose(tmx)
      mx shouldEqual(ttmx)

    }

  }

  def genNxNMatrix(n: Int): MatrixMult.Matrix = {
    def generateRow(n: Int): MatrixMult.Row = {
      (0 to n).map(_ => Random.nextInt().toDouble).toList
    }
    (0 to n).map(_ => generateRow(n)).toList
  }

}