package com.goyal.addy.finance

import java.time.temporal.TemporalUnit

import org.scalatest.{FlatSpec, Matchers}
import java.time.{Duration, Instant, LocalDate, ZoneOffset}

/**
  * Created by addy on 5/18/17.
  */
class TimeValueMoneySpec extends FlatSpec with Matchers {


  "Time Value Money" should "calculate fv of single transaction correctly " in {
    val now = Instant.now()
    val oneYr = Duration.ofDays(365)
    val pv = 100
    val evt = CashFlowEvent(pv, now.minus(oneYr))
    val r = 0.05
    val fv = TimeValueMoney.fv(evt, now, 0.05)
    fv should ===(105)
  }

  val cashFlow: List[CashFlowEvent] = List(("2001-01-01", 10000), ("2002-01-02", 2000), ("2003-03-15", 2500), ("2004-05-12", 5000), ("2005-08-10", 1000))
    .map { case (date, value) => {
      val timestamp = LocalDate.parse(date).atStartOfDay().toInstant(ZoneOffset.UTC)
      CashFlowEvent(value, timestamp)
    }
    }

  it should "calculate fv of a cash flow correctly " in {

    val r = 0.0328125
    val t = LocalDate.parse("2017-05-17").atStartOfDay().toInstant(ZoneOffset.UTC)
    val fv: Double = TimeValueMoney.fv(cashFlow, t, r)
    val expected = 33000
    val delta: Double = (expected - fv)/ expected
    assert(delta < 0.01)
  }

  it should "calculate irr correctly " in {
    val expctedIrr = 0.0328125
    val t = LocalDate.parse("2017-05-17").atStartOfDay().toInstant(ZoneOffset.UTC)
    val irr = TimeValueMoney.irr(33000, cashFlow, t)
    val delta = (irr - expctedIrr) / expctedIrr
    assert(delta < 0.01)
  }

}
