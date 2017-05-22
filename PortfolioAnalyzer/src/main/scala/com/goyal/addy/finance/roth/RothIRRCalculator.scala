package com.goyal.addy.finance.roth

import java.time.{Instant, ZoneId, ZoneOffset}
import java.util.{SimpleTimeZone, TimeZone}

import com.goyal.addy.finance.{CashFlowEvent, TimeValueMoney}
import com.goyal.addy.finance.model.{RothHistoryCsvParser, RothTransaction}

/**
  * Created by agoyal on 5/22/17.
  */
object RothIRRCalculator extends App{
  val path = "resources/roth/AddyRoth-19May2016To30Mar2017.csv"
  val transactions : List[RothTransaction] = RothHistoryCsvParser.readFile(path)

  val contributions: List[RothTransaction] = transactions.filter(contributionFilter)

  val totalContribution = contributions.map(t => t.amount.getOrElse(0.0)).sum
  println(s"Total contributions: $totalContribution")

  val totalDividend = transactions.filter(dividendFilder).map(t => t.amount.getOrElse(0.0)).sum

  println(s"Total dividend = $totalDividend")


  val presentValue : Double = 6990.90 //TODO put the current value of the Portfolio.

  println(s"present value: $presentValue")

  println(s"total Gain or Loss: ${presentValue - totalContribution}")
  println(s"total gain percentage : ${((presentValue - totalContribution) / totalContribution)*100}%")

  val cashFlow: List[CashFlowEvent]= contributions.map(toCashFlowEvent)
  val irr = TimeValueMoney.irr(presentValue, cashFlow)
  println(s"The calculated irr is ${irr*100}%")

  val fv = TimeValueMoney.future_value(cashFlow, Instant.now, irr)

  println(s"fv as per the calculated irr: $fv")






  def dividendFilder(transaction: RothTransaction): Boolean = transaction.action startsWith "DIVIDEND"
  def contributionFilter(transaction: RothTransaction): Boolean = transaction.action startsWith "CASH CONTRIBUTION"


  def toCashFlowEvent(transaction: RothTransaction): CashFlowEvent = {
    CashFlowEvent(transaction.amount.getOrElse(0), transaction.runDate.get.atStartOfDay().toInstant(ZoneOffset.UTC))
  }

}
