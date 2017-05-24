package services.finance

import java.time.{Instant, ZoneOffset}

import model.{F01KTransaction, RothTransaction}

/**
  * Created by addy on 5/18/17.
  */
case class CashFlowEvent(amount: Double, time: Instant)

object CashFlowEvent{
  def fromF01KTransaction(transaction: F01KTransaction): CashFlowEvent = CashFlowEvent(transaction.amount, transaction.date.atStartOfDay.toInstant(ZoneOffset.UTC))

  def fromRothTransaction(transaction: RothTransaction): CashFlowEvent = {
    CashFlowEvent(transaction.amount.getOrElse(0), transaction.runDate.get.atStartOfDay().toInstant(ZoneOffset.UTC))
  }
}
