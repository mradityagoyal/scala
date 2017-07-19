package com.goyal.addy.hackerrank.graph

import scala.collection.mutable

/**
  * Created by addy on 7/18/17.
  */
class Graph(val numVertices: Int){
  //The array of vertices in the graph.
  val vertices: Array[Vertex] = (0 to numVertices).map(Vertex(_, new mutable.HashMap[Int, Edge]())).toArray

  /**
    * Adds an edge from Start to End and an edge from End to Start. (undirected edge)
    * @param start the start index of the undireced edge
    * @param end the end index of the undirected edge
    * @param weight the weight of the edge
    */
  def addEdge(start: Int, end: Int, weight: Int): Unit = {
    val edge = Edge(start, end, weight)
    //add edge from start to end.. if this weight is less than the weith of already existing edge from start to end.
    vertices(start).edges.get(end) match {
      case None => vertices(start).edges.+=((end, edge))
      case Some(e: Edge) => if (edge.weight < e.weight ){
        vertices(start).edges(end) = edge
      }
    }
    val reverse = Edge.reverse(edge)
    vertices(end).edges.get(start) match {
      case None => vertices(end).edges.+=((start, reverse))
      case Some(e) => if (edge.weight < e.weight) {
        vertices(end).edges(start) = reverse
      }
    }
    //    vertices(end) = Vertex(end, vertices(end).edges :+ Edge(end, start, weight))
  }

}



case class Vertex( index: Int, edges: mutable.Map[Int, Edge]){

}



case class Edge(startIndex: Int, endIndex: Int, weight: Int){

}

object Edge {
  def reverse(in: Edge) : Edge = Edge(in.endIndex, in.startIndex, in.weight)
}
