package com.goyal.addy.hackerrank.graph

import java.util.Scanner

import scala.collection.mutable
import scala.io.Source

/**
  * Created by addy on 7/17/17.
  * solution for https://www.hackerrank.com/challenges/5595
  *
  */
object DjkstraShortestReach2 extends App{



  def getShortestDistances(g: Graph, s: Int): Array[Long] = {
    val distances = Array.fill(g.numVertices+1)(-1L)
    //update distace to start as -999 ( to filter out later)
    distances(s) = -999
    val visited = mutable.ListBuffer[Edge]()

    val start = g.vertices(s)
    val queue: mutable.Queue[(Edge, Long)] = mutable.Queue.empty

    for( e <- start.edges.values){
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
      visited+=Edge.reverse(edge)


      //enqueue new neighbors. //TODO: Need to maintain a list of visited vertices. or already traversed edges.. so as not to traverse them again.  
      val endVertex = g.vertices(edge.endIndex)
      for (e <- endVertex.edges.values.filter(!visited.contains(_))){
        queue.enqueue((e, acc + edge.weight))
      }

    }

    distances
  }




  override def main(args: Array[String]) {

//    val src = scala.io.StdIn
//    src.readI
    val src = Source.fromFile("/home/addy/Downloads/input01.txt").getLines()


    val t = src.next.toInt

    for(a <- 0 until t){
      val Array(n,m) = src.next.split(" ").map(_.toInt)
      val g = DjkstraHelper.populateGraph(n, m, src)
      print("finished creating new graph")
      val s = src.next.toInt
      val distances = getShortestDistances(g, s)
      val res = distances.tail.filter(_ != -999).mkString(" ")
      println(res)
    }
  }

}
