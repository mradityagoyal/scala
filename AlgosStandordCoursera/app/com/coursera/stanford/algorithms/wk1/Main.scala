package com.coursera.stanford.algorithms.wk1

object Main extends App{
  
  override def main(args: Array[String]) = {
    val res = Multiplication.recursive_multiply(1234,5678)
    println(f"$res%10.0f")  
  }
  
}