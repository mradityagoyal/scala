package model

import java.sql
import java.text.SimpleDateFormat
import java.util.Date

import play.api.libs.json.{Json, OFormat}
import services._

import scala.io.Source
import scala.util.{Failure, Success, Try}

sealed trait FidelityTransaction {
  def symbol: String

  def action: String

  def quantity: Double

  def price: Option[Double]

  def amount: Double

  def date: java.util.Date

  def commission: Double

  def description: String

  def accountType: AccountType

  def owner: AccountOwner

}

object FidelityTransaction {
  def toTransactionsRow(ft: FidelityTransaction): TransactionsRow = TransactionsRow(
    0, Some(ft.symbol), ft.action, Some(BigDecimal.valueOf(ft.quantity)), ft.price.map(BigDecimal.valueOf), BigDecimal.valueOf(ft.amount),
    new sql.Date(ft.date.getTime), BigDecimal.valueOf(ft.commission), Some(ft.description), ft.accountType, ft.owner
  )
}

//case class TransactionsRow(id: Long, symbol: Option[String], action: String, quantity: Option[scala.math.BigDecimal],
//                           price: Option[scala.math.BigDecimal], amount: scala.math.BigDecimal, date: java.sql.Date,
//                           commission: scala.math.BigDecimal, description: Option[String], accountType: AccountType, owner: AccountOwner)

case class F01KTransaction(date: java.util.Date,
                           symbol: String,
                           action: String,
                           amount: Double,
                           quantity: Double) extends FidelityTransaction {

  override def commission: Double = 0.0 //commission is always zero in 401K transactions

  override def price: Option[Double] = quantity match {
    case 0 => None
    case _ => Some(amount / quantity)
  }

  override def description = "" // always none in 401K

  override def accountType = F01K()

  override def owner = Addy() //always addy
}

object F01KTransaction {
  val dtFormat = new SimpleDateFormat("MM/dd/yyyy")

  def parseLine(line: String): Try[F01KTransaction] = {
    try {
      val Array(strDt, investment, transactionType, strAmount, strShares) = line.split(",").map(_.trim)
      val date = dtFormat.parse(strDt)
      val amt = strAmount.toDouble
      val shares = strShares.toDouble
      Success(F01KTransaction(date, investment, transactionType, amt, shares))
    } catch {
      case e: Exception => Failure(e)
    }

  }

  @deprecated
  def fromFile(path: String): List[F01KTransaction] = {
    val lines = Source.fromFile(path).getLines()
    val trans = lines.map(parseLine)
    trans.collect { case Success(t) => t }.toList //collect lines that were parsed successfully.
  }
}

case class IRATransaction(date: java.util.Date,
                          action: String,
                          symbol: String,
                          description: String,
                          securityType: String,
                          quantity: Double,
                          price: Option[Double],
                          commission: Double,
                          fee: Option[Double],
                          accruedInterest: Option[Double],
                          amount: Double,
                          settlementDate: Option[Date],
                          owner: AccountOwner
                         ) extends FidelityTransaction{
  override def accountType: AccountType = ROTH()
}

object IRATransaction {


  def parseLine(line: String, owner:AccountOwner = Addy()): Try[IRATransaction] = {
    val dtFormat = new SimpleDateFormat("MM/dd/yyyy")
    try {
      val values: Array[String] = line.split(",").map(_.trim)
      if (values.length < 11 || values.size > 12) {
        throw new NumberFormatException(s"the line does not represent a transaction. /n $line")
      }
      val Array(runDt, action, symbol, description, secType, qty, strPrice, cmsn, fs, straccruedInterest, amt) = values.take(11)
      val date = dtFormat.parse(runDt)
      val quantity = if (qty.isEmpty) 0 else qty.toDouble
      val price = if (strPrice.isEmpty) None else Some(strPrice.toDouble)
      val commission = if (cmsn.isEmpty) 0.0 else cmsn.toDouble
      val fee = if (fs.isEmpty) None else Some(fs.toDouble)
      val accruedInterest = if (straccruedInterest.isEmpty) None else Some(straccruedInterest.toDouble)
      val amount = if (amt.isEmpty) 0 else amt.toDouble
      val settlementDate: Option[Date] = if (values.length == 13 && !values(12).isEmpty) {
        Some(dtFormat.parse(values(12)))
      } else None

      val row = IRATransaction(date, action, symbol, description, secType, quantity, price,
        commission, fee, accruedInterest, amount, settlementDate, owner)
      Success(row)
    } catch {
      case e: Exception => Failure(e)
    }
  }

  @deprecated
  def fromFile(path: String): List[IRATransaction] = {
    val src = Source.fromFile(path)
    val lines = src.getLines()
    val trans = lines.map(parseLine(_, Addy()))
    trans.collect { case Success(t) => t }.toList //collect lines that were parsed successfully.
  }
}
