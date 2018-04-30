package model

import java.util.Date

import services.{Addy, ROTH, Ragini}

import scala.concurrent.{Await, Future}
import scala.concurrent.duration._

object TransactionsDAO  {

  final val TIMEOUT = 2.seconds

  import slick.jdbc.H2Profile.api._

  val Transactions = TableQuery[TransactionsTable]

  val db = Database.forConfig("h2")

  def freshTestData = Seq(
    TransactionRow(0, "NVDA", "BUY", 12.1, Some(100), 1210, new Date(), Some(4.95), "Test", ROTH(), Addy())
//    TransactionRow(0, "NVDA", "SELL", 12.1, 100, 1210, new Date(), 4.95, "Test", ROTH(), Addy()),
//    TransactionRow(0, "NVDA", "SELL", 12.1, 100, 1210, new Date(), 4.95, "Test",ROTH(), Ragini()),
//    TransactionRow(0, "NVDA", "BUY", 1.1, 100, 1110, new Date(), 4.95, "Test",ROTH(), Ragini())
  )

  def createTable = {
    Await.result(db.run(Transactions.schema.create), TIMEOUT)
  }

  def insertTransactions(transactions: Seq[TransactionRow]): Option[Int] = Await.result(
    db.run(Transactions ++= transactions),
    TIMEOUT
  )

//  createTable
//
//  exec(Transactions ++= freshTestData)



//  val queryAction: DBIO[Seq[TransactionRow]] = Transactions.result
//  val result: Seq[TransactionRow] = Await.result(db.run(queryAction), 2.seconds)
//
//  result.foreach(res => println(s"Queried from DB $res"))
//
//  def exec[T](action: DBIO[T]): T =
//    Await.result(db.run(action), 2.seconds)
//
//  val buysQuery = for {
//    transaction <- Transactions if transaction.action === "BUY"
//  } yield transaction
//
//  val buys: Seq[TransactionRow] = exec(buysQuery.result)
//
//  buys.foreach(buy => println(s"Bought ${buy.symbol} on ${buy.date} @ ${buy.price} for total of ${buy.amount}"))

}
