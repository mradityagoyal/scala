package com.goyal.addy.datastructures.and.algorithms.wk1.rough

import scala.annotation.tailrec

object BracketDnQ {


  @deprecated
  def fold(brackets: List[(Char, Int)], char_idx: (Char, Int)): List[(Char, Int)] = char_idx match {
    case (char, _) =>
      if (char == '[' | char == '(' | char == '{') {
        (char_idx) +: brackets
      } else if (char == ']' | char == ')' | char == '}') {
        //if is closing bracket.. return brackets.tail
        //else.. return the error and stop folding. 
        brackets
        //TODO.. perform popping
      } else {
        //neither a open bracket nor close bracket. 
        brackets
      }
  }

  /**
   * @param charAndIdx the bracket and the index .
   * @param brackets - the list of brakcets already present.
   * @return List of brackets or the optional error index.
   */
  @deprecated
  def addChar(charAndIdx: (Char, Int))(brackets: List[(Char, Int)]): (List[(Char, Int)], Option[Int]) = charAndIdx match {
    case (char, _) => if (char == '[' | char == '(' | char == '{') {
      (charAndIdx +: brackets, None)
    } else if (char == ']' | char == ')' | char == '}') {
      //if is closing bracket.. return brackets.tail
      //else.. return the error and stop folding. 
      (charAndIdx +: brackets, None)
      //TODO.. perform popping
    } else {
      //neither a open bracket nor close bracket. 
      (brackets, None)
    }
  }

  @deprecated
  def stringFold(input: String): (String, Boolean) = {
    def calcDebt(acc: String, in: String): (String, Boolean) = {
      //only times it exists is when in.isempty or in.head is not closing pair of acc.head
      if (in.isEmpty()) (acc, true)
      else in.head match {
        case '[' | '{' | '(' => calcDebt(in.head +: acc, in.tail)
        case ')' | '}' | ']' => if (acc.isEmpty() || isClosingBrakcet(acc.head)) {
          calcDebt(in.head +: acc, in.tail)
        } else {
          if (isClosingPair(acc.head, in.head)) calcDebt(acc.tail, in.tail)
          else (in.head +: acc, false)
        }
        case _ => calcDebt(acc, in.tail)
      }
    }
    val debt = calcDebt("", input)
    debt
  }

  def reduce(input: String)(startIdx: Int): (Boolean, List[(Char, Int)]) = {
    @tailrec
    def calcDebt(acc: List[(Char, Int)], in: List[(Char, Int)]): (Boolean, List[(Char, Int)]) = in match {
      case Nil => (true, acc) //only times it exists is when in.isempty or in.head is not closing pair of acc.head
      case (c, _) +: _ => c match {
        case '[' | '{' | '(' => calcDebt(in.head +: acc, in.tail)
        case ')' | '}' | ']' => {
          if (acc.isEmpty) {
            calcDebt(in.head +: acc, in.tail)
          } else {
            val prevChar = acc.head._1
            if (isClosingBrakcet(prevChar)) calcDebt(in.head +: acc, in.tail)
            else if (isClosingPair(prevChar, c)) calcDebt(acc.tail, in.tail)
            else (false, in.head +: acc)
          }
        }
        case _ => calcDebt(acc, in.tail)
      }
    }
    val rng = startIdx to startIdx + input.length()
    val zipped = input zip rng
    val debt = calcDebt(List(), zipped.toList)
    debt
  }

  def checkBrackets(input: String): (Boolean, Option[(Char, Int)]) = {
    val debt = reduce(input)(0)
    val (balanced: Boolean, unmatched: List[(Char, Int)]) = debt
    if (balanced == true) {
      if (unmatched.isEmpty) (true, None)
      else (false, Some(unmatched.reverse.head))
    } else {
      (false, Some(unmatched.head))
    }
  }

  def isClosingBrakcet(c: Char): Boolean = if (c == ')' || c == '}' || c == ']') true else false

  def isClosingPair(l: Char, r: Char): Boolean = (l, r) match {
    case ('{', '}') => true
    case ('[', ']') => true
    case ('(', ')') => true
    case _ => false
  }

}