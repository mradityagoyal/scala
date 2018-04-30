package model



import java.sql.Timestamp

import slick.lifted.Tag
import slick.jdbc.H2Profile.api._


final class TransactionsTable(tag: Tag) extends Table[TransactionRow](tag, "TRANSACTIONS") {

  implicit val dateMapper = MappedColumnType.base[java.util.Date, java.sql.Timestamp](
    d => new java.sql.Timestamp(d.getTime),
    d => new java.util.Date(d.getTime))

  def id = column[Long]("ID", O.PrimaryKey, O.AutoInc)
  def symbol = column[String]("SYMBOL")
  def action = column[String]("ACTION")
  def quantity = column[Double]("QUANTITY")
  def price = column[Double]("PRICE")
  def amount = column[Double]("AMOUNT")
  def date = column[java.util.Date]("DATE")
  def commission = column[Double]("COMMISSION")
  def description = column[String]("DESCRIPTION")
  def * = (id, symbol, action, quantity, price, amount, date, commission, description).mapTo[TransactionRow]

}
