package services.finance

import java.time.{Duration, Instant}

import model.{F01KTransaction, FidelityTransaction, IRATransaction}

/**
  * Created by agoyal on 5/22/17.
  */
object PortfolioAnalyzer extends App{
  val pathAddyRoth = "resources/roth/ROTH_ALL.csv"
  val pathRagsRoth = "resources/roth/Rags_ROTH_ALL.csv"
  val pathAddyIRA = "resources/roth/Addy_IRA.csv"
  val pathAddy401 = "resources/401K/401KHistoryAll.csv"

  //if wanted.. flag each one as ira or roth.
  val transactionsAddyIRA : List[FidelityTransaction] = IRATransaction.fromFile(pathAddyRoth)  ::: IRATransaction.fromFile(pathAddyIRA)
  val transactionsRags : List[FidelityTransaction] = IRATransaction.fromFile(pathRagsRoth)
  val transactionsAddy401: List[FidelityTransaction] = F01KTransaction.fromFile(pathAddy401) //noMore transactions here.
  val transactionsAddy = transactionsAddy401 ::: transactionsAddyIRA

  val addyRothPresentValue: Double = 18112.96
  val addyIRAPresentValue: Double = 29222.22
  val ragsRothPresentValue: Double = 5809.92

  //TODO put the current value of the Portfolio.
  val presentValue : Double = addyRothPresentValue + addyIRAPresentValue + ragsRothPresentValue




  val transactions : List[FidelityTransaction] = transactionsAddy ::: transactionsRags

  val contributions: List[FidelityTransaction] = transactions.filter(contributionFilter)

  println(s"num transactions: ${transactions.size}")

  println(s"num contributions: ${contributions.size}")

  println(s"total Rags Contributions: ${transactionsRags.filter(contributionFilter).map(t => t.amount.getOrElse(0.0)).sum}")
  println(s"total Addy Contributions: ${transactionsAddy.filter(contributionFilter).map(t => t.amount.getOrElse(0.0)).sum}")
  println(s"Addy IRA Contributions: ${transactionsAddyIRA.filter(contributionFilter).map(t => t.amount.getOrElse(0.0)).sum} , (does not include ROLLOVER). ")
  println(s"Addy 401K Contributions: ${transactionsAddy401.filter(contributionFilter).map(t => t.amount.getOrElse(0.0)).sum} ")
  val totalContribution = contributions.map(t => t.amount.getOrElse(0.0)).sum
  println(s"Total contributions: $totalContribution")

  val totalDividend = transactions.filter(dividendFilder).map(t => t.amount.getOrElse(0.0)).sum

  println(s"Total dividend = $totalDividend")



  println(s"present value: $presentValue")

  println(s"total Gain or Loss: ${presentValue - totalContribution}")
  println(s"total gain percentage : ${((presentValue - totalContribution) / totalContribution)*100}%")

  val cashFlow: List[CashFlowEvent]= contributions.map(CashFlowEvent.fromFidelityTransaction)
  val irr = TimeValueMoney.irr(presentValue, cashFlow)

  println(s"The calculated irr is ${irr*100}%")

  val nextYr = Instant.now().plus(Duration.ofDays(365))
  val irrNextYr = TimeValueMoney.irr(presentValue, cashFlow, nextYr)

  println(s"The irr if same present value stayed for a year is ${irrNextYr*100}%")
  println(s"Portfolio value if you maintain ${irr*100}% return for one more year ${presentValue * (1+irr)}")

  println(s"Dream on: if you maintain this rate of return for 5 years you will have ${presentValue * Math.pow((1+irr), 5)}")
  println(s"No way this will happen: if you maintain this rate of return for 10 years you will have ${presentValue * Math.pow((1+irr), 10)}")



//  val irr_monthlyAvg = TimeValueMoney.irr_avgMonthly(presentValue, cashFlow)
//  println(s"The irr when averaged monthly with new algo: ${irr_monthlyAvg*100}")

  val fv = TimeValueMoney.future_value(cashFlow, Instant.now, irr)

  println(s"Debug - fv as per the calculated irr: $fv")

//  val grouped = transactions.groupBy(_.)






  def dividendFilder(transaction: FidelityTransaction): Boolean = transaction.action contains "DIVIDEND"
  def contributionFilter(transaction: FidelityTransaction): Boolean = (transaction.action contains "CONTRIBUTION")




}
