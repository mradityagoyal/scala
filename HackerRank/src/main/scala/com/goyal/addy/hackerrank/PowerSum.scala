package com.goyal.addy.hackerrank



/**
  * Created by addy on 7/13/17.
  */
object Solution {

  def numberOfWays(X: Int, N: Int): Int = {

    def powerSumHelper(sum: Int, maximum: Int): Int = sum match {
      case x if x < 1 => 0
      case _ => {
        val limit = scala.math.min(maximum, scala.math.floor(scala.math.pow(sum, 1.0 / N)).toInt)
        (limit to 1 by -1).map(x => {
          val y = scala.math.pow(x, N).toInt
          if (y == sum) 1 else powerSumHelper(sum - y, x - 1)
        }).sum
      }
    }

    powerSumHelper(X, Int.MaxValue)
  }



  def main(args: Array[String]) {
    println(numberOfWays(readInt(), readInt()))
  }
}
