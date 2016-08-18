package patmat
import java.util.concurrent.ConcurrentHashMap.ForEachKeyTask
import patmat.Huffman._

object huffmanTesting {
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet
  
  val l1 = List('a', 'b','c')                     //> l1  : List[Char] = List(a, b, c)
  val grouped = l1.groupBy(identity)              //> grouped  : scala.collection.immutable.Map[Char,List[Char]] = Map(b -> List(b
                                                  //| ), a -> List(a), c -> List(c))
  grouped.mapValues(x => x.length).toList         //> res0: List[(Char, Int)] = List((b,1), (a,1), (c,1))
  
  def decodedSecret: List[Char] = decode(frenchCode, secret)
                                                  //> decodedSecret: => List[Char]
  println(decodedSecret)                          //> List(h, u, f, f, m, a, n, e, s, t, c, o, o, l)
  
  val sec = quickEncode(frenchCode)("hello".toList)
                                                  //> sec  : List[patmat.Huffman.Bit] = List(0, 0, 1, 1, 1, 0, 1, 1, 1, 0, 0, 1, 0
                                                  //| , 1, 0, 1, 0, 1, 0, 1, 0, 0)
  
   decode(frenchCode, sec)                        //> res1: List[Char] = List(h, e, l, l, o)
   
   
   
}