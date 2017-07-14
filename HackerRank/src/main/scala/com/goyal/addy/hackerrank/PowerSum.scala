package com.goyal.addy.hackerrank

import scala.math._

/**
  * Created by addy on 7/13/17.
  */
object PowerSum {

  def numberOfWays(X: Int, N: Int): Int = {
    def numWays(sum: Int, maximum: Int): Int = sum match {
      case x if x < 1 => 0
      case _ => {
        val limit = min(maximum, floor(pow(sum, 1.0 / N)).toInt)
        (limit to 1 by -1).map(x => {
          val y = pow(x, N).toInt
          if (y == sum) 1 else numWays(sum - y, x - 1)
        }).sum
      }
    }
    numWays(X, Integer.MAX_VALUE)
  }

  def main(args: Array[String]) {
    println(numberOfWays(readInt(), readInt()))
  }
}
