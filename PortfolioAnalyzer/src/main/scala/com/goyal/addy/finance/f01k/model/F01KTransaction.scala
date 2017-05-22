package com.goyal.addy.finance.f01k.model

import java.time.LocalDate
import java.time.format.DateTimeFormatter

import scala.util.{Failure, Success, Try}

/**
  * Created by agoyal on 5/22/17.
  */
case class F01KTransaction(date: LocalDate, investment: String, transactionType: String, amount:Double, shares: Double)

object F01KTransaction{
  val dtFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy")

  def parse(line: String): Try[F01KTransaction] = {
    try{
      val Array(strDt, investment, transactionType, strAmount, strShares ) = line.split(",").map(_.trim)
      val date = LocalDate.parse(strDt, dtFormat)
      val amt = strAmount.toDouble
      val shares = strShares.toDouble
      Success(F01KTransaction(date, investment, transactionType, amt, shares))
    }catch{
      case e: Exception => Failure(e)
    }

  }
}
