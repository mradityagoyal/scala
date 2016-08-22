package wk2

object wk2 {
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet
  
  def isPrime(x: Int): Boolean = (2 to x-1) forall(y => x%y !=0 )
                                                  //> isPrime: (x: Int)Boolean
  isPrime(4)                                      //> res0: Boolean = false
  isPrime(7)                                      //> res1: Boolean = true
  
  ((1 to 1000).toStream filter isPrime)(5)        //> res2: Int = 11
}