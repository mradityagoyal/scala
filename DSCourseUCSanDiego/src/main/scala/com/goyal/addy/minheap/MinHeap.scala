package com.goyal.addy.minheap

object MinHeap {

  abstract class MinHeap {
    def height: Int
    def data: Int
    def getMax: Int
    def getMin: Int = data
  }

  case class NodeMH(data: Int, left: Option[MinHeap], right: Option[MinHeap]) extends MinHeap {
    require({
      val leftGtData: Boolean = if (left.isDefined) {
        left.get.data > data
      } else true
      val rightGtData: Boolean = if (right.isDefined) {
        right.get.data > data
      } else true

      val rightGtLeft: Boolean = if (left.isDefined && right.isDefined) {
        right.get.data > left.get.data
      } else true

      leftGtData && rightGtData && rightGtLeft
    })
    
    def this(data: Int) = {
      this(data, None, None)
    }

    lazy val height: Int = right match {
      case None => left.map(_.height).getOrElse(0) + 1
      case Some(rt) => left.map(lt => Math.min(rt.height, lt.height)).getOrElse(rt.height) + 1
    }

    lazy val getMax: Int = right.map(_.getMax).getOrElse(left.map(_.getMax).getOrElse(data))

  }
}