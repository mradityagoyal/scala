package addy.datastructures.and.algorithms.wk3

/**
  * Created by agoyal on 7/20/17.
  */
class BinaryMaxHeapSpec extends UnitSpec{

  "A BinaryMaxHeap" should "satisfy heap property  extractMax " in {
    val testArr = Array(1, 4, 9, 2, 11, 5, 8, 15)
    val bmh = BinaryMaxHeap.emptyHeap(testArr.length)
    testArr.foreach(bmh.insert)
    val extracted = (0 until testArr.length).map(x => bmh.extractMax)
    assert(extracted sameElements testArr.sorted.reverse , "Should return sorted list. ")
  }

  it should "satisfy remove properly " in {
    val testArr = Array(1, 4, 9, 2, 11, 5, 8)
    val bmh = BinaryMaxHeap.emptyHeap(testArr.length)
    testArr.foreach(bmh.insert)

    assert(bmh.size == testArr.size, "size should be equal")
    bmh.remove(0)
    assert(bmh.size == testArr.size -1, "size should reduce after removing an element.")
    assert(bmh.getMax == testArr.sorted.reverse.tail.head, "should return second largest number")

    val extracted = (0 until bmh.size).map(x => bmh.extractMax)
    assert(extracted sameElements testArr.sorted.reverse.tail , "Should return sorted list. ")

  }

  "BuildHeap method of BinaryMaxHeap" should "Return a binaryMaxTree correctly " in {
    val testArr = Array(1, 4, 9, 2, 11, 5, 8)
    val bmh = BinaryMaxHeap.buildHeap(testArr)
    assert(bmh.size == testArr.size, "size should be equal")

    bmh.remove(0)
    assert(bmh.size == testArr.size -1, "size should reduce after removing an element.")
    assert(bmh.getMax == testArr.sorted.reverse.tail.head, "should return second largest number")

    val extracted = (0 until bmh.size).map(x => bmh.extractMax)
    assert(extracted sameElements testArr.sorted.reverse.tail , "Should return sorted list. ")

  }

  "BinaryMaxHeap" should "sort correctly " in {
    val test = Array(1, -4, 11, 99, 100, 43, 187, 76)
    val heapSorted = BinaryMaxHeap.heapSort(test)
    val sorted = test.sorted
    assert(heapSorted sameElements sorted, "should sort correctly. ")
  }

}
