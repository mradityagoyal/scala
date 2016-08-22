package pouring

object Test {
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet

  val problem = new Pouring(Vector(4, 7, 9))      //> problem  : pouring.Pouring = pouring.Pouring@174d20a

  problem.moves                                   //> res0: scala.collection.immutable.IndexedSeq[Product with Serializable with p
                                                  //| ouring.Test.problem.Move] = Vector(Empty(0), Empty(1), Empty(2), Fill(0), Fi
                                                  //| ll(1), Fill(2), Pour(0,1), Pour(0,2), Pour(1,0), Pour(1,2), Pour(2,0), Pour(
                                                  //| 2,1))
  
  problem.solution(6)                             //> res1: Stream[pouring.Test.problem.Path] = Stream(Fill(0) Pour(0,1) Fill(2) P
                                                  //| our(2,1)-->Vector(0, 7, 6), ?)
}