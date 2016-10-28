package reductions

import scala.annotation._
import org.scalameter._
import common._

object ParallelParenthesesBalancingRunner {

  @volatile var seqResult = false

  @volatile var parResult = false

  val standardConfig = config(
    Key.exec.minWarmupRuns -> 40,
    Key.exec.maxWarmupRuns -> 80,
    Key.exec.benchRuns -> 120,
    Key.verbose -> true) withWarmer (new Warmer.Default)

  def main(args: Array[String]): Unit = {
    val length = 100000000
    val chars = new Array[Char](length)
    val threshold = 10000
    val seqtime = standardConfig measure {
      seqResult = ParallelParenthesesBalancing.balance(chars)
    }
    println(s"sequential result = $seqResult")
    println(s"sequential balancing time: $seqtime ms")

    val fjtime = standardConfig measure {
      parResult = ParallelParenthesesBalancing.parBalance(chars, threshold)
    }
    println(s"parallel result = $parResult")
    println(s"parallel balancing time: $fjtime ms")
    println(s"speedup: ${seqtime / fjtime}")
  }
}

object ParallelParenthesesBalancing {

  /**
   * Returns `true` iff the parentheses in the input `chars` are balanced.
   */
  def balance(chars: Array[Char]): Boolean = {
    def reduce(acc: Int, start: Int, chars: Array[Char]): Boolean = {
      if (acc < 0) false
      else if (start >= chars.length) {
        if (acc == 0) true else false
      } else if (chars(start) == '(') reduce(acc + 1, start + 1, chars)
      else if (chars(start) == ')') reduce(acc - 1, start + 1, chars)
      else reduce(acc, start + 1, chars)
    }
    reduce(0, 0, chars)
  }

  /**
   * Returns `true` iff the parentheses in the input `chars` are balanced.
   */
  def parBalance(chars: Array[Char], threshold: Int): Boolean = {

    def traverse(idx: Int, until: Int, min: Int, total: Int): (Int, Int) = {
      if (idx >= until) {
        (min, total)
      } else if (chars(idx) == '(') {
        traverse(idx + 1, until, min, total + 1)
      } else if (chars(idx) == ')') {
        if (total - 1 < min) {
          traverse(idx + 1, until, total - 1, total - 1)
        } else {
          traverse(idx + 1, until, min, total - 1)
        }
      } else traverse(idx + 1, until, min, total)
    }

    def reduce(from: Int, until: Int): (Int, Int) = {
      if (until - from < threshold) {
        traverse(from, until, 0, 0)
      } else {
        val mid = (from + until) / 2
        val (pair1, pair2) = parallel(traverse(from, mid, 0, 0), traverse(mid, until, 0, 0))
        //(min1, total1) + (min2, total2) = (total1 + min2 , total1 + total2
        (pair1._2 + pair2._1, pair1._2 + pair2._2)
      }

    }

    reduce(0, chars.length) == (0, 0)
  }

  // For those who want more:
  // Prove that your reduction operator is associative!

}
