import org.scalatest.FlatSpec
import org.scalatest.Matchers
import scala.util.Random
import org.scalatest.FunSuite
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import scala.util.Random


@RunWith(classOf[JUnitRunner])
class MergeSortUnitTest extends FunSuite{
  
  test("merge sort"){
    lazy val randoms: Vector[Int] = Vector.fill[Int](100000)(Random.nextInt)
    println("Started")
    val merge_sorted = com.coursera.stanford.algorithms.wk1.Sorting.merge_sort(randoms)
   // val simple_sorted = randoms.sorted
    //assert(SortingSpec.isSorted(merge_sorted))
    println("finished")
  }
  
}