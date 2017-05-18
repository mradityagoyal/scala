package com.goyal.addy.finance

import java.time._



/**
  * Created by agoyal on 5/17/17.
  */
object TVMTest extends App{


  val cashFlow: List[CashFlowEvent] = List(("2001-01-01",	10000)	, ("2002-01-02",	2000), ("2003-03-15",	2500) ,  ("2004-05-12",	5000), ("2005-08-10",	1000))
    .map{case (date, value) => {
      val timestamp = LocalDate.parse(date).atStartOfDay().toInstant(ZoneOffset.UTC)
      CashFlowEvent(value, timestamp)
    }}

  val pv = 33000
  val r2 = TimeValueMoney.irr(pv, cashFlow, Instant.now())
  println(s"Guessed IRR is ${r2*100} %")

  val reverse = TimeValueMoney.fv(cashFlow, Instant.now(), r2)
  println(s"reverse is $reverse")

  println(s"delta is ${((reverse - pv) / pv)*100} %")

//  val

}
