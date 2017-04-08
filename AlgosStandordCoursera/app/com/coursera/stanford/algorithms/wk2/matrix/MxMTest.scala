package com.coursera.stanford.algorithms.wk2.matrix

import com.coursera.stanford.algorithms.wk2.matrix.MatrixMult._

object MxMTest extends App{
  
  val mx: Matrix = genNxNMatrix(3)
  
  println(mx)
  
  val tmx = transpose(mx)
  println(tmx)
  val ttmx = transpose(tmx)
  val eq = ttmx == mx
  print(s"Equal $eq")
  
  
}