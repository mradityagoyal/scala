package services.finance

import java.time.{Instant, ZoneOffset}

import model.{F01KTransaction, FidelityTransaction, IRATransaction}

/**
  * Created by addy on 5/18/17.
  */
case class CashFlowEvent(amount: Double, time: Instant)

object CashFlowEvent{
  def fromFidelityTransaction(transaction:FidelityTransaction): CashFlowEvent = CashFlowEvent(transaction.amount.get, transaction.date.get.atStartOfDay.toInstant(ZoneOffset.UTC))
}
