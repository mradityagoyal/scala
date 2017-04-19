package com.goyal.addy.datastructures.and.algorithms.wk2.rough

/**
 * @author agoyal
 *
 */
import scala.annotation.tailrec
import scala.io.StdIn

object MergeTables extends App{

  val nm = StdIn.readLine().split(" ").map(_.toInt)
  val n = nm(0)
  val m = nm(1)

  val counts: Array[Long] = StdIn.readLine().split(" ").map(_.toLong)

  val parents: Array[Int] = (0 until n).toArray
  var maxCount = counts.max

  for (i <- 0 until m) {
    val destAndSrc = StdIn.readLine().split(" ").map(_.toInt)
    val (dest, src) = (destAndSrc(0), destAndSrc(1))
    merge(dest, src)
  }

  /** Returns the id of the root of node at this id. */
  @tailrec
  def getRootIdx(i: Int): Int = {
    if (parents(i) == i) i
    else getRootIdx(parents(i))
  }

  /**merges the src into destination. */
  def merge(dest: Int, src: Int): Unit = {
    val destRoot = getRootIdx(dest)
    val srcRoot = getRootIdx(src)
    if (destRoot != srcRoot) {
      //copy rows from src to dest. 
      val srcCount: Long = counts(srcRoot)
      val destCount: Long = counts(destRoot)
      val combinedCount = srcCount + destCount
      //store max number of rows. 
      if (maxCount < combinedCount) maxCount = combinedCount
      counts(destRoot) = combinedCount
      //set parent of srcRoot = destRoot. (merge trees)
      parents(srcRoot) = destRoot
      println(maxCount)
    }

  }

}