package com.goyal.addy.rough

object CheckBracketsDevCon {

  def checkBrack(input: String): Boolean = ???

  case class Result(isError: Boolean = false, errorIdx: Int = -1, debt: List[Char] = List.empty[Char]) {
    def addDebt(c: Char): Result = Result(isError, errorIdx, c +: debt)
  }

  def checkBracket(input: String): Boolean = {
    val z = Result()
    val res: Result = input.foldLeft(z)((acc, c) => {
      if (isOpenBracket(c)) acc.addDebt(c)
      else if (isCloseBracket(c)) {
        if (acc.debt.isEmpty || isCloseBracket(acc.debt.head)) acc.addDebt(c)
        else if (isPair(acc.debt.head, c)) Result(acc.isError, acc.errorIdx, acc.debt.tail)
        else Result(true, 123, acc.debt)
      } else acc
    })
    !res.isError
  }

  def isOpenBracket(c: Char): Boolean = if (c == '(' || c == '[' || c == '{') true else false
  def isCloseBracket(c: Char): Boolean = if (c == ')' || c == ']' || c == '}') true else false
  def isPair(l: Char, r: Char) = (l, r) match {
    case ('(', ')') => true
    case ('{', '}') => true
    case ('[', ']') => true
    case _ => false
  }

}