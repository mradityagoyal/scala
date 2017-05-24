package finance

import java.time.{Duration, Instant, LocalDate, ZoneOffset}

import org.scalatest.{FlatSpec, Matchers}
import services.finance.{CashFlowEvent, TimeValueMoney}

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
    val fv = TimeValueMoney.future_value(evt, now, 0.05)
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
    val fv: Double = TimeValueMoney.future_value(cashFlow, t, r)
    val expected = 33000
    val delta: Double = (expected - fv)/ expected
    assert(delta < 0.0001)
  }

  it should "calculate irr correctly " in {
    val expctedIrr = 0.0328125
    val t = LocalDate.parse("2017-05-17").atStartOfDay().toInstant(ZoneOffset.UTC)
    val irr = TimeValueMoney.irr(33000, cashFlow, t)
    val delta = (irr - expctedIrr) / expctedIrr
    assert(delta < 0.0001)
  }

  it should "calculate irr correctly with negetive rate" in {
    val expectedIrr = -0.0469921875
    val t = LocalDate.parse("2017-05-17").atStartOfDay().toInstant(ZoneOffset.UTC)
    val irr = TimeValueMoney.irr(10000, cashFlow, t)
//    println(s"irr is: $irr")
    val delta = scala.math.abs((irr - expectedIrr) / expectedIrr)
    assert(delta < 0.0001)
  }

  it should "calculate irr correctly with default year" in {
    val now = Instant.now
    val cashFlow = List(CashFlowEvent(100, now.minus(Duration.ofDays(365))))
    val pv = 120
    val irr = TimeValueMoney.irr(pv, cashFlow )
    val expected = 0.2
    val delta = scala.math.abs((irr - expected) / expected)
    assert(delta < 0.01)

  }

}
