package com.goyal.addy.hackerrank.g2
import scala.collection.mutable
import scala.io.Source

/**
  * Created by addy on 7/18/17.
  */
object Solution extends App {

  def populateGraph(numVertices: Int, edges: Seq[Edge]): Graph = {
    val g = new Graph(numVertices)
    edges.par.foreach(g.addEdge)
    g
  }

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
//  override def main(args: Array[String]) {
////    val src: Iterator[String] = Iterator.continually(scala.io.StdIn.readLine())
//    //val src: Iterator[String] = Source.fromFile("/home/addy/Downloads/input01.txt").getLines()
//    val t = scala.io.StdIn.readInt()
//
//    for (a <- 0 until t) {
//      val Array(n, m) = scala.io.StdIn.readLine.split(" ").map(_.toInt)
//      val edges:List[Edge]  = (0 until m).map(_=>scala.io.StdIn.readLine()).map(line => {
//        val Array(start, end, weight) = line.split(" ").map(_.toInt)
//        Edge(start, end, weight)
//      }).toList
//      val g: Graph = populateGraph(n, edges)
//      val s = scala.io.StdIn.readInt()
//      val distances = getShortestDistances(g, s)
//      val res = distances.tail.filter(_ != 0).mkString(" ")
//      println(res)
//    }
//  }

  override def main(args: Array[String]) {
    //    val src: Iterator[String] = Iterator.continually(scala.io.StdIn.readLine())
    val src: Iterator[String] = Source.fromFile("/home/addy/Downloads/input07.txt").getLines()
    val t = src.next().toInt

    for (a <- 0 until t) {
      val Array(n, m) = src.next.split(" ").map(_.toInt)
      val edges: Seq[Edge]  = (0 until m).map(_=>src.next).map(line => {
        val Array(start, end, weight) = line.split(" ").map(_.toInt)
        Edge(start, end, weight)
      })
      val g: Graph = populateGraph(n, edges)
      val s = src.next().toInt
      val distances = getShortestDistances(g, s)
      val res = distances.tail.filter(_ != 0).mkString(" ")
      println(res)
    }
  }

}
class Graph(val numVertices: Int){
  def reverse(in: Edge) : Edge = Edge(in.endIndex, in.startIndex, in.weight)
  //The array of vertices in the graph.
  val vertices: Array[Vertex] = (0 to numVertices).map(Vertex(_, new mutable.HashMap[Int, Edge]())).toArray

  /**
    * Adds an edge from Start to End and an edge from End to Start. (undirected edge)
    * @param start the start index of the undireced edge
    * @param end the end index of the undirected edge
    * @param weight the weight of the edge
    */

  def addEdge(edge: Edge): Unit ={
    val start  = edge.startIndex
    val end = edge.endIndex
    vertices(start).edges.get(end) match {
      case None => vertices(start).edges.+=((end, edge))
      case Some(e: Edge) => if (edge.weight < e.weight ){
        vertices(start).edges(end) = edge
      }
    }
    val rev = reverse(edge)
    vertices(end).edges.get(start) match {
      case None => vertices(end).edges.+=((start, rev))
      case Some(e) => if (edge.weight < e.weight) {
        vertices(end).edges(start) = rev
      }
    }
  }

}
case class Vertex( index: Int, edges: mutable.Map[Int, Edge])
case class Edge(startIndex: Int, endIndex: Int, weight: Int)
case class Path(length: Int, destination: Int) extends Ordered[Path] {
  // Required as of Scala 2.11 for reasons unknown - the companion to Ordered
  // should already be in implicit scope
  import scala.math.Ordered.orderingToOrdered

  def compare(that: Path): Int = that.length compare this.length
}
