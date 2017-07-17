package services.finance

import java.time.{Duration, Instant}

import scala.collection.SortedSet
import scala.collection.immutable.NumericRange


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
    future_value(cashFlows.map(cf => mapToPNPair(cf, futureDate)), r)
  }

  /**
    *
    * @param evt        The cash flow event.
    * @param futureDate . the future date.
    * @param r          rate of return.
    * @return future value of amount invested at rate r.(compounded every 365 days)
    */
  def future_value(evt: CashFlowEvent, futureDate: Instant, r: Double): Double = {
    require(!futureDate.isBefore(evt.time))
    future_value(mapToPNPair(evt, futureDate), r)
  }

  def future_value(pnPair: PNPair, r: Double): Double = pnPair match {
    case PNPair(p,n) => p * scala.math.pow(1 + r, n)
  }

  /** returns (1+r)^n  **/
  def compoundFactor(r: Double, n: Double): Double = scala.math.pow((1 + r), n)



  def future_value(pnPairs: List[PNPair], r: Double): Double = pnPairs.map(future_value(_, r)).sum

  case class PNPair(p: Double, n: Double)

  /**returns absolute number of days between to instants **/
  def getDaysBetween(start: Instant, end: Instant): Long = Duration.between(start, end).toDays

  def mapToPNPair(evt: CashFlowEvent, futureDate: Instant): PNPair = PNPair(evt.amount, getDaysBetween(evt.time, futureDate)/ 365.0)

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
    val pnPairs : List[PNPair] = cashFlow.map(mapToPNPair(_, t ))
    var calculatedValue: Double = future_value(pnPairs,  guess)

    while (!isGuessAcceptable(calculatedValue, presentValue)) {
      val decreaseRate = calculatedValue > presentValue
      val (nextGuess: Double, newRateBoundary: RateBoundary) = getNextGuess(guess, decreaseRate, rateBoundary)
      guess = nextGuess
      rateBoundary = newRateBoundary
      calculatedValue = future_value(pnPairs, guess)
    }
    guess
  }

  //this method does not work correctly. left mid way.
  def irr_avgMonthly(presentValue: Double, cashFlow: List[CashFlowEvent], t: Instant = Instant.now): Double = {

    val costBasis: Double = cashFlow.map(_.amount).sum
    val isRatePositive: Boolean = presentValue >= costBasis
    val oldestEvt = cashFlow.minBy(_.time)
    val maxMonths: Long = Duration.between(oldestEvt.time, t).toDays / 30
    //list of (amount, months it was invested for).
    val pnPairs : List[(Double, Long)] = cashFlow.map(evt => (evt.amount, getDaysBetween(evt.time, t)/30))

    var guess = getSeedRate(isRatePositive)
    var rateBoundary = initializeRateBoundary(isRatePositive)
    // a map of months -> compoundFactor.. for all months from 0 to maxMonths at rate r.

    def calculateValue(rate: Double): Double = {
      val compoundFactorTable = monthlyCompoundFactorTableForRateR(rate)
      pnPairs.map{case (p: Double, months: Long) =>{
        p * compoundFactorTable.getOrElse(months, {println("*** Else block")
          scala.math.pow((1+rate), months)})
      }}.sum
    }

    def monthlyCompoundFactorTableForRateR(rate: Double): Map[Long, Double] = {
      val allMonths = 0L to(maxMonths)
      val allCompoundFactors = allMonths.scanLeft(1.0)((l, _) => l * (1+rate) )
      (allMonths zip allCompoundFactors).toMap
    }

    var result = calculateValue(guess)
    while (!isGuessAcceptable(result, presentValue)) {
      val decreaseRate = result > presentValue
      val (nextGuess: Double, newRateBoundary: RateBoundary) = getNextGuess(guess, decreaseRate, rateBoundary)
      guess = nextGuess
      rateBoundary = newRateBoundary
      result = calculateValue(guess)
    }
    scala.math.pow((1 + guess), 1) - 1
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
    val diff: Double = calculated - expected
    val delta: Double = scala.math.abs(diff / expected)
    delta < 0.0001
  }

}
