package reductions

import org.scalameter._
import common._

object LineOfSightRunner {

  val standardConfig = config(
    Key.exec.minWarmupRuns -> 40,
    Key.exec.maxWarmupRuns -> 80,
    Key.exec.benchRuns -> 100,
    Key.verbose -> true) withWarmer (new Warmer.Default)

  def main(args: Array[String]) {
    val length = 10000000
    val input = (0 until length).map(_ % 100 * 1.0f).toArray
    val output = new Array[Float](length + 1)
    val seqtime = standardConfig measure {
      LineOfSight.lineOfSight(input, output)
    }
    println(s"sequential time: $seqtime ms")

    val partime = standardConfig measure {
      LineOfSight.parLineOfSight(input, output, 10000)
    }
    println(s"parallel time: $partime ms")
    println(s"speedup: ${seqtime / partime}")
  }
}

object LineOfSight {

  def max(a: Float, b: Float): Float = if (a > b) a else b

  def lineOfSight(input: Array[Float], output: Array[Float]): Unit = {
    var i = 1;
    output(0) = 0
    var maxTan = 0f
    while (i < input.length) {
      val tan = input(i) / i
      if (tan > maxTan) {
        maxTan = tan
      }
      output(i) = maxTan
      i = i + 1
    }
  }

  sealed abstract class Tree {
    def maxPrevious: Float
  }

  case class Node(left: Tree, right: Tree) extends Tree {
    val maxPrevious = max(left.maxPrevious, right.maxPrevious)
  }

  case class Leaf(from: Int, until: Int, maxPrevious: Float) extends Tree

  /**
   * Traverses the specified part of the array and returns the maximum angle.
   */
  def upsweepSequential(input: Array[Float], from: Int, until: Int): Float = {
    var i: Int = from
    var maxT: Float = 0
    while (i < until) {
      maxT = max(maxT, input(i) / i)
      i = i + 1
    }
    maxT
  }

  /**
   * Traverses the part of the array starting at `from` and until `end`, and
   *  returns the reduction tree for that part of the array.
   *
   *  The reduction tree is a `Leaf` if the length of the specified part of the
   *  array is smaller or equal to `threshold`, and a `Node` otherwise.
   *  If the specified part of the array is longer than `threshold`, then the
   *  work is divided and done recursively in parallel.
   */
  def upsweep(input: Array[Float], from: Int, end: Int,
    threshold: Int): Tree = {
    val size = end - from
    if (size <= threshold) {
      val seqMax = upsweepSequential(input, from, end)
      Leaf(from, end, seqMax)
    } else {
      val mid = from + ((end - from) / 2)
      val (maxl, maxr) = parallel(upsweep(input, from, mid, threshold), upsweep(input, mid, end, threshold))
      Node(maxl, maxr)
    }
  }

  /**
   * Traverses the part of the `input` array starting at `from` and until
   *  `until`, and computes the maximum angle for each entry of the output array,
   *  given the `startingAngle`.
   */
  def downsweepSequential(input: Array[Float], output: Array[Float],
    startingAngle: Float, from: Int, until: Int): Unit = {

    var i: Int = if (from == 0) 1 else from
    var maxT: Float = startingAngle
    while (i < until) {
      maxT = max(maxT, input(i) / i)
      output(i) = maxT
      i = i + 1
    }
  }

  /**
   * Pushes the maximum angle in the prefix of the array to each leaf of the
   *  reduction `tree` in parallel, and then calls `downsweepTraverse` to write
   *  the `output` angles.
   */
  def downsweep(input: Array[Float], output: Array[Float], startingAngle: Float,
    tree: Tree): Unit = {
    tree match {
      case Leaf(from, until, _) => downsweepSequential(input, output, startingAngle, from, until)
      case Node(left, right) => {
        parallel(
          downsweep(input, output, startingAngle, left),
          downsweep(input, output, left.maxPrevious, right))
      }
    }
  }

  /** Compute the line-of-sight in parallel. */
  def parLineOfSight(input: Array[Float], output: Array[Float],
    threshold: Int): Unit = {
    val tree = upsweep(input, 0, input.length, threshold)
    downsweep(input, output, 0f, tree)
  }
}
