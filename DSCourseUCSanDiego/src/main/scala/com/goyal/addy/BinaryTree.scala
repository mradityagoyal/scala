package com.goyal.addy


case class Node[A](key: A, left: Option[Node[A]] = None, right: Option[Node[A]] = None) {
    
    def height: Int = {
      1 + Math.max(left.map(_.height).getOrElse(0), right.map(_.height).getOrElse(0))
    }
  }