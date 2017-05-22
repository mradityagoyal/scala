package com.goyal.addy.finance

import java.time.{Duration, Instant, LocalDateTime, Period}


/**
  * Created by agoyal on 5/17/17.
  */
object TimeValueMoney {

  /**
    *
    * @param cashFlows  the list of cash flow envents.
    * @param futureDate the Instant in future
    * @param r          rate of return.
    * @return future value of cashflows @ time = futureDate, and @ rate = r
    */
  def future_value(cashFlows: List[CashFlowEvent], futureDate: Instant, r: Double): Double = {
    cashFlows.map(future_value(_, futureDate, r)).sum
  }

  /**
    *
    * @param evt        The cash flow event.
    * @param futureDate . the future date.
    * @param r          rate of return.
    * @return future value of amount invested at rate r.(compounded every 365 days)
    */
  def future_value(evt: CashFlowEvent, futureDate: Instant, r: Double): Double = {
    require(futureDate.isAfter(evt.time))
    val numDays: Long = Duration.between(evt.time, futureDate).toDays
    val discountFactor = scala.math.pow(1 + r, numDays / 365.0)
    evt.amount * discountFactor
  }


  case class RateBoundary(maxRate: Option[Double], minRate: Option[Double], isPositive: Boolean)

  /**
    *
    * @param presentValue The present value of the portfolio.
    * @param cashFlow The investments made in the portfolio.
    * @param t Time when we want to calculate IRR. (Require, all cash flow to happen before t)
    * @return The IRR such that @presentValue =  sum of future values of the transactions in the cash flow, invested at IRR. FV calculated at time t.
    */
  def irr(presentValue: Double, cashFlow: List[CashFlowEvent], t: Instant = Instant.now): Double = {
    val costBasis: Double = cashFlow.map(_.amount).sum
    val isRatePositive: Boolean = presentValue >= costBasis

    var guess = getSeedRate(isRatePositive)
    var rateBoundary = initializeRateBoundary(isRatePositive)
    var calculatedValue: Double = future_value(cashFlow, t, guess)

    while (!isGuessAcceptable(calculatedValue, presentValue)) {
      val decreaseRate = calculatedValue > presentValue
      val (nextGuess: Double, newRateBoundary: RateBoundary) = getNextGuess(guess, decreaseRate, rateBoundary)
      guess = nextGuess
      rateBoundary = newRateBoundary
      calculatedValue = future_value(cashFlow, t, guess)
    }
    guess
  }


  /** returns the initial range of rates. **/
  def initializeRateBoundary(rateIsPositive: Boolean): RateBoundary = {
    val minRate : Option[Double] = if (rateIsPositive) Some(0) else None // if rate is positive.. minRate is zero.
    val maxRate : Option[Double] = if(rateIsPositive) None else Some(0) // if rate is negetive max rate is zero.
    RateBoundary(maxRate = maxRate, minRate = minRate, rateIsPositive)
  }

  //returns + 1% or -1% initial seed rate.
  def getSeedRate(rateIsPositive: Boolean): Double = if (rateIsPositive) 0.01 else -0.01

  /**
    *
    * @param guess current guess
    * @param decreaseRate should the method decrease the rate or increase the rate.
    * @param rateBoundary the max and min bound
    * @return the next guess.
    */
  def getNextGuess(guess: Double, decreaseRate: Boolean, rateBoundary: RateBoundary): (Double, RateBoundary) = {
    if (decreaseRate) {
      //guess was too large
      //set upper bound
      val newMax = Some(scala.math.min(rateBoundary.maxRate.getOrElse(Double.PositiveInfinity), guess))
      val newRateBoundary: RateBoundary = RateBoundary(maxRate = newMax, minRate = rateBoundary.minRate, isPositive = rateBoundary.isPositive)
      val nextGuess = newRateBoundary.minRate match {
        case Some(min) => (guess + min) / 2
        case None => if (guess >= 0) guess / 2 else guess * 2
      }
      (nextGuess, newRateBoundary)
    } else {
      //guess was too small
      val newMin = Some(scala.math.max(rateBoundary.minRate.getOrElse(Double.NegativeInfinity), guess))
      val newRateInfo = RateBoundary(maxRate = rateBoundary.maxRate, minRate = newMin, isPositive = rateBoundary.isPositive)
      val nextGuess = newRateInfo.maxRate match {
        case Some(max) => (guess + max) / 2
        case None => if (guess >= 0) guess * 2 else guess / 2
      }
      (nextGuess, newRateInfo)
    }
  }

  /**returns true if delta is less than 0.01% **/
  def isGuessAcceptable(calculated: Double, expected: Double): Boolean = {
    val diff: Double = scala.math.abs(calculated - expected)
    val delta: Double = diff / expected
    delta < 0.0001
  }

}
