package com.coursera.stanford.algorithms.wk2.matrix

import com.coursera.stanford.algorithms.wk2.matrix.MatrixMult._

object MxMTest extends App {
  
  implicit def pimp(m: Matrix) = new RichMatrix(m)

  val mx: Matrix = genNxNMatrix(3)
  
  val m = List(
      List(1.0,2.0),
      List(3.0,4.0))
  
  val split = m.splitIn4
  

  println(split)



}