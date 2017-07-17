package misc

/**
  * Created by agoyal on 7/13/17.
  * Tries to solve the problem as stated at https://www.hackerrank.com/challenges/the-power-sum/problem
  * Find the number of ways that a given integer, , can be expressed as the sum of the  power of unique, natural numbers.
  * *
  * Input Format
  * *
  * The first line contains an integer .
  * The second line contains an integer .
  * *
  * Constraints 1 <= X <= 1000
  * 2 <= N <=10
  * *
  * Output Format
  * *
  * Output a single integer, the answer to the problem explained above.
  *
  *
  */
object PowerSumSoln extends App {

  val N = 2
  (4 to 10).foreach(x => println(s"num of ways for $x is ${numberOfWays(x, N)}"))

  def flooredRoot(X: Int, N: Int): Double = scala.math.floor(scala.math.pow(X, 1.0 / N))

  def numberOfWays(X: Int, N: Int): Int = {
    def numWays(X: Int, N: Int, maxIn: Int): Int = {
      if (X < 1) 0
      else {
        val max: Int = scala.math.min(maxIn, flooredRoot(X, N)).toInt
        if (max == 1) {
          if (X == 1) 1 else 0
        } else {
          val counts = for (p <- max to 1 by -1) yield {
            val pn = scala.math.pow(p, N)
            if (pn == X) 1
            else numWays(X - pn.toInt, N, p-1)
          }
          counts.sum
        }
      }
    }
    numWays(X, N, Integer.MAX_VALUE)
  }


  //  def main(args: Array[String]) {
  //    println(numberOfWays(readInt(), readInt()))
  //  }
}

