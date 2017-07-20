package addy.datastructures.and.algorithms.wk1

import scala.io.StdIn

object ValidateBrackets extends App {
  val in: String = StdIn.readLine()
  val (res: Boolean, debt: Option[(Char, Int)]) = checkBrackets(in)
  if (res) println("Success")
  else {
    val err = debt.get
    println(err._2)
  }
  
  def calcDebt(input: String)(startIdx: Int): (Boolean, List[(Char, Int)]) = {
    val rng = startIdx to startIdx + input.length()
    evalUnmatchedBrackets(List(), (input zip rng).toList)
  }
  
  def checkBracketsSimple(input: String): (Boolean, Option[(Char, Int)]) = {
    val debt = calcDebt(input)(1)
    val (balanced: Boolean, unmatched: List[(Char, Int)]) = debt
    if (balanced == true) {
      if (unmatched.isEmpty) (true, None)
      else (false, Some(unmatched.reverse.head))
    } else {
      (false, Some(unmatched.head))
    }
  }
  
  /**
   * @param acc a list of (unmatched brackets , index)
   * @param in input string as a list of (char, index)
   * @return true, List of unmatched brackets if there is no error in closing bracket, else return false, lsit of unmatched brackets.
   */
  def evalUnmatchedBrackets(acc: List[(Char, Int)], in: List[(Char, Int)]): (Boolean, List[(Char, Int)]) = in match {
    case Nil => (true, acc) //only times it exists is when in.isempty or in.head is not closing pair of acc.head
    case (c, _) +: _ => c match {
      case '[' | '{' | '(' => evalUnmatchedBrackets(in.head +: acc, in.tail)
      case ')' | '}' | ']' => {
        if (acc.isEmpty) {
          evalUnmatchedBrackets(in.head +: acc, in.tail)
        } else {
          val prevChar = acc.head._1
          if (isClosingBrakcet(prevChar)) evalUnmatchedBrackets(in.head +: acc, in.tail)
          else if (isClosingPair(prevChar, c)) evalUnmatchedBrackets(acc.tail, in.tail)
          else (false, in.head +: acc)
        }
      }
      case _ => evalUnmatchedBrackets(acc, in.tail)
    }
  }
  
  def merge(debtA: (Boolean, List[(Char, Int)]), debtB: (Boolean, List[(Char, Int)])): (Boolean, List[(Char, Int)]) = {
    val (successA, unmatchedA): (Boolean, List[(Char, Int)]) = debtA
    val (successB, unmatchedB): (Boolean, List[(Char, Int)]) = debtB

    if (successA) {
      //first is successful.. check second. 
      if (successB) {
        evalUnmatchedBrackets(unmatchedA, unmatchedB.reverse)
      } else {
        debtB
      }
    } else {
      //first is not successful.. return it. 
      debtA
    }
  }

  def checkBrackets(input: String): (Boolean, Option[(Char, Int)]) = {
    val idxs = 1 to input.length() + 1
    val zippedWithIdx = (input zip idxs).toList
    lazy val debt = evalUnmatchedSplitAndMerge(zippedWithIdx)
    getMatchResult(debt)
  }
  
  def evalUnmatchedSplitAndMerge(withIdx: List[(Char, Int)]): (Boolean, List[(Char, Int)]) = {
    if (withIdx.length < 10) {
      evalUnmatchedBrackets(List(), withIdx)
    } else {
      val half = withIdx.length / 2
      val (x, y) = withIdx.splitAt(half)
      merge(evalUnmatchedSplitAndMerge(x), evalUnmatchedSplitAndMerge(y))
    }
  }
  
  def getMatchResult(debt: (Boolean, List[(Char, Int)])): (Boolean, Option[(Char, Int)]) = {
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