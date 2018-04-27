package model

import java.time.LocalDate
import java.time.format.DateTimeFormatter

import play.api.libs.json.Json

import scala.io.Source
import scala.util.{Failure, Success, Try}

sealed trait FidelityTransaction {
  def amount : Option[Double]
  def action : String
  def date : Option[LocalDate]
  def ticker: String;
  def numShares: Option[Double]
}

case class F01KTransaction(date: Option[LocalDate], investment: String, transactionType: String, amount:Option[Double], shares: Double) extends FidelityTransaction{

  override def action: String = transactionType

  override def ticker: String = investment

  override def numShares: Option[Double] = Some(shares)
}

object F01KTransaction {
  val dtFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy")

  def parseLine(line: String): Try[F01KTransaction] = {
    try{
      val Array(strDt, investment, transactionType, strAmount, strShares ) = line.split(",").map(_.trim)
      val date = LocalDate.parse(strDt, dtFormat)
      val amt = strAmount.toDouble
      val shares = strShares.toDouble
      Success(F01KTransaction(Some(date), investment, transactionType, Some(amt), shares))
    }catch{
      case e: Exception => Failure(e)
    }

  }

  def fromFile(path: String): List[F01KTransaction] = {
    val lines = Source.fromFile(path).getLines()
    val trans = lines.map(parseLine)
    trans.collect{case Success(t)=>t}.toList //collect lines that were parsed successfully.
  }
}

case class IRATransaction(runDate: Option[LocalDate],
                          action: String,
                          symbol: String,
                          description: String,
                          securityType: String,
                          quantity: Option[Double],
                          price: Option[Double],
                          commission: Option[Double],
                          fee: Option[Double],
                          accruedInterest: Option[Double],
                          amount: Option[Double],
                          settlementDate: Option[LocalDate]
                         ) extends FidelityTransaction{
  override def date = runDate;

  override def ticker: String = symbol

  override def numShares: Option[Double] = quantity
}

object IRATransaction {

  implicit val jsonFormat = Json.format[IRATransaction]

  def parseLine(line: String): Try[IRATransaction] = {
    val dtFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy")
    try {
      val values: Array[String] = line.split(",").map(_.trim)
      if(values.size < 11 || values.size > 12){
        throw new NumberFormatException(s"the line does not represent a transaction. /n $line")
      }
      val Array(runDt, action, symbol, description, secType, qty, strPrice, cmsn, fs, straccruedInterest, amt) = values.take(11)
      val runDate = Some(LocalDate.parse(runDt, dtFormat))
      val quantity = if (qty.isEmpty) None else Some(qty.toDouble)
      val price = if (strPrice.isEmpty) None else Some(strPrice.toDouble)
      val commission = if (cmsn.isEmpty) None else Some(cmsn.toDouble)
      val fee = if(fs.isEmpty) None else Some(fs.toDouble)
      val accruedInterest = if (straccruedInterest.isEmpty) None else Some(straccruedInterest.toDouble)
      val amount = if(amt.isEmpty) None else Some(amt.toDouble)
      val settlementDate: Option[LocalDate] = if(values.size == 13 && !values(12).isEmpty)  Some(LocalDate.parse(values(12),  dtFormat)) else None

      val row = IRATransaction(runDate = runDate,
        action = action,
        symbol = symbol,
        description = description,
        securityType = secType,
        quantity = quantity,
        price = price,
        commission = commission,
        fee = fee,
        accruedInterest = accruedInterest,
        amount = amount,
        settlementDate = settlementDate
      )
      Success(row)
    } catch {
      case e: Exception => {
        //        println(s"error $e , line $line")
        Failure(e)
      }
    }
  }

  def fromFile(path: String): List[IRATransaction] = {
    val src = Source.fromFile(path)
    val lines = src.getLines()
    val trans  = lines.map(parseLine)
    trans.collect{case Success(t)=>t}.toList //collect lines that were parsed successfully.
  }
}
