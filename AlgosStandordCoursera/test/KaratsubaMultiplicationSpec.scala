
import org.scalacheck.Properties
import org.scalacheck.Prop.forAll
import com.coursera.stanford.algorithms.wk1.KaratsubaMultiplication

object KaratsubaMultiplicationSpec extends Properties("Karatsuba"){
  
  

  property("calculateCoeff") = forAll { (x: Int) => 
    val (a,b) = KaratsubaMultiplication.calculateCoeff(10, 2)(x)
    a * KaratsubaMultiplication.pow(10, 2) + b == x
  }
  
  property("recursive_multiply") = forAll { (x: Int, y: Int) => 
    val rec_mul_result = KaratsubaMultiplication.recursive_multiply(x, y)
    rec_mul_result == x.toLong * y
  }
  
  property("karatsuba_multiply") = forAll { (x: Int, y: Int) => 
    val karatsuba_mul_result = KaratsubaMultiplication.karatsuba_multiply(x, y)
    karatsuba_mul_result == x.toLong * y
  }
  
}