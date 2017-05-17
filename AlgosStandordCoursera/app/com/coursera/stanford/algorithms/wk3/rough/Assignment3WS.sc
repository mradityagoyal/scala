package com.coursera.stanford.algorithms.wk3.rough

object Assignment3WS {
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet
  
  def medianOf3(x: Int, y: Int, z: Int): Int = {
    List(x,y,z).sorted.tail.head
  }                                               //> medianOf3: (x: Int, y: Int, z: Int)Int
  
  medianOf3(10,2,31)                              //> res0: Int = 10
}