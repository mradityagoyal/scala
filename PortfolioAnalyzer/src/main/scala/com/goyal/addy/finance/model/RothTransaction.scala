package com.goyal.addy.finance.model

import java.time.LocalDate
import java.time.format.DateTimeFormatter

import scala.util.{Failure, Success, Try}

/**
  * Created by addy on 5/19/17.
  * Run Date,Account,Action,Symbol,Security Description,Security Type,Quantity,Price ($),
  * Commission ($),Fees ($),Accrued Interest ($),Amount ($),Settlement Date
  */
case class RothTransaction(runDate: LocalDate,
                           account: String,
                           action: String,
                           symbol: String,
                           description: String,
                           securityType: String,
                           quantity: Double,
                           price: Double,
                           commission: Double,
                           fee: Double,
                           accruedInterest: Double,
                           amount: Double,
                           settlementDate: Option[LocalDate]
                          )

object RothTransaction {

  def parse(line: String): Try[RothTransaction] = {
    try {
      val values: Array[String] = line.split(",").map(_.trim)

      if(values.size < 12 || values.size > 13){
        throw new NumberFormatException(s"the line does not represent a transaction. /n $line")
      }
      val Array(runDt, account, action, symbol, description, secType, qty, strPrice, cmsn, fs, straccruedInterest, amt) = values.take(12)
      val strSettlementDate = if(values.size == 13) values(12) else ""
      val dtFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy")
      val runDate: LocalDate = LocalDate.parse(runDt, dtFormat)
      val quantity: Double = if (qty.isEmpty) 0 else qty.toDouble
      val price = if (strPrice.isEmpty) 0 else strPrice.toDouble //TODO remove o and add Options
      val commission = if (cmsn.isEmpty) 0 else cmsn.toDouble
      val fee = if(fs.isEmpty) 0 else fs.toDouble
      val accruedInterest = if (straccruedInterest.isEmpty) 0 else straccruedInterest.toDouble
      val amount = if(amt.isEmpty)0 else amt.toDouble
      val settlementDate: Option[LocalDate] = if (strSettlementDate.isEmpty) None else Some(LocalDate.parse(strSettlementDate,  dtFormat))

      val row = RothTransaction(runDate = runDate,
        account = account,
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
        println(s"error $e , line $line")
        Failure(e)
      }
    }
  }
}
