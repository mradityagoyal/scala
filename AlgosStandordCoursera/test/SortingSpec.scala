import org.scalacheck.Properties
import org.scalacheck.Prop._
import com.coursera.stanford.algorithms.wk1.Sorting


object SortingSpec extends Properties("Sorting"){
  
  property("minAndRest") = forAll{(input: Vector[Int]) =>
    val ord = scala.math.Ordering.Int
    val maybeMinandRest = Sorting.minAndRest(ord)(input)
    maybeMinandRest match {
      case None => true
      case Some((min,rest)) => {
        //min is correctly chosen. 
        min == input.min(ord) &&
        //size of rest is less than size of input
        rest.size < input.size &&
        //adding min and rest leads back to input and nothing else 
        (min +: rest diff(input)) == Vector.empty
      }
    }
  }
  
  property("vector_selection_sort") = forAll{(input: Vector[Int]) =>
    val ord = scala.math.Ordering.Int
    val sorted = Sorting.vector_selection_sort(ord)(input)
    sorted match {
      //if sorted is empty.. input must be empty too. 
      case Vector() => input.isEmpty
      case sorted => {
        //sorted is same lenght as input. 
        sorted.length == input.length &&
        //sorted and input have exactly same elements. 
        sorted.diff(input) == Vector.empty
      }
    }
  }
  
  property("mergeSortedVectors") = forAll {(first: Vector[Int], second: Vector[Int]) =>
    val merged = Sorting.mergeSortedVectors(first, second)
    if (merged.isEmpty) first.isEmpty && second.isEmpty
    else {
      //size of merged is total of size of inputs
      merged.size == first.size + second.size &&
      //and merged has nothing except inputs. 
      merged.diff(first ++ second) == Vector.empty
    }
  }
  
}