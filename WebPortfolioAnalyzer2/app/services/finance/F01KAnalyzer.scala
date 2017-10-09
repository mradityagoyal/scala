package services.finance

import java.time.Instant

import model.F01KTransaction

/**
  * Created by agoyal on 5/22/17.
  */
object F01KAnalyzer extends App{

  val path = "resources/401K/401KHistoryAll.csv"

  val transactions: List[F01KTransaction] = F01KTransaction.fromFile(path)

  val contributions: List[F01KTransaction] = transactions.filter(_.transactionType == "CONTRIBUTION")

  println(s"num transactions: ${transactions.size}")

  println(s"num contributions: ${contributions.size}")

  val totalContribution = contributions.map(_.amount).sum

  println(s"Total Contribution: $totalContribution")


  val dividends = transactions.filter(_.transactionType == "DIVIDEND")

  println(s"num dividends: ${dividends.size}")

  val totalDiv = dividends.map(_.amount).sum
  println(s"total dividend value: $totalDiv")

  println(s"Total contribution + dividend: ${totalDiv + totalContribution} ")



  val presentValue = 33426.79

  println(s"Present Value: $presentValue")


  println(s"total Gain or Loss: ${presentValue - totalContribution}")
  println(s"total gain - dividend: ${presentValue - totalContribution - totalDiv}")
  println(s"total gain percentage : ${((presentValue - totalContribution) / totalContribution)*100}%")

  println(s"total gain - dividend percentage : ${((presentValue - totalContribution- totalDiv) / (totalContribution+ totalDiv))*100}%")

  val cashFlow: List[CashFlowEvent]= contributions.map(CashFlowEvent.fromF01KTransaction)
  val irr = TimeValueMoney.irr(presentValue, cashFlow)
  println(s"The calculated irr is ${irr*100}%")

  val fv = TimeValueMoney.future_value(cashFlow, Instant.now, irr)

  println(s"fv as per the calculated irr: $fv")

//  val grouped: Map[String, List[F01KTransaction]] = transactions.groupBy(_.investment)
//
//  val symbols = grouped.keys
//  println(s"All investments: ${symbols.mkString(" , ")}")
//
//  val holdings = grouped.mapValues(transactions => transactions.map(_.shares).sum)
//
//  val viiixTransactions = transactions.filter(_.investment == "VANG INST INDEX PLUS")
//  val viiixCashFlow = viiixTransactions.map(CashFlowEvent.fromF01KTransaction)
//
//  val totalContrViiix = viiixTransactions.map(_.amount).sum
//  println(s"cost basis viiix: $totalContrViiix")
//  println(s"current holding viiix: ${viiixTransactions.map(_.shares).sum}")
//
//  val pvViiix = 12470.20
//
//  val irrViiix = TimeValueMoney.irr(pvViiix, viiixCashFlow, Instant.now())
//
//  println(s"viiix total gain: ${pvViiix - totalContrViiix}")
//
//  println(s"viiix gain percent: ${((pvViiix -totalContrViiix)/totalContrViiix) * 100}%")
//
//  println(s"irr of VIIIX: ${irrViiix*100}% ")
//  for((k,v) <- holdings){
//    println(s"$k  :   $v")
//  }
//
//  val futValViiix = TimeValueMoney.future_value(viiixCashFlow, Instant.now, irrViiix)
//
//  println(s"fv of VIIIX as per the calculated irr: $futValViiix")

}
