package com.addy.goyal

import org.scalatest.FlatSpec
import org.scalatest.Matchers
import org.scalatest.prop.PropertyChecks
import com.addy.google.OnScrKB

class OnScrKbSpec extends FlatSpec with Matchers with PropertyChecks{
  
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
  
}