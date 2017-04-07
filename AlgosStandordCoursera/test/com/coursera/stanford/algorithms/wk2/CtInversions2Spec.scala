package com.coursera.stanford.algorithms.wk2

import org.scalatest.prop.PropertyChecks
import org.scalatest.Matchers
import org.scalatest.FlatSpec

class CtInversions2Spec extends FlatSpec with PropertyChecks with Matchers {

  "MergeAndCountInversions method" should "count inversions of 1 2 2 4 correctly" in {
    val (first, second) = (List(1, 2), List(2, 4))
    val (inversions, sorted) = CtInversion2.mergeAndCountInversions(0,List(), first, second)
    inversions shouldBe 0
    sorted sameElements (first ::: second).sorted shouldBe true
  }
  
  it should "count iversions of  1 5 2 9" in {
    val (first, second) = (List(1, 5), List(2, 9))
    val (inversions, sorted) = CtInversion2.mergeAndCountInversions(0 ,List(), first, second)
    inversions shouldBe 1
    sorted sameElements (first ::: second).sorted shouldBe true
  }
  it should "count inversions pf 1 5 8 9 4 9 10 correctly " in {
    val (first, second) = (List(1, 5, 8, 9), List(4, 9, 10))
    val (inversions, sorted) = CtInversion2.mergeAndCountInversions(0 ,List(), first, second)
    inversions shouldBe 3
    sorted sameElements (first ::: second).sorted shouldBe true
  }

  "CountInversions method" should "count inversions = 0 for all sorted lists" in {
    forAll("input") { (input: List[Int]) =>
      val sortedIn = input.sorted
      CtInversion2.countInversions(sortedIn) shouldBe 0
    }
  }
  it should "count inversions correctly " in {
    CtInversion2.countInversions(List(1, 5, 2, 9)) shouldBe 1
    CtInversion2.countInversions(List(1, 5, 2, 9, 10)) shouldBe 1
     CountInversions.countInversions(List(1, 5, 8, 9, 4, 9, 10)) shouldBe 3
  }

  it should "return inversions = (n * (n-1)) / 2 for a reverse sorted List" in {
    forAll("input") { (input: List[Int]) =>
      whenever(!input.isEmpty) {
        val reverseSortedDistinct = input.distinct.sorted.reverse
        CtInversion2.countInversions(reverseSortedDistinct) should be(reverseSortedDistinct.length * (reverseSortedDistinct.length - 1) / 2)
      }
    }
  }
}