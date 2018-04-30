package services

import model.{FidelityTransaction, TransactionRow, TransactionsDAO}

object Startup extends App {

  val addyRothPath = "resources/roth/ROTH_ALL.csv"
  val addyIRAPath = "resources/roth/Addy_IRA.csv"
  val addyF01KPath = "resources/401K/401KHistoryAll.csv"
  val ragsRothPath = "resources/roth/Rags_ROTH_ALL.csv"

  val allTransactions: Seq[FidelityTransaction] =
    Seq(addyF01KPath, addyIRAPath, addyRothPath).flatMap(FidelityHistoryFileParser.readFile(_, Addy())) ++
    FidelityHistoryFileParser.readFile(ragsRothPath, Ragini())

  //create table.
  TransactionsDAO.createTable

  //load data.

  val insertedRecords = TransactionsDAO.insertTransactions(allTransactions map fidelityTransactionToTransactionRow)

  println(s"Inserted $insertedRecords records")


  def fidelityTransactionToTransactionRow(ft: FidelityTransaction): TransactionRow = TransactionRow( 0, ft.symbol, ft.action,
    ft.quantity, ft.price, ft.amount, ft.date, ft.commission, ft.description, ft.accountType, ft.owner

  )

}
