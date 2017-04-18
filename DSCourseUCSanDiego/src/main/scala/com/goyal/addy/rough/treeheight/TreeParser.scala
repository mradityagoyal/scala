package com.goyal.addy.rough.treeheight

import scala.io.StdIn

object TreeParser extends App {

  //ignore first
  StdIn.readLine()

  lazy val parArray: Array[Int] = StdIn.readLine().split(" ").map(_.toInt)
  lazy val root: Node[Int] = parseTree(parArray)

  val height = root.height
  println(height)

  def parentArrayToGroups(parentArray: Array[Int]): Map[Int, Array[(Int, Int)]] = {
    (parentArray.zipWithIndex).groupBy { case (parent, idx) => parent }
  }

  def parseTree(parentArray: Array[Int]): Node[Int] = {
    lazy val grouped: Map[Int, Array[(Int, Int)]] = parentArrayToGroups(parentArray)
    def nodeInfoToNode(nodeInfo: (Int, Int)): Node[Int] = {
      val (parentIdx, selfIdx) = nodeInfo
      lazy val childNodeInfos: Array[(Int, Int)] = grouped.getOrElse(selfIdx, Array.empty[(Int, Int)])
      if (childNodeInfos.isEmpty) {
        //if has no children. 
        Leaf(selfIdx)
      } else {
        val data = selfIdx;
        //child nodes recursively. 
        lazy val childNodes: Array[Node[Int]] = childNodeInfos.map(nodeInfoToNode(_))
        InteriorNode(data, 1 + childNodes.map(_.height).max, childNodes)
      }
    }
    val root = grouped(-1).head
    nodeInfoToNode(root)
  }

  def parseTreeIteratively(parentArray: Array[Int]): Node[Int] = {
    val n = parentArray.length
    val populated: Array[Option[Node[Int]]] = Array.fill(n)(None)

    var rootIdx: Int = -1
    for (i <- 0 until n) {
      val nodeI: Node[Int] = populated(i).getOrElse(Leaf(i))
      populated(i) = Some(nodeI)

      val parentIdx = parentArray(i)
      if (parentIdx == -1) {
        rootIdx = i
      } else {
        val newParent: Node[Int] = (populated(parentIdx).getOrElse(Leaf(parentIdx))).addChild(nodeI)
        populated(parentIdx) = Some(newParent)
      }

    }
    populated(rootIdx).get
  }

  abstract class Node[T] {
    def height: Int
    def data: T

    def addChild(child: Node[T]): Node[T]
  }

  case class Leaf[T](data: T) extends Node[T] {
    def height = 1
    def addChild(child: Node[T]): Node[T] = InteriorNode(data, 1 + child.height, Array(child))
  }

  case class InteriorNode[T](data: T, height: Int, children: Array[Node[T]]) extends Node[T] {
    def addChild(child: Node[T]): Node[T] = {
      if (child.height < height) {
        InteriorNode(data, height, children :+ child)
      } else {
        InteriorNode(data, 1 + child.height, children :+ child)
      }

    }
  }

}