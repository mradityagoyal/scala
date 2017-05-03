package com.coursera.stanford.algorithms.wk3.rough

import org.scalatest.prop.PropertyChecks
import org.scalatest.Matchers
import org.scalatest.FlatSpec
import org.scalatest.Assertions._
import scala.util.Random

class QuickSortSpec extends FlatSpec with Matchers with PropertyChecks {

  "Quicksort class" should "sort correctly" in {
    forAll("input") { (input: Array[Int]) =>
      val distinct = input.distinct
      val sorted = distinct.sorted
      val (qsorted, count) = QuickSort.sort(distinct)
      qsorted should ===(sorted)
    }
  }

}