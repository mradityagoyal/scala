package com.goyal.addy.hackerrank.g3

import scala.collection.mutable
import scala.io.Source

/**
  * Created by addy on 7/18/17.
  */
object Solution extends App {

  case class Edge(start: Int, end: Int, weight: Int)

  def reverse(e: Edge): Edge = Edge(e.end, e.start, e.weight)



  def populateGraph(numVertices: Int, lines: Seq[String]): Graph = {
    
    val edges: List[Edge] = lines.par.map(line => {
      val Array(start, end, weight) = line.split(" ").map(_.toInt)
      Edge(start, end, weight)
    }).toList

    val allDirected: List[Edge] = edges ::: edges.par.map(reverse).toList
    val groupedByStart: Map[Int, List[Edge]] = allDirected.groupBy(_.start)
    val adjMatrix : Map[Int , Map[Int, Int]] = groupedByStart.map(startAndEdge => startAndEdge match {
      case (start: Int, edges: List[Edge]) => start -> {
        val groupedByDest = edges.groupBy(_.end)
        groupedByDest.map(t => t match{
          case (destination: Int, edges: Seq[Edge]) => destination -> edges.map(_.weight).min
        })
      }
    })
    val g = new Graph(numVertices, adjMatrix )
    g
  }

  def getShortestDistances(g: Graph, s: Int): Array[Long] = {
    val distances = Array.fill(g.numVertices + 1)(-1L)
    //update distace to start as -999 ( to filter out later)
    val start: Map[Int, Int] = g.adjMatrix(s) // all the outgoing from start.
    val queue: mutable.PriorityQueue[Path] = new mutable.PriorityQueue()
    val p0 = Path(0, s)
    queue.enqueue(p0)
    //      start.edges.values.map(edge => Path(edge.weight, edge.endIndex)).foreach(path => queue.enqueue(path))
    while (!queue.isEmpty) {
      val p: Path = queue.dequeue()
      //update shortest distance to end of edge
      val currentShortestDist = distances(p.destination)
      if (currentShortestDist == -1) {
        distances(p.destination) = p.length
        // Enqueue new gyus here.
        val nextDestinations: Map[Int, Int] = g.adjMatrix(p.destination)
        nextDestinations.map{
          case (dest: Int, weight: Int) => Path(p.length + weight, dest)
        }.foreach(queue.enqueue(_))

      } else if (p.length < currentShortestDist) {
        println(s"This should have never happened. Path= $p Distances = $distances")
      }
    }

    distances
  }
  override def main(args: Array[String]) {
    val t = scala.io.StdIn.readInt()

    for (a <- 0 until t) {
      val Array(n, m) = scala.io.StdIn.readLine.split(" ").map(_.toInt)
      val edges:Seq[String]  = (0 until m).map(_=>scala.io.StdIn.readLine())
      val g: Graph = populateGraph(n, edges)
      val s = scala.io.StdIn.readInt()
      val distances = getShortestDistances(g, s)
      val res = distances.tail.filter(_ != 0).mkString(" ")
      println(res)
    }
  }

//  override def main(args: Array[String]) {
//    //    val src: Iterator[String] = Iterator.continually(scala.io.StdIn.readLine())
//    val src: Iterator[String] = Source.fromFile("/home/addy/Downloads/input07.txt").getLines()
//    val t = src.next().toInt
//
//    for (a <- 0 until t) {
//      val Array(n, m) = src.next.split(" ").map(_.toInt)
//      val edges: Seq[String]  = (0 until m).map(_=>src.next)
//
//      val g: Graph = populateGraph(n, edges)
//      val s = src.next().toInt
//      val distances = getShortestDistances(g, s)
//      val res = distances.tail.filter(_ != 0).mkString(" ")
//      println(res)
//    }
//  }

}
class Graph(val numVertices: Int, val adjMatrix: Map[Int, Map[Int, Int]])


case class Path(length: Int, destination: Int) extends Ordered[Path] {
  // Required as of Scala 2.11 for reasons unknown - the companion to Ordered
  // should already be in implicit scope
  import scala.math.Ordered.orderingToOrdered

  def compare(that: Path): Int = that.length compare this.length
}
