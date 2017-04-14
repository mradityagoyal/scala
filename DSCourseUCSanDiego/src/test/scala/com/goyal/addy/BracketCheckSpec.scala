package com.goyal.addy

import org.scalatest.FlatSpec
import org.scalatest.Matchers

class BracketCheckSpec extends FlatSpec with Matchers{
  
  "()" should "match brackets correctly" in {
    val (res: Boolean, debt: Option[(Char, Int)]) = BracketCheck.checkBrackets("()")
    res should be (true)
  }
  
  "()(" should "match brackets correctly" in {
    val (res: Boolean, debt: Option[(Char, Int)]) = BracketCheck.checkBrackets("()(")
    res should be (false)
    debt should === (Some(('(',2)))
  }
  
}