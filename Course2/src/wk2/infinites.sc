package wk2

object infinites {
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet
  
  def from(n: Int): Stream[Int] = n #:: from(n+1) //> from: (n: Int)Stream[Int]
  
  from(5)(10)                                     //> res0: Int = 15
  val nats = from(0)                              //> nats  : Stream[Int] = Stream(0, ?)
  
  val m4s = nats map (_*4)                        //> m4s  : scala.collection.immutable.Stream[Int] = Stream(0, ?)
  
  val start = from(2)                             //> start  : Stream[Int] = Stream(2, ?)
  start.filter(x => x%2 != 0)                     //> res1: scala.collection.immutable.Stream[Int] = Stream(3, ?)
  
  def fil(x: Int)(y: Int): Boolean = (y % x) != 0 //> fil: (x: Int)(y: Int)Boolean
  
  def sieve(s: Stream[Int]): Stream[Int] = {
    s.head #:: sieve(s.tail filter (_% s.head != 0))
  }                                               //> sieve: (s: Stream[Int])Stream[Int]
  
  val primes = sieve(start)                       //> primes  : Stream[Int] = Stream(2, ?)
  primes(4)                                       //> res2: Int = 11
  
}