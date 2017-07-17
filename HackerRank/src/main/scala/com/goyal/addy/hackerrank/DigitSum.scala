package com.goyal.addy.hackerrank

import scala.annotation.tailrec

/**
  * Created by agoyal on 7/17/17.
  *
  * Solution for the question at https://www.hackerrank.com/challenges/recursive-digit-sum?utm_campaign=challenge-recommendation&utm_medium=email&utm_source=24-hour-campaign
  *
  */



object DigitSumSolution {

  def main(args: Array[String]) {
    val input: Array[BigInt] = scala.io.StdIn.readLine().split(" ").map(BigInt(_))
    if(input.length != 2){
      println("Invalid input")
      System.exit(1)
    }
    val N = input.head
    val K = input.last

    println(digitSum(N, K))
  }


  def digitSum(N: BigInt, K: BigInt): Int = {
    val y = sumOfDigits(N) * K
    recDigitSum(y)
  }

  def sumOfDigits(x: BigInt): BigInt = {
    x.toString.split("").map(_.toInt).sum
  }

  @tailrec
  def recDigitSum(x: BigInt): Int = x match {
    case x if x <=10 => x.toInt
    case _ => recDigitSum(sumOfDigits(x))
  }


}
