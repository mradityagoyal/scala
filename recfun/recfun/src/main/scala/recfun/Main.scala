package recfun

object Main {
  def main(args: Array[String]) {
    println("Pascal's Triangle")
    for (row <- 0 to 10) {
      for (col <- 0 to row)
        print(pascal(col, row) + " ")
      println()
    }
  }

  /**
   * Exercise 1
   */
  def pascal(c: Int, r: Int): Int = {
    if (c == 0 || c == r) 1
    else pascal(c - 1, r - 1) + pascal(c, r - 1)
  }

  /**
   * Exercise 2
   */
  def balance(chars: List[Char]): Boolean = {
    def process(x: Int, chars: List[Char]): Boolean = {
      //if -ve then there was a unmatched )
      if (x < 0) return false
      if (chars.isEmpty) {
        if (x == 0) return true else return false
      }
      if (chars.head == '(') return process(x + 1, chars.tail)
      if (chars.head == ')') return process(x - 1, chars.tail)
      //head isn't ( or ) .. so process rest of the list. 
      process(x, chars.tail)
    }

    process(0, chars)
  }

  /**
   * Exercise 3
   */
  def countChange(money: Int, coins: List[Int]): Int = ???
}
