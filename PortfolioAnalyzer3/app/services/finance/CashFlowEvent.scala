package services.finance

import java.time.Instant

import model.FidelityTransaction

/**
  * Created by addy on 5/18/17.
  */
case class CashFlowEvent(amount: Double, time: Instant)

object CashFlowEvent{
  def fromFidelityTransaction(transaction:FidelityTransaction): CashFlowEvent = CashFlowEvent(transaction.amount, transaction.date.toInstant)
}
