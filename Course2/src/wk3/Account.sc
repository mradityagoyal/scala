package wk3

import wk4.Consolidator
import wk4.Consolidator

object Account {
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet
  
  val acct = new BankAccount                      //> acct  : wk3.BankAccount = wk3.BankAccount@6442b0a6
  acct.deposit(50)
  acct.currentBalance                             //> res0: Int = 50
  
  acct withdraw 10
  
  acct.currentBalance                             //> res1: Int = 40
  
  acct withdraw 20
  
  val acct2 = new BankAccount                     //> acct2  : wk3.BankAccount = wk3.BankAccount@60f82f98
  
  acct2 deposit 10
  
  acct2 currentBalance                            //> res2: Int = 10
  
  
  
  val cons = new Consolidator(List(acct, acct2))  //> cons  : wk4.Consolidator = wk4.Consolidator@15975490
  
  cons.totalBalance                               //> res3: Int = 30
  
  acct2 deposit 30
  
  cons.totalBalance                               //> res4: Int = 60
}