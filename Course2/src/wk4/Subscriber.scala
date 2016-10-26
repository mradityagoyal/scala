package wk4

trait Subscriber {
  
  def handler(pub: Publisher)
  
}