package com.coursera.stanford.algorithms.wk1

import scala.util.Random


object MSortWS {
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet
  
  val input =  List.fill(10)(Random.nextInt).sorted
                                                  //> input  : List[Int] = List(-1868811573, -1367196552, -1348622797, -1058868131
                                                  //| , -915822077, 1033220680, 1060767851, 1212493431, 1791165561, 1827411724)
  MergeSort.countInversions(input.reverse)        //> res0: Int = 45
}