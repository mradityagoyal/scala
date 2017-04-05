package com.coursera.stanford.algorithms.wk1

object Test {
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet
  val ac = 10                                     //> ac  : Int = 10
  (1 << 4) * ac                                   //> res0: Int = 160
  
  ac << 4                                         //> res1: Int = 160
  
  Multiplication.recursive_multiply(1234, 5678)   //> res2: Int = 7006652
  val res = Multiplication.recursive_multiply_base10(1001, 20000)
                                                  //> res  : Double = 2.002E7
  println(f"result is : $res%10.0f")              //> result is :   20020000
  
}