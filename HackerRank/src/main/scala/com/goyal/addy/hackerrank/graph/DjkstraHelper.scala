package com.goyal.addy.hackerrank.graph

/**
  * Created by addy on 7/18/17.
  */
object DjkstraHelper {

  def populateGraph(numVertices: Int, numEdges: Int, src: Iterator[String]): Graph = {
    val g = new Graph(numVertices)
    for(i <- 0 until numEdges){
      val Array(start, end, weight) = src.next.split(" ").map(_.toInt)
      g.addEdge(start, end, weight)
    }
    g
  }

}
