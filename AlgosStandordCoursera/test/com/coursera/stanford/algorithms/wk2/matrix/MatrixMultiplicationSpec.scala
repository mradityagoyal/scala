package com.coursera.stanford.algorithms.wk2.matrix

import org.scalatest.FlatSpec
import org.scalatest.Matchers
import org.scalatest.prop.PropertyChecks
import scala.util.Random

class MatrixMultiplicationSpec extends FlatSpec with Matchers with PropertyChecks {

  "A Matrix " should "when transposed twice should be equal to the original matrix " in {
    (0 to 10).map { x =>
      val mx = MatrixMult.genNxNMatrix(x)
      val tmx = MatrixMult.transpose(mx)
      val ttmx = MatrixMult.transpose(tmx)
      assert(mx === ttmx)
    }
  }
}