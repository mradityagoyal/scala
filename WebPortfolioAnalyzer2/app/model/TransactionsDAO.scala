package model

import java.util.Date

import scala.concurrent.{Await, Future}
import scala.concurrent.duration._

object TransactionsDAO extends App{

  import slick.jdbc.H2Profile.api._

  val Transactions = TableQuery[TransactionsTable]

  val db = Database.forConfig("h2")

  def freshTestData = Seq(
    TransactionRow(0, "NVDA", "BUY", 12.1, 100, 1210, new Date(), 4.95, "Test"),
    TransactionRow(0, "NVDA", "SELL", 12.1, 100, 1210, new Date(), 4.95, "Test"),
    TransactionRow(0, "NVDA", "SELL", 12.1, 100, 1210, new Date(), 4.95, "Test"),
    TransactionRow(0, "NVDA", "BUY", 1.1, 100, 1110, new Date(), 4.95, "Test")
  )

  def createTable = {
    Await.result(db.run(Transactions.schema.create), 2.seconds)
  }

  createTable

  val insert: DBIO[Option[Int]] = Transactions ++= freshTestData

  val insertResult = Await.result(db.run(insert), 2.seconds)

  println(s"inserted $insertResult records")

  val queryAction: DBIO[Seq[TransactionRow]] = Transactions.result
  val result: Seq[TransactionRow] = Await.result(db.run(queryAction), 2.seconds)

  result.foreach(res => println(s"Queried from DB $res"))

}
