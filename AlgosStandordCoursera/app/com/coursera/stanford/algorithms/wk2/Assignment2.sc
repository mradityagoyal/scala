package com.coursera.stanford.algorithms.wk2

import scala.io.Source

object Assignment2 {
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet
  
  val filename = "/ssd/scala_github/scala/AlgosStandordCoursera/resources/IntegerArray.txt"
                                                  //> filename  : String = /ssd/scala_github/scala/AlgosStandordCoursera/resources
                                                  //| /IntegerArray.txt
  
  val ints: List[Int] = Source.fromFile(filename).getLines().toList.map(_.toInt)
                                                  //> ints  : List[Int] = List(54044, 14108, 79294, 29649, 25260, 60660, 2995, 537
                                                  //| 77, 49689, 9083, 16122, 90436, 4615, 40660, 25675, 58943, 92904, 9900, 95588
                                                  //| , 46120, 29390, 91323, 85363, 45738, 80717, 57415, 7637, 8540, 6336, 45434, 
                                                  //| 65895, 61811, 8959, 9139, 31027, 87662, 2484, 65550, 23260, 15616, 3490, 495
                                                  //| 68, 5979, 44737, 52808, 72122, 37957, 34826, 21419, 73531, 94323, 52910, 844
                                                  //| 96, 71799, 50162, 1692, 1565, 59279, 56864, 20141, 13893, 63942, 6055, 33424
                                                  //| , 44771, 25678, 33639, 27793, 41268, 1857, 63388, 32976, 46195, 61291, 61740
                                                  //| , 61680, 45264, 76361, 49243, 73250, 67432, 29124, 15198, 30626, 18950, 1385
                                                  //| 7, 32569, 45179, 47696, 21283, 77169, 26357, 97885, 54741, 57246, 61929, 259
                                                  //| 97, 45859, 3353, 32204, 97451, 60550, 49516, 38558, 57674, 36443, 666, 32486
                                                  //| , 54910, 24666, 45119, 40779, 3441, 83779, 60645, 63003, 59616, 7364, 69058,
                                                  //|  41651, 12201, 13234, 19717, 11536, 70350, 15611, 74484, 96153, 4127, 60035,
                                                  //|  19418, 58613, 28590, 32
                                                  //| Output exceeds cutoff limit.
  print(s"head $ints.head")                       //> head List(54044, 14108, 79294, 29649, 25260, 60660, 2995, 53777, 49689, 9083
                                                  //| , 16122, 90436, 4615, 40660, 25675, 58943, 92904, 9900, 95588, 46120, 29390,
                                                  //|  91323, 85363, 45738, 80717, 57415, 7637, 8540, 6336, 45434, 65895, 61811, 8
                                                  //| 959, 9139, 31027, 87662, 2484, 65550, 23260, 15616, 3490, 49568, 5979, 44737
                                                  //| , 52808, 72122, 37957, 34826, 21419, 73531, 94323, 52910, 84496, 71799, 5016
                                                  //| 2, 1692, 1565, 59279, 56864, 20141, 13893, 63942, 6055, 33424, 44771, 25678,
                                                  //|  33639, 27793, 41268, 1857, 63388, 32976, 46195, 61291, 61740, 61680, 45264,
                                                  //|  76361, 49243, 73250, 67432, 29124, 15198, 30626, 18950, 13857, 32569, 45179
                                                  //| , 47696, 21283, 77169, 26357, 97885, 54741, 57246, 61929, 25997, 45859, 3353
                                                  //| , 32204, 97451, 60550, 49516, 38558, 57674, 36443, 666, 32486, 54910, 24666,
                                                  //|  45119, 40779, 3441, 83779, 60645, 63003, 59616, 7364, 69058, 41651, 12201, 
                                                  //| 13234, 19717, 11536, 70350, 15611, 74484, 96153, 4127, 60035, 19418, 58613, 
                                                  //| 28590, 32917, 97780, 176
                                                  //| Output exceeds cutoff limit.
}