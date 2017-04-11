package com.goyal.addy

object BTWrkSheet {
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet

  val (leaf, blah, wrld) = (Node("Hello"), Node("blah"), Node("world"))
                                                  //> leaf  : com.goyal.addy.Node[String] = Node(Hello,None,None)
                                                  //| blah  : com.goyal.addy.Node[String] = Node(blah,None,None)
                                                  //| wrld  : com.goyal.addy.Node[String] = Node(world,None,None)

  val l2 = Node("hi", Some(blah), Some(wrld))     //> l2  : com.goyal.addy.Node[String] = Node(hi,Some(Node(blah,None,None)),Some(
                                                  //| Node(world,None,None)))
  l2.rowN(2)                                      //> res0: String = blah,world

  val l3 = Node("l3", None, Some(l2))             //> l3  : com.goyal.addy.Node[String] = Node(l3,None,Some(Node(hi,Some(Node(blah
                                                  //| ,None,None)),Some(Node(world,None,None)))))
  l3.rowN(1)                                      //> res1: String = l3
  l3.rowN(2)                                      //> res2: String = "	,hi"
  l3.rowN(3)                                      //> res3: String = "	,blah,world"
  
  
  l3.stringify.foreach(println(_))                //> 		l3
                                                  //| 	None , hi
                                                  //| None , None , blah , world


}