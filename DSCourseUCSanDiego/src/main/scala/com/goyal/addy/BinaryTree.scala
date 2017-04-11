package com.goyal.addy


case class Node[A](key: A, left: Option[Node[A]] = None, right: Option[Node[A]] = None) {
    
    lazy val height: Int = 1 + Math.max(left.map(_.height).getOrElse(0), right.map(_.height).getOrElse(0))
    
    lazy val size: Int = 1 + left.map(_.size).getOrElse(0) + right.map(_.size).getOrElse(0)
    
    def nodesAtHeight(ht: Int): List[Node[A]] = {
      require(ht >0, "Cant specify ht less than 1")
      if(ht == 1){
        List(this)
      }else{
        val empt = List.empty[Node[A]]
        left.map(_.nodesAtHeight(ht -1)).getOrElse(empt) ::: right.map(_.nodesAtHeight(ht -1)).getOrElse(empt)
      }
    }
    
    
  }