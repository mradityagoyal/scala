package com.coursera.stanford.algorithms.wk1

import org.scalatest.prop.PropertyChecks
import org.scalatest.Matchers
import org.scalatest.FlatSpec
import com.google.inject.matcher.Matchers.InPackage

class MergeSortSpec extends FlatSpec with PropertyChecks with Matchers {

  "MergeSortedLists method" should "merge two lists of Ints properly" in {
    forAll("first", "second") { (first: List[Int], second: List[Int]) =>
      val merged = MergeSort.mergeSortedLists(first.sorted, second.sorted)
      merged sameElements (first ::: second).sorted shouldBe true
    }
  }

  "MergeSort method" should "sort a list of Ints properly" in {
    forAll("input") { (input: List[String]) =>
      val mergeSorted = MergeSort.mergeSort(input)
      mergeSorted sameElements input.sorted shouldBe true
    }
  }
  it should "sort a list of Strings properly" in {
    forAll("input") { (input: List[Int]) =>
      val mergeSorted = MergeSort.mergeSort(input)
      mergeSorted sameElements input.sorted shouldBe true
    }
  }


}