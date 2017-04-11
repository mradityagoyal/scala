package com.goyal.addy

case class Node[A](key: A, left: Option[Node[A]] = None, right: Option[Node[A]] = None) {

  lazy val height: Int = 1 + Math.max(left.map(_.height).getOrElse(0), right.map(_.height).getOrElse(0))

  lazy val size: Int = 1 + left.map(_.size).getOrElse(0) + right.map(_.size).getOrElse(0)

  def nodesAtHeight(ht: Int): List[Node[A]] = {
    require(ht > 0, "Cant specify ht less than 1")
    if (ht == 1) {
      List(this)
    } else {
      val empt = List.empty[Node[A]]
      left.map(_.nodesAtHeight(ht - 1)).getOrElse(empt) ::: right.map(_.nodesAtHeight(ht - 1)).getOrElse(empt)
    }
  }

  def rowN(n: Int): String = {
    if (n == 1) {
      key.toString()
    } else {
      left.map(_.rowN(n - 1)).getOrElse("\t") + "," + right.map(_.rowN(n - 1)).getOrElse("\t")
    }
  }

  def stringify: List[String] = {

    //h number of lines will be there.
    val tab = "\t"

    val line1 = tab * (height - 1) + key

    def nextLvl(in: List[Option[Node[A]]]): List[Option[Node[A]]] = {
      in.flatMap(_ match {
        case None => List[Option[Node[A]]](None, None) //TODO: fix
        case Some(nd) => List(nd.left, nd.right)
      })
    }

    def rowToStr(in: List[Option[Node[A]]]): String = in.map(_ match {
      case None => "None"
      case Some(x) => x.key.toString()
    }).reduce(_ +" , " + _)

    val top = List(Some(this))
    val l2 = nextLvl(top)
    val l2s = rowToStr(l2)
    
    val rowsToReturn : List[String] = List.empty
    
    rowToStr(top)
    
    def recur(levelsLeft: Int, acc: List[String], thisLevel: List[Option[Node[A]]]): List[String] = {
      if(levelsLeft == 1){
        acc :+ rowToStr(thisLevel)
      }else {
        recur(levelsLeft -1, acc :+ (("\t"*(levelsLeft -1)) + rowToStr(thisLevel)), nextLvl(thisLevel))
      }
    }

    recur(height, List.empty[String], List(Some(this)))
  }

}