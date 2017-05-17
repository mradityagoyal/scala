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
    cashFlows.par.map(evt => {
      require(futureDate.isAfter(evt.time)) //doesnt work when cash flow continues after the future date.
      val n = Duration.between(evt.time, futureDate).toDays
      val discFactor: Double = scala.math.pow(1 + r, n / 365)
      evt.amount * discFactor
    }).sum
  }


  def irr(fv: Double, cashFlow: List[CashFlowEvent]): Double = {
    val acceptableError = 0.1
    var guess = 0.03
    var calculatedVal = 0d
    var delta = 0d
    do {
      guess = guess + 0.005
      calculatedVal = TimeValueMoney.fv(cashFlow, Instant.now(), guess)
      delta = (fv -calculatedVal)
    }while(delta > acceptableError)
    guess
  }

}
