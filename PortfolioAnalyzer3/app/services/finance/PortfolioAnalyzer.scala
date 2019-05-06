package services.finance

import java.text.NumberFormat
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

  val addyRothPresentValue: Double = 18234
  val addyIRAPresentValue: Double = 21924
  val ragsRothPresentValue: Double = 4883

  //TODO put the current value of the Portfolio.
  val presentValue : Double = addyRothPresentValue + addyIRAPresentValue + ragsRothPresentValue




  val transactions : List[FidelityTransaction] = transactionsAddy ::: transactionsRags

  val contributions: List[FidelityTransaction] = transactions.filter(contributionFilter)

  println(s"num transactions: ${transactions.size}")

  println(s"num contributions: ${contributions.size}")

  val formatter: NumberFormat = NumberFormat.getCurrencyInstance

  println(s"total Rags Contributions: ${formatter.format(transactionsRags.filter(contributionFilter).map(t => t.amount).sum)}")
  println(s"total Addy Contributions: ${formatter.format(transactionsAddy.filter(contributionFilter).map(t => t.amount).sum)}")
  println(s"Addy IRA Contributions: ${formatter.format(transactionsAddyIRA.filter(contributionFilter).map(t => t.amount).sum)} , (does not include ROLLOVER). ")
  println(s"Addy 401K Contributions: ${formatter.format(transactionsAddy401.filter(contributionFilter).map(t => t.amount).sum)} ")
  val totalContribution = contributions.map(t => t.amount).sum
  println(s"Total contributions: ${formatter.format(totalContribution)}")

  val totalDividend = transactions.filter(dividendFilder).map(t => t.amount).sum

  println(s"Total dividend = ${formatter.format(totalDividend)}")



  println(s"present value: ${formatter.format(presentValue)}")

  println(s"total Gain or Loss: ${formatter.format(presentValue - totalContribution)}")
  println(s"total gain percentage : ${((presentValue - totalContribution) / totalContribution)*100}%")

  val cashFlow: List[CashFlowEvent]= contributions.map(CashFlowEvent.fromFidelityTransaction)
  val irr = TimeValueMoney.irr(presentValue, cashFlow)

  println(s"The calculated irr is ${irr*100}%")

  val nextYr = Instant.now().plus(Duration.ofDays(365))
  val irrNextYr = TimeValueMoney.irr(presentValue, cashFlow, nextYr)

  println(s"The irr if same present value stayed for a year is ${irrNextYr*100}%")
  println(s"Portfolio value if you maintain ${irr*100}% return for one more year ${formatter.format(presentValue * (1+irr))}")

  println(s"Dream on: if you maintain this rate of return for 5 years you will have ${formatter.format(presentValue * Math.pow((1+irr), 5))}")
  println(s"No way this will happen: if you maintain this rate of return for 10 years you will have ${formatter.format(presentValue * Math.pow((1+irr), 10))}")



//  val irr_monthlyAvg = TimeValueMoney.irr_avgMonthly(presentValue, cashFlow)
//  println(s"The irr when averaged monthly with new algo: ${irr_monthlyAvg*100}")

  val fv = TimeValueMoney.future_value(cashFlow, Instant.now, irr)

  println(s"Debug - fv as per the calculated irr: ${formatter.format(fv)}")

//  val grouped = transactions.groupBy(_.)






  def dividendFilder(transaction: FidelityTransaction): Boolean = transaction.action contains "DIVIDEND"
  def contributionFilter(transaction: FidelityTransaction): Boolean = (transaction.action contains "CONTRIBUTION")




}
