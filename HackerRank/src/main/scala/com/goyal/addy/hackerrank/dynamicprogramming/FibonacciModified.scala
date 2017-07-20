package com.goyal.addy.hackerrank.dynamicprogramming

/**
  * Created by agoyal on 7/19/17.
  * Solution for https://www.hackerrank.com/challenges/fibonacci-modified
  *
  *
  */
object FibonacciModified extends App{

  def modFib(t1: BigInt, t2: BigInt)( n: Int ): BigInt = n match {
    case 1 => t1
    case 2 => t2
    case n => modFib(t1, t2)(n-2) + modFib(t1,t2)(n-1).pow(2)
  }

  override def main(args: Array[String]): Unit = {
    val Array(t1, t2, n) = scala.io.StdIn.readLine().split(" ").map(_.toInt)
    val result = modFib(t1, t2)(n)
    println(result)
  }


}
