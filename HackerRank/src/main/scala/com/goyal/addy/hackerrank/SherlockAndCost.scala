package com.goyal.addy.hackerrank

/**
  * Created by agoyal on 7/17/17.
  * https://www.hackerrank.com/challenges/sherlock-and-cost/problem
  *
  */
object SherlockAndCost{




  def processTestLine(line: String): Int ={
    val arrB: Array[Int] = line.split(" ").map(_.toInt)

    val rangesOfA: Array[Seq[Int]] = arrB.map(bi => 1 to bi)
    val allCombinations: Seq[List[Int]] = rangesOfA.foldLeft(Seq(List[Int]()))((lol, l ) => {
      for (x: Int <- l; combs: List[Int] <- lol)yield {
        combs :+ x
      }
    } )

    def calculatS(l : List[Int]): Int = (l.tail zip l).map(bminusa).sum

    def bminusa(pair: (Int, Int)): Int = pair match {
      case (b,a) => scala.math.abs(b-a)
    }

    val answer = allCombinations.map(calculatS).max
    answer
  }

  def main(args: Array[String]) {
    val T = scala.io.StdIn.readInt()
    for (t <- 0 until T){
      val N = scala.io.StdIn.readInt()
      val B = scala.io.StdIn.readLine()
      println(processTestLine(B))
    }
  }


}
