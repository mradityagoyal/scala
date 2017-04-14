package com.goyal.addy

import scala.collection.immutable.Stack

object BracketsTestWS {

  val input = "this ( is ( a }} test"             //> input  : String = this ( is ( a }} test

  val zero = List.empty[Char]                     //> zero  : List[Char] = List()
  val bracketDebt: List[Char] = input.foldLeft(zero)((acc, c) => {
    if (c == '(') c +: acc
    else acc
  })                                              //> bracketDebt  : List[Char] = List((, ()

  val z = (None, Stack.empty[Char])               //> z  : (None.type, scala.collection.immutable.Stack[Char]) = (None,Stack())
  
  val res = CheckBracketsDevCon.checkBracket("  (( ))[] ")
                                                  //> res  : Boolean = true
}