package com.goyal.addy.rough.treeheight

object TreeHeightAssignment {
 
  val parArray: Array[Int] = Array(2,2,-1)        //> parArray  : Array[Int] = Array(2, 2, -1)
  val node = TreeParser.parseTreeIteratively(parArray)
                                                  //> node  : com.goyal.addy.rough.treeheight.TreeParser.Node[Int] = InteriorNode(
                                                  //| 2,2,[Lcom.goyal.addy.rough.treeheight.TreeParser$Node;@13c78c0b)
   node.height                                    //> res0: Int = 2
   
   (node.addChild(node)).height                   //> res1: Int = 3

}