package com.goyal.addy.finance

import java.time._
import javafx.scene.control.SpinnerValueFactory.LocalDateSpinnerValueFactory

import com.goyal.addy.finance.TimeValueMoney.CashFlowEvent


/**
  * Created by agoyal on 5/17/17.
  */
object TVMTest extends App{

//  val cashFlows = List(CashFlowEvent(100, Instant.parse("2011-05-17T10:15:30.00Z")), CashFlowEvent(100, Instant.parse("2012-05-17T10:15:30.00Z")))
//  val t1 = TimeValueMoney.fv(cashFlows, LocalDateTime.now().toInstant(ZoneOffset.UTC), 0.05)
 // print(t1)


//  val r = TimeValueMoney.irr(220.5, cashFlows, Instant.now())

//  println(r)

  val cashFlow: List[CashFlowEvent] = List(("2001-01-01",	10000)	, ("2002-01-02",	2000), ("2003-03-15",	2500) ,  ("2004-05-12",	5000), ("2005-08-10",	1000))
    .map{case (date, value) => {

      val timestamp = LocalDate.parse(date).atStartOfDay().toInstant(ZoneOffset.UTC)
      CashFlowEvent(value, timestamp)
    }}

  val pv = 29000
  val r2 = TimeValueMoney.irr(pv, cashFlow, Instant.now())
  println(s"Guessed IRR is ${r2*100} %")

  val reverse = cashFlow.map{
    case CashFlowEvent(value, timestamp) => value * scala.math.pow((1 + r2), Duration.between(timestamp, Instant.now()).toDays / 365)
  }.sum

  println(s"reverse is $reverse")

  println(s"delta is ${((reverse - pv) / pv)*100} %")

//  val

}
