package com.goyal.addy

object BracketCheckTest {
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet
  
  val input = "()[(()"                            //> input  : String = ()[(()
  //val debt = BracketDnQ.stringFold2(input)(0)
  //println(s"char: ${debt._1.head._1} \t idx: ${debt._1.head._2} \tbalanced: ${debt._2}")
  
  val v1 = BracketDnQ.checkBrackets("(()[")       //> v1  : (Boolean, Option[(Char, Int)]) = (false,Some(((,0)))
}