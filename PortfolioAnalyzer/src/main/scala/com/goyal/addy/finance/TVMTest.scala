package com.goyal.addy.finance

import java.time.{Instant, LocalDateTime, ZoneOffset}
import javafx.scene.control.SpinnerValueFactory.LocalDateSpinnerValueFactory

import com.goyal.addy.finance.TimeValueMoney.CashFlowEvent

/**
  * Created by agoyal on 5/17/17.
  */
object TVMTest extends App{

  val cashFlows = List(CashFlowEvent(100, Instant.parse("2016-05-17T10:15:30.00Z")))
  val t1 = TimeValueMoney.fv(cashFlows, LocalDateTime.now().toInstant(ZoneOffset.UTC), 0.05)
 // print(t1)


  val r = TimeValueMoney.irr(105, cashFlows)

  println(r)


//  val

}
