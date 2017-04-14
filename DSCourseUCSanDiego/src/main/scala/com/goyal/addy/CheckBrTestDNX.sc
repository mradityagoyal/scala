package com.goyal.addy

object CheckBrTestDNX {
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet
  val input = "()(())}"                           //> input  : String = ()(())}
  val debt = BracketCheckDevideAndConquer.checkBrackets(input)
                                                  //> debt  : (Boolean, Option[(Char, Int)]) = (false,Some((},6)))
  println(s"success: ${debt._1} errorChar : ${debt._2.getOrElse(('*,-1))}")
                                                  //> success: false errorChar : (},6)
}