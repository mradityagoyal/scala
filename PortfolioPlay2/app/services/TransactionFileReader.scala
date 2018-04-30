package services

import model.{F01KTransaction, FidelityTransaction, IRATransaction}


object TransactionFileReader  {
  val pathAddyRoth = "resources/roth/ROTH_ALL.csv"
  val pathRagsRoth = "resources/roth/Rags_ROTH_ALL.csv"
  val pathAddyIRA = "resources/roth/Addy_IRA.csv"
  val pathAddy401 = "resources/401K/401KHistoryAll.csv"

  //if wanted.. flag each one as ira or roth.
  val transactionsAddyIRA : List[FidelityTransaction] = IRATransaction.fromFile(pathAddyRoth)  ::: IRATransaction.fromFile(pathAddyIRA)
  val transactionsRags : List[FidelityTransaction] = IRATransaction.fromFile(pathRagsRoth)
  val transactionsAddy401: List[FidelityTransaction] = F01KTransaction.fromFile(pathAddy401) //noMore transactions here.
  val transactionsAddy = transactionsAddy401 ::: transactionsAddyIRA

  val allTransactions = transactionsAddy ::: transactionsRags

  val addyRothPresentValue: Double = 18058
  val addyIRAPresentValue: Double = 29183
  val ragsRothPresentValue: Double = 5787

  //TODO put the current value of the Portfolio.
  val presentValue : Double = addyRothPresentValue + addyIRAPresentValue + ragsRothPresentValue
}
