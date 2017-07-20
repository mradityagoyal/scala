package addy.datastructures.and.algorithms.wk3

/**
  * Created by agoyal on 7/20/17.
  * A binary tree as defined in the coursera course at https://www.coursera.org/learn/data-structures/lecture/0g1dl/basic-operations
  *
  */
sealed trait BTreeNode[T]{
  def getMax: T
}

case class Leaf[T](value: T) extends BTreeNode[T]{
  override def getMax: T = value
}
case class Node[T](value: T, left: Option[BTreeNode[T]], right: Option[BTreeNode[T]]) extends BTreeNode[T]{
  override def getMax: T = value
}


