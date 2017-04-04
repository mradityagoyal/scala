package algorithms.stanford.coursera.wk1

object Multiplication extends App{
  
  override def main(args: Array[String]) = {
    val product = karatsubaMultiply("1234", "5678")
    println(s"Product is $product")
//    val (a , b, c, d) = ("12", "34", "56", "78")
  }
  
  
  /**
   * returns x * y . uses karatsuba method on string. 
   * 
 * @param x 
 * @param y
 * @return
 */
def karatsubaMultiply(x: String, y: String): Integer = {
  
  def split(input: String): (String, String) = {
    val length = input.length();
    (input.substring(0, length/2) , input.substring(length/2))
  }
                  
  
  val (a , b) = split(x)
  val (c,d) = split(y)
  println(s"a: $a , b: $b, c: $c , d: $d")
  def mul(first: String, second: String): Int = first.toInt * second.toInt
  def add(first: String, second: String): Int = first.toInt + second.toInt
  val ac = mul(a, c)
  println(s"ac = $ac")
  val bd = mul(b,d)
  println(s"bd = $bd")
  val aPlusB = add(a,b)
  println(s"aPlusB = $aPlusB")
  val cPlusD = add(c,d)
  println(s"cPlusD = $cPlusD")
  val star = aPlusB * cPlusD - ac - bd 
  
  star
  }
  
  
}