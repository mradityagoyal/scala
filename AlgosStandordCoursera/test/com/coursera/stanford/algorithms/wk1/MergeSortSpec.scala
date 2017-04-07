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

  "MergeAndCountInversions method" should "count inversions of 1 2 2 4 correctly" in {
    val (first, second) = (List(1, 2), List(2, 4))
    val (inversions, sorted) = MergeSort.mergeAndCountInversions(first, second)
    inversions shouldBe 0
    sorted sameElements (first ::: second).sorted shouldBe true
  }
  it should "count iversions of  1 5 2 9" in {
    val (first, second) = (List(1, 5), List(2, 9))
    val (inversions, sorted) = MergeSort.mergeAndCountInversions(first, second)
    inversions shouldBe 1
    sorted sameElements (first ::: second).sorted shouldBe true
  }
  it should "count inversions pf 1 5 8 9 4 9 10 correctly " in {
    val (first, second) = (List(1, 5, 8, 9), List(4, 9, 10))
    val (inversions, sorted) = MergeSort.mergeAndCountInversions(first, second)
    inversions shouldBe 3
    sorted sameElements (first ::: second).sorted shouldBe true
  }

  "CountInversions method" should "count inversions = 0 for all sorted lists" in {
    forAll("input") { (input: List[Int]) =>
      val sortedIn = input.sorted
      MergeSort.countInversions(sortedIn) shouldBe 0
    }
  }
  it should "count inversions correctly " in {
    MergeSort.countInversions(List(1,5,2,9)) shouldBe 1
    MergeSort.countInversions(List(1, 5, 8, 9, 4, 9, 10)) shouldBe 3
  }
  
  it should "return inversions = (n * (n-1)) / 2 for a reverse sorted List" in {
    forAll("input"){(input: List[Int]) =>
      whenever (!input.isEmpty) {
        val reverseSorted = input.sorted.reverse
        MergeSort.countInversions(reverseSorted) should be (input.length * (input.length -1) / 2)
      }
    }
  }

}