package com.goyal.addy.hackerrank

/**
  * Created by addy on 7/17/17.
  */
object SherlockCost2 {

  def processTestLine(line: String): Int ={
    val arrB: Array[Int] = line.split(" ").map(_.toInt)

    //map the B array to tuples Array(bi) => Array((1, Bi))
    val rangesOfA: Array[(Int, Int)] = arrB.map((1 , _))


    val allCombinations: Seq[List[Int]] = rangesOfA.foldLeft(Seq(List[Int]()))((acc, t ) => {
      acc.flatMap(l => Seq((l:+t._1) , (l:+t._2)))
    } )

    def calculatS(l : List[Int]): Int = (l.tail zip l).map(pair => pair match {
      case (b, a) => scala.math.abs(b-a)
    }).sum

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
