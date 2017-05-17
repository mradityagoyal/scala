package com.goyal.addy.finance

import java.time.{Duration, Instant, LocalDateTime, Period}


/**
  * Created by agoyal on 5/17/17.
  */
object TimeValueMoney {

  case class CashFlowEvent(amount: Double, time: Instant)


  /**
    *
    * @param cashFlows the list of cash flow envents.
    * @param futureDate the Instant in future
    * @param r rate of return.
    * @return future value of cashflows @ time = futureDate, and @ rate = r
    */
  def fv(cashFlows: List[CashFlowEvent], futureDate: Instant, r: Double): Double = {
    cashFlows.par.map(fv(_, futureDate, r)).sum
  }

  /**
    *
    * @param evt The cash flow event.
    * @param futureDate. the future date.
    * @param r rate of return.
    * @return future value of amount invested at rate r.(compounded every 365 days)
    */
  def fv(evt: CashFlowEvent, futureDate: Instant, r: Double): Double = {
    require(futureDate.isAfter(evt.time))
    val numDays = Duration.between(evt.time, futureDate)
    val discountFactor = scala.math.pow(1+r, numDays/365)
    evt.amount * discountFactor
  }


  def irr(presentValue: Double, cashFlow: List[CashFlowEvent]): Double = {
    val acceptableError = 0.1
    var guess = 0.03
    var delta = 0d
    do {
      guess = guess + 0.005
      delta = (presentValue - fv(cashFlow, Instant.now(), guess))
    }while(delta > acceptableError)
    guess
  }

}
