package com.addy.google

import org.scalatest.FlatSpec
import org.scalatest.Matchers
import org.scalatest.prop.PropertyChecks

class OnScrKbSpec extends FlatSpec with Matchers with PropertyChecks {
  
  "OnScreenKb" should " return coordinates correctly" in {
    OnScrKB.coords('A') should === ((0,0))
    OnScrKB.coords('B') should === ((0,1))
  }
  
  it should "return keystrokes correctly" in {
    val aToB = OnScrKB.path('A', 'B')
    aToB should ===("R")
    OnScrKB.path('A', 'C') should ===("RR")
    OnScrKB.path('A', 'E') should ===("D")
    OnScrKB.path('A', 'F') should ===("RD")
  }
  
  it should "return path correctly " in {
    forAll("input"){(input: String) =>
      val cleaned = input.toUpperCase().filter(OnScrKB.alphabets.contains(_))
      val foldRightResult = OnScrKB.getKeyStrokes(cleaned)
      val devideAndConqRes = OnScrKB.devideAndConquer(cleaned)
      val foldResult = OnScrKB.parallelFold(cleaned)
      
      foldRightResult should ===(devideAndConqRes)
      devideAndConqRes should ===(foldResult)
      
    }
  }
  
}