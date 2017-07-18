package com.goyal.addy.hackerrank.graph

import scala.collection.mutable

/**
  * Created by addy on 7/17/17.
  * solution for https://www.hackerrank.com/challenges/5595
  *
  */
object DjkstraShortestReach2 extends App{




  def populateGraph(numVertices: Int, numEdges: Int): Graph = {
    val g = new Graph(numVertices)
    for(i <- 0 until numEdges){
      val Array(start, end, weight) = scala.io.StdIn.readLine().split(" ").map(_.toInt)
      g.addEdge(start, end, weight)
    }
    g
  }

  def getShortestDistances(g: Graph, s: Int): Array[Long] = {
    val distances = Array.fill(g.numVertices+1)(-1L)
    //update distace to start as -999 ( to filter out later)
    distances(s) = -999
    val visited = mutable.ListBuffer[Edge]()

    val start = g.vertices(s)
    val queue: mutable.Queue[(Edge, Long)] = mutable.Queue.empty

    for( e <- start.edges){
      queue.enqueue((e, 0))
    }

    while (!queue.isEmpty){
      val (edge, acc) = queue.dequeue()
      //update shortest distance to end of edge.
      val currentShortestDist = distances(edge.endIndex)
      if(currentShortestDist == -1){
        distances(edge.endIndex) = acc + edge.weight
      }else if(acc + edge.weight < currentShortestDist){
        distances(edge.endIndex) = acc + edge.weight
      }
      //add to visited List.
      visited+=edge
      visited+=reverseEdge(edge)


      //enqueue new neighbors. //TODO: Need to maintain a list of visited vertices. or already traversed edges.. so as not to traverse them again.  
      val endVertex = g.vertices(edge.endIndex)
      for (e <- endVertex.edges.filter(!visited.contains(_))){
        queue.enqueue((e, acc + edge.weight))
      }

    }

    distances
  }

  def reverseEdge(e: Edge): Edge = Edge(e.endIndex, e.startIndex, e.weight)


  override def main(args: Array[String]) {
    val t = scala.io.StdIn.readInt()

    for(a <- 0 until t){
      val Array(n,m) = scala.io.StdIn.readLine().split(" ").map(_.toInt)
      val g = populateGraph(n, m)
      val s = scala.io.StdIn.readInt()
      val distances = getShortestDistances(g, 1)
      val res = distances.tail.filter(_ != -999).mkString(" ")
      println(res)
    }
  }

}





class Graph(val numVertices: Int){
  //The array of vertices in the graph.
  val vertices: Array[Vertex] = (0 to numVertices).map(Vertex(_, List[Edge]())).toArray

  /**
    * Adds an edge from Start to End and an edge from End to Start. (undirected edge)
    * @param start the start index of the undireced edge
    * @param end the end index of the undirected edge
    * @param weight the weight of the edge
    */
  def addEdge(start: Int, end: Int, weight: Int): Unit = {
    vertices(start) = Vertex(start, vertices(start).edges :+ Edge(start, end, weight))
    vertices(end) = Vertex(end, vertices(end).edges :+ Edge(end, start, weight))
  }
}



case class Vertex(val index: Int, val edges: List[Edge])

case class Edge(startIndex: Int, endIndex: Int, weight: Int)
