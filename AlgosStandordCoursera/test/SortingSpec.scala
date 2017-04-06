import org.scalacheck.Properties
import org.scalacheck.Prop._
import com.coursera.stanford.algorithms.wk1.Sorting

object SortingSpec extends Properties("Sorting") {

  property("minAndRest") = forAll { (input: Vector[String]) =>
    val ordering = scala.math.Ordering.String
    Sorting.minAndRest(input).map {
      case (min, rest) => {
        //min is correctly chosen. 
        min == input.min &&
          //size of rest is less than size of input
          rest.size < input.size &&
          //adding min and rest leads back to input and nothing else 
          (min +: rest diff (input)) == Vector.empty
      }
    }.getOrElse(true)
  }

  property("vector_selection_sort") = forAll { (input: Vector[Int]) =>
    val ord = scala.math.Ordering.Int
    val sorted = Sorting.vector_selection_sort(input)
    sorted match {
      //if sorted is empty.. input must be empty too. 
      case sorted if sorted.isEmpty => input.isEmpty
      case sorted => {
        //sorted is same lenght as input. 
        sorted.length == input.length &&
          //sorted and input have exactly same elements. 
          sorted.diff(input) == Vector.empty &&
          isSorted(sorted)
      }
    }
  }

  property("mergeSortedVectors") = forAll { (first: Vector[Int], second: Vector[Int]) =>
    val merged = Sorting.mergeSortedVectors(first.sorted, second.sorted)
    if (merged.isEmpty) first.isEmpty && second.isEmpty
    else {
      //size of merged is total of size of inputs
      merged.size == first.size + second.size &&
        //and merged has nothing except inputs. 
        merged.diff(first ++ second) == Vector.empty &&
        isSorted(merged)
    }
  }
  
  def isSorted[A](input: Seq[A])(implicit ordering: Ordering[A]): Boolean = {
    lazy val sorted = input.sorted
    Range(0,input.length).forall(index => input(index) == sorted(index))
  }

}