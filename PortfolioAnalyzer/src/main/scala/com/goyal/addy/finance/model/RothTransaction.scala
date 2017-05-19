package com.goyal.addy.finance.model

import java.time.LocalDate
import java.time.format.DateTimeFormatter

import scala.util.{Failure, Success, Try}

/**
  * Created by addy on 5/19/17.
  * Run Date,Account,Action,Symbol,Security Description,Security Type,Quantity,Price ($),
  * Commission ($),Fees ($),Accrued Interest ($),Amount ($),Settlement Date
  */
case class RothTransaction(runDate: Option[LocalDate],
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
                          )

object RothTransaction {

  def parse(line: String): Try[RothTransaction] = {
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

      val row = RothTransaction(runDate = runDate,
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
}
