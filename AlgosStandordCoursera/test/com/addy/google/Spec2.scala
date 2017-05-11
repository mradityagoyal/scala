package com.addy.google

import org.scalatest.FlatSpec
import org.scalatest.Matchers
import org.scalatest.prop.PropertyChecks
import scala.util.Random

class Spec2 extends FlatSpec with Matchers with PropertyChecks {

  "OnScreenKb" should " return coordinates correctly" in {
    OnScrKB.coords('A') should ===((0, 0))
    OnScrKB.coords('B') should ===((0, 1))
  }

  it should "return keystrokes correctly" in {
    val aToB = OnScrKB.path('A', 'B')
    aToB should ===("R*")
    OnScrKB.path('A', 'C') should ===("RR*")
    OnScrKB.path('A', 'E') should ===("D*")
    OnScrKB.path('A', 'F') should ===("RD*")
  }

  it should "return path correctly " in {
    val numtests = 100
    val lengths = (0 to numtests).map(_ => Random.nextInt(30))
    for (len <- lengths) {
      val cleaned = List.fill(len)(Random.nextInt(25)).map(OnScrKB.alphabets(_)).mkString
      val foldRightResult = OnScrKB.getKeyStrokes(cleaned, 'A')
      val devideAndConqRes = OnScrKB.devideAndConquer(cleaned)
      val foldResult = OnScrKB.parallelFold(cleaned)
      foldRightResult should ===(devideAndConqRes)
      devideAndConqRes should ===(foldResult)
    }

  }

}