package wk4


object SignalsWS {

Math.pow( 4, 2)                                   //> res0: Double = 16.0
  def consolidated(accts: List[BankAccount]): Signal[Int] =  Signal(accts.map(_.balance()).sum)
                                                  //> consolidated: (accts: List[wk4.BankAccount])wk4.Signal[Int]
    
  val a = new BankAccount()                       //> a  : wk4.BankAccount = wk4.BankAccount@566776ad
  val b = new BankAccount()                       //> b  : wk4.BankAccount = wk4.BankAccount@6108b2d7
  
  val c = consolidated(List(a,b))                 //> c  : wk4.Signal[Int] = wk4.Signal@2d3fcdbd
  
  c()                                             //> res1: Int = 0
  
  a deposit 10
  
  c()                                             //> res2: Int = 10
  
  b deposit 20
  
  c()                                             //> res3: Int = 30
  
  val xchange = Signal(246.00)                    //> xchange  : wk4.Signal[Double] = wk4.Signal@7225790e
  
  val inDollor = Signal(c() * xchange())          //> inDollor  : wk4.Signal[Double] = wk4.Signal@54a097cc
  
  inDollor()                                      //> res4: Double = 7380.0
  
  b deposit 10
  
  inDollor()                                      //> res5: Double = 9840.0
}