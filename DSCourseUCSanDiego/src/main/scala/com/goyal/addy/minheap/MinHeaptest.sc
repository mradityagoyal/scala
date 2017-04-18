package com.goyal.addy.minheap
import MinHeap._

object MinHeaptest {
val lf5 = LeafMH(5)                               //> lf5  : com.goyal.addy.minheap.MinHeap.LeafMH = LeafMH(5)
val lf1 = LeafMH(1)                               //> lf1  : com.goyal.addy.minheap.MinHeap.LeafMH = LeafMH(1)
val lf10 = LeafMH(10)                             //> lf10  : com.goyal.addy.minheap.MinHeap.LeafMH = LeafMH(10)

val ndr = NodeMH(1, Some(lf5), Some(lf10))        //> ndr  : com.goyal.addy.minheap.MinHeap.NodeMH = NodeMH(1,Some(LeafMH(5)),Some
                                                  //| (LeafMH(10)))
ndr.getMax                                        //> res0: Int = 10


  
  
  
  
}