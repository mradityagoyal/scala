package com.goyal.addy.hackerrank.sorting

/**
  * Created by agoyal on 7/19/17.
  * solution for https://www.hackerrank.com/challenges/tutorial-intro
  *
  *
  */
object SortingIntro extends App{

  def findIndex(arr: Array[Int])(v: Int): Int = {
    /**It is guaranteed that v exists exactly once in the sorted array arr **/
    def helper(start: Int): Int = start match {
      case x if arr(x) == v => x
      case x if arr(x) < v => helper(x + 1)
      case _ => -1
    }
    helper(0)
  }

  override def main(args: Array[String]): Unit = {
    val v = scala.io.StdIn.readInt()
    val n = scala.io.StdIn.readInt()
    val arr = scala.io.StdIn.readLine().split(" ").map(_.toInt)
    val index = findIndex(arr)(v)
    println(index)
  }



}
