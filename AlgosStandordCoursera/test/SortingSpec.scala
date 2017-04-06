import org.scalacheck.Properties
import org.scalacheck.Prop._
import com.coursera.stanford.algorithms.wk1.Sorting

object SortingSpec extends Properties("Sorting") {

  property("minAndRest") = forAll { (input: Seq[String]) =>
    val ordering = scala.math.Ordering.String
    Sorting.minAndRest(input)(ordering).map {
      case (min, rest) => {
        //min is correctly chosen. 
        min == input.min &&
          //size of rest is less than size of input
          rest.size < input.size &&
          //adding min and rest leads back to input and nothing else 
          (min +: rest diff (input)) == Vector.empty
      }
    }.getOrElse(input.isEmpty)
  }


  property("seq_selection_sort") = forAll { (input: Seq[Int]) =>
    val sorted = Sorting.selection_sort(input)
    if (sorted.isEmpty) {
      input.isEmpty
    } else {
      //sorted is same lenght as input. 
      sorted.length == input.length &&
        //sorted and input have exactly same elements. 
        sorted.diff(input) == Vector.empty &&
        isSorted(sorted)
    }
  }

  property("mergeSortedSeq") = forAll { (first: Seq[Int], second: Seq[Int]) =>
    //need to supply ordering as in case of two empty vectors.. it becomes ambiguous without explicit ordering
    val merged = Sorting.mergeSortedSeq(first.sorted, second.sorted)(math.Ordering.Int)
    if (merged.isEmpty) first.isEmpty && second.isEmpty
    else {
      //size of merged is total of size of inputs
      merged.size == first.size + second.size &&
        //and merged has nothing except inputs. 
        merged.diff(first ++ second) == Vector.empty &&
        isSorted(merged)
    }
  }

  property("merge_sort") = forAll { (input: Seq[Int]) =>
    val sorted = Sorting.merge_sort(input)
    if (sorted.isEmpty) {
      input.isEmpty
    } else {
      //sorted is same lenght as input. 
      sorted.length == input.length &&
        //sorted and input have exactly same elements. 
        sorted.diff(input) == Vector.empty &&
        isSorted(sorted)
    }
  }
  
  property("merge_sort_vectors") = forAll { (input: Vector[Int]) =>
    val sorted = Sorting.merge_sort(input)
    if (sorted.isEmpty) {
      input.isEmpty
    } else {
      //sorted is same lenght as input. 
      sorted.length == input.length &&
        //sorted and input have exactly same elements. 
        sorted.diff(input) == Vector.empty &&
        isSorted(sorted)
    }
  }

  def isSorted[A](input: Seq[A])(implicit ordering: Ordering[A]): Boolean = {
    lazy val sorted = input.sorted
    Range(0, input.length).forall(index => input(index) == sorted(index))
  }

}