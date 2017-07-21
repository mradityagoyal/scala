package addy.datastructures.and.algorithms.wk3.assignment

/**
  * Created by addy on 7/20/17.
  */
object PhoneBook extends App{


  //assumes p is a prime number
  def getHashFunc(a: Int, b: Int, m: Int, p: Int): Int => Int = {
    val hashFunc: Int => Int = x => ((a * x + b) % p) % m
    hashFunc
  }

  val m = 1000
  val p = 1000019
  val a = 2
  val b = 3

  val hFunc: Int => Int = getHashFunc(a, b, m, p)

  val arr: Array[List[Contact]] = Array.fill(m)(List[Contact]())

  val N = scala.io.StdIn.readInt()

  val queryLines: Seq[String] = for (i <- 0 until N) yield scala.io.StdIn.readLine
  val queries: Seq[Query] = queryLines map lineToQuery

  for (q <- queries){
    processQuery(q)
  }

  def lineToQuery(line: String): Query ={
    val arr = line.split(" ")
    arr.head match {
      case "add" => AddQuery(arr(1).toInt, arr(2))
      case "del" => DelQuery(arr(1).toInt)
      case "find" => FindQuery(arr(1).toInt)
      case _ => throw new RuntimeException("Invalid query format")
    }
  }

  def processQuery(q: Query): Unit = q match {
    case addQuery : AddQuery => {
      val contact: Contact = Contact(addQuery.number, addQuery.name)
      val hash = hFunc(addQuery.number)
      val bucket = arr(hash)
      if(bucket.isEmpty){
        arr(hash) = List(contact)
      }else{
        val (c, rest) = bucket.partition(c => c.number == addQuery.number)
        arr(hash) = rest :+ contact
      }
    }
    case delQuery: DelQuery => {
      val hash = hFunc(delQuery.number)
      val bucket = arr(hash)
      if(!bucket.isEmpty){
        arr(hash) = bucket.filterNot(c => c.number == delQuery.number)
      }
    }
    case findQuery: FindQuery => {
      val hash = hFunc(findQuery.number)
      val bucket = arr(hash)
      val c: Option[Contact] = bucket.filter(_.number == findQuery.number).headOption
      c match {
        case None => println("not found")
        case Some(x) => println(x.name)
      }
    }
  }

}

case class Contact(number: Int, name: String)

sealed trait Query

case class AddQuery(number: Int, name: String) extends Query
case class DelQuery(number: Int) extends Query
case class FindQuery(number: Int) extends Query
