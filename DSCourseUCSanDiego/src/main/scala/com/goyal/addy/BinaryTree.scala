package com.goyal.addy

import scala.annotation.tailrec
import scala.util.control.TailCalls.TailRec

case class BinaryTree[T <% Ordered[T]](key: T, left: Option[BinaryTree[T]] = None, right: Option[BinaryTree[T]] = None) {
  require(key != Nil, "Can't have Nil key") //TODO: check if required
  require(left match {
    case None => true
    case Some(l) => l.key <= key
  }, "Left can't be more than key")

  require(right match {
    case None => true
    case Some(r) => r.key > key
  }, "Right can't be less than key")

  lazy val height: Int = 1 + Math.max(left.map(_.height).getOrElse(0), right.map(_.height).getOrElse(0))

  lazy val size: Int = 1 + left.map(_.size).getOrElse(0) + right.map(_.size).getOrElse(0)

  lazy val min: T = left.map(_.min).getOrElse(key)

  lazy val max: T = left.map(_.max).getOrElse(key)

  def add(x: T): BinaryTree[T] = {
    if (x <= key) {
      lazy val newLeft = (left.map(_.add(x))).getOrElse(BinaryTree(x))
      BinaryTree(key, Some(newLeft), right)
    } else {
      lazy val newRight = (right.map(_.add(x))).getOrElse(BinaryTree(x))
      BinaryTree(key, left, Some(newRight))
    }
  }

  /**
   * pops and returns ths min and rest.
   */
  def popMin: (T, Option[BinaryTree[T]]) = left match {
    case None => (key, right) // if left is None 
    case Some(lv) => {
      val (min, newLeft) = lv.popMin
      (min, Some(BinaryTree(key, newLeft, right)))
    }
  }

  def nodesAtHeight(ht: Int): List[BinaryTree[T]] = {
    require(ht > 0, "Cant specify ht less than 1")
    if (ht == 1) {
      List(this)
    } else {
      val empt = List.empty[BinaryTree[T]]
      left.map(_.nodesAtHeight(ht - 1)).getOrElse(empt) ::: right.map(_.nodesAtHeight(ht - 1)).getOrElse(empt)
    }
  }

  def stringify: List[String] = {
    val tab = "\t"
    def nextLvl(in: List[Option[BinaryTree[T]]]): List[Option[BinaryTree[T]]] = {
      in.flatMap(_ match {
        case None => List[Option[BinaryTree[T]]](None, None) //TODO: fix
        case Some(nd) => List(nd.left, nd.right)
      })
    }
    def lvlToStr(in: List[Option[BinaryTree[T]]]): String = in.map(_ match {
      case None => "None"
      case Some(x) => x.key.toString()
    }).reduce(_ + " , " + _)

    @tailrec
    def recur(levelsLeft: Int, acc: List[String], thisLevel: List[Option[BinaryTree[T]]]): List[String] = {
      if (levelsLeft == 1) {
        acc :+ lvlToStr(thisLevel)
      } else {
        recur(levelsLeft - 1, acc :+ (("\t" * (levelsLeft - 1)) + lvlToStr(thisLevel)), nextLvl(thisLevel))
      }
    }
    recur(height, List.empty[String], List(Some(this)))
  }

}