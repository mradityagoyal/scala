package com.coursera.stanford.algorithms.wk1

object Main extends App{
  
  override def main(args: Array[String]) = {
//    val res = Multiplication.recursive_multiply_base10(1234,5678)
    val res = KaratsubaMultiplication.recursive_multiply(1234, 5678)
    println(s"$res")  
  }
  
}