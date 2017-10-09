package services.finance

import java.time.{Duration, Instant}
import java.time.temporal.TemporalUnit

import model.IRATransaction

/**
  * Created by agoyal on 5/22/17.
  */
object IRAAnalyzer extends App{
  val path = "resources/roth/ROTH_ALL.csv"
  val transactions : List[IRATransaction] = IRATransaction.fromFile(path)

  val contributions: List[IRATransaction] = transactions.filter(contributionFilter)

  println(s"num transactions: ${transactions.size}")

  println(s"num contributions: ${contributions.size}")

  val totalContribution = contributions.map(t => t.amount.getOrElse(0.0)).sum
  println(s"Total contributions: $totalContribution")

  val totalDividend = transactions.filter(dividendFilder).map(t => t.amount.getOrElse(0.0)).sum

  println(s"Total dividend = $totalDividend")


  val presentValue : Double = 9902 //TODO put the current value of the Portfolio.

  println(s"present value: $presentValue")

  println(s"total Gain or Loss: ${presentValue - totalContribution}")
  println(s"total gain percentage : ${((presentValue - totalContribution) / totalContribution)*100}%")

  val cashFlow: List[CashFlowEvent]= contributions.map(CashFlowEvent.fromRothTransaction)
  val irr = TimeValueMoney.irr(presentValue, cashFlow)

  println(s"The calculated irr is ${irr*100}%")

  val nextYr = Instant.now().plus(Duration.ofDays(365))
  val irrNextYr = TimeValueMoney.irr(presentValue, cashFlow, nextYr)

  println(s"The irr if same present value stayed for a year is ${irrNextYr*100}%")



//  val irr_monthlyAvg = TimeValueMoney.irr_avgMonthly(presentValue, cashFlow)
//  println(s"The irr when averaged monthly with new algo: ${irr_monthlyAvg*100}")

  val fv = TimeValueMoney.future_value(cashFlow, Instant.now, irr)

  println(s"fv as per the calculated irr: $fv")

//  val grouped = transactions.groupBy(_.)






  def dividendFilder(transaction: IRATransaction): Boolean = transaction.action startsWith "DIVIDEND"
  def contributionFilter(transaction: IRATransaction): Boolean = transaction.action startsWith "CASH CONTRIBUTION"




}
