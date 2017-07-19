package com.goyal.addy.hackerrank.graph


import scala.collection.mutable
import scala.io.Source

/**
  * Created by addy on 7/18/17.
  */
object DjkstraAttempt2 extends App {


  def getShortestDistances(g: Graph, s: Int): Array[Long] = {
    val distances = Array.fill(g.numVertices + 1)(-1L)
    //update distace to start as -999 ( to filter out later)
    val start = g.vertices(s)
    val queue: mutable.PriorityQueue[Path] = new mutable.PriorityQueue()
    val p0 = Path(0, s)
    queue.enqueue(p0)
    //      start.edges.values.map(edge => Path(edge.weight, edge.endIndex)).foreach(path => queue.enqueue(path))
    while (!queue.isEmpty) {
      val p: Path = queue.dequeue()
      //update shortest distance to end of edge
      val currentShortestDist = distances(p.destination)
      if (currentShortestDist == -1) {
        val destinationVertex = g.vertices(p.destination)
        distances(destinationVertex.index) = p.length

        //TODO: Enqueue new gyus here.
        val neighbors = destinationVertex.edges.values.map(edge => Path(edge.weight + p.length, edge.endIndex)).foreach(path => queue.enqueue(path))

      } else if (p.length < currentShortestDist) {
        println(s"This should have never happened. Path= $p Distances = $distances")
      }
    }

    distances
  }

  case class Path(length: Int, destination: Int) extends Ordered[Path] {
    // Required as of Scala 2.11 for reasons unknown - the companion to Ordered
    // should already be in implicit scope
    import scala.math.Ordered.orderingToOrdered

    def compare(that: Path): Int = that.length compare this.length
  }


  override def main(args: Array[String]) {

    //    val src = scala.io.StdIn
    //    src.readI
//    val src: Iterator[String] = Iterator.continually(scala.io.StdIn.readLine())
    val src: Iterator[String] = Source.fromFile("/home/addy/Downloads/input01.txt").getLines()


    val t = src.next.toInt

    for (a <- 0 until t) {
      val Array(n, m) = src.next.split(" ").map(_.toInt)
      val g: Graph = DjkstraHelper.populateGraph(n, m, src)
//      print("finished creating new graph")
      val s = src.next.toInt
      val distances = getShortestDistances(g, 1)
      val res = distances.tail.filter(_ != 0).mkString(" ")
      println(res)
    }
  }

}
