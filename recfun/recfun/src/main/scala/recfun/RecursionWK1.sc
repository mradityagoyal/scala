package recfun

object RecursionWK1 {
  println("Welcome to the Scala worksheet")

  def pascal(c: Int, r: Int): Int = {
    if (c == 0 || c == r) 1
    else pascal(c - 1, r - 1) + pascal(c, r - 1)
  }

  pascal(0, 1)

  def main(x: Double) {
    println("Pascal's Triangle")
    for (row <- 0 to 10) {
      for (col <- 0 to row)
        print(pascal(col, row) + " ")
      println()
    }
  }

  def balance(chars: List[Char]): Boolean = {
    def process(x: Int, chars: List[Char]): Boolean = {
      if (x < 0) false
      if (chars.isEmpty) {
        if (x == 0) true else false
      }
      if (chars.head == '(') process(x + 1, chars.tail)
      if (chars.head == ')') process(x - 1, chars.tail)
      process(x, chars.tail)
    }

    process(0, chars)
  }

  balance"(this)()".toList)
}