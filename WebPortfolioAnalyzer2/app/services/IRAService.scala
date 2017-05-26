package services

import javax.inject.Singleton

import model.IRATransaction
import services.finance.CashFlowEvent

/**
  * Created by agoyal on 5/26/17.
  */

trait IRAService {
    def transactions: List[IRATransaction]

    def contributions: List[IRATransaction]

    def totalContribution: Double

  def totalDividend : Double

    def cashFlow: List[CashFlowEvent]

}

@Singleton
class IRAFileService extends IRAService{
  private val path = "resources/roth/AddyRoth-19May2016To30Mar2017.csv"

  override def  transactions: List[IRATransaction] = IRATransaction.fromFile(path)

  override def contributions: List[IRATransaction] = transactions.filter(contributionFilter)

  override def totalContribution = contributions.map(t => t.amount.getOrElse(0.0)).sum

  override def totalDividend = transactions.filter(dividendFilder).map(t => t.amount.getOrElse(0.0)).sum

  override def cashFlow: List[CashFlowEvent]= contributions.map(CashFlowEvent.fromRothTransaction)

  private def dividendFilder(transaction: IRATransaction): Boolean = transaction.action startsWith "DIVIDEND"

  private def contributionFilter(transaction: IRATransaction): Boolean = transaction.action startsWith "CASH CONTRIBUTION"
}
