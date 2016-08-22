package week1
import week1.Quer._
object Queries {
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet
  
  val books  = List( Book("ragini", "wife"), Book("aditya", "IBM"),
                       Book("ragini", "accenture"), Book("dhruv", "deloitte"), Book("ragini", "deloitte"))
                                                  //> books  : List[week1.Quer.Book] = List(Book(ragini,wife), Book(aditya,IBM), B
                                                  //| ook(ragini,accenture), Book(dhruv,deloitte), Book(ragini,deloitte))
  for(x <- books if x.name.equals("dhruv")) yield x
                                                  //> res0: List[week1.Quer.Book] = List(Book(dhruv,deloitte))
  for(x <- books if x.employer.equals("deloitte")) yield x.name
                                                  //> res1: List[String] = List(dhruv, ragini)
  
  for(x <- books if x.name.equals("ragini")) yield x.employer
                                                  //> res2: List[String] = List(wife, accenture, deloitte)
  
  books.groupBy(x => x.employer).mapValues( value => for(x <- value) yield x.name)
                                                  //> res3: scala.collection.immutable.Map[String,List[String]] = Map(IBM -> List(
                                                  //| aditya), deloitte -> List(dhruv, ragini), accenture -> List(ragini), wife ->
                                                  //|  List(ragini))
  
}