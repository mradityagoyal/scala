package services.finance

import java.time.{Duration, Instant}

/**
  * Created by agoyal on 5/25/17.
  */
object AnnuityTest extends App{

//  val dates: List[Instant] = ???

  val now = Instant.now();
  val oneYr = Duration.ofDays(365)

  val years: List[Duration] = List.fill(5)(oneYr)

  val durations = years.scan(Duration.ZERO)(_.plus(_))

  val instants: List[Instant] = for(dur <- durations)yield now.plus(dur)

  val cashFlows: List[CashFlowEvent] = instants.tail.map(t => CashFlowEvent(125000, t))

  val irr = TimeValueMoney.irr(834325, cashFlows, instants.last)

  val fv = TimeValueMoney.future_value(cashFlows, instants.last, 0.08)

  println(fv)

  println(irr)

}
