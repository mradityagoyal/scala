package addy.datastructures.and.algorithms.wk4

import scala.annotation.tailrec

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

  lazy val max: T = right.map(_.max).getOrElse(key)

  def push(x: T): BinaryTree[T] = {
    if (x <= key) {
      lazy val newLeft = (left.map(_.push(x))).getOrElse(BinaryTree(x))
      BinaryTree(key, Some(newLeft), right)
    } else {
      lazy val newRight = (right.map(_.push(x))).getOrElse(BinaryTree(x))
      BinaryTree(key, left, Some(newRight))
    }
  }

  def add(that: BinaryTree[T]): BinaryTree[T] = that.left match {
      //if left is none
      case None => that.right match {
        case None => this.push(that.key) //left and right both None
        case Some(rt) => this.push(that.key).add(rt) //left none right has something. 
      }
      case Some(lt) => {
        lazy val leftAdded = this.push(that.key).add(lt)
        that.right match {
          case None => leftAdded
          case Some(rt) => leftAdded.add(rt)
        }
      }
    }
  
  def inOrder: List[T] = left match {
    case None => right match {
        case None => List(key)
        case Some(rt) => key +: rt.inOrder
      }
    case Some(lt) => right match {
      case None => lt.inOrder :+ key 
      case Some(rt) => lt.inOrder ::: List(key) ::: rt.inOrder
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

  @deprecated
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