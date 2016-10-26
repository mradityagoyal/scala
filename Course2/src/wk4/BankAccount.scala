package wk4



class BankAccount extends Publisher{
  val balance = Var(0)
  
  def deposit(amount: Int): Unit = {
    if(amount > 0) {
      val current = balance()
      balance() = current + amount
    }
    publish()
  }
  
  def withdraw(amount: Int): Unit = {
    if(0 < amount && amount < balance()) {
      val current = balance()
      balance() = current - amount 
      publish()
    }else throw new Error("Insufficient funds")
  }
  
  def currentBalance =  balance
}