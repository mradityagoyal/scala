package com.goyal.addy.finance.f01K.model

import java.time.Instant

import com.goyal.addy.finance.f01k.F01KAnalyzer
import com.goyal.addy.finance.{CashFlowEvent, TimeValueMoney}
import com.goyal.addy.finance.f01k.model.F01KTransaction
import org.scalatest.{FlatSpec, Matchers}

/**
  * Created by addy on 5/22/17.
  */
class F01KTransactionSpec extends FlatSpec with Matchers {

  "F01K Transaction file " should "be read correctly " in {
    val path = "resources/401K/401KHistory1May2015to18May2017.csv"
    val transactions: List[F01KTransaction] = F01KTransaction.fromFile(path)

    transactions.size should ===(138)


    val contributions: List[F01KTransaction] = transactions.filter(_.transactionType == "CONTRIBUTION")


    contributions.size should ===(84)

    val totalContribution = contributions.map(_.amount).sum
    totalContribution should ===(24301.99)


    val dividends = transactions.filter(_.transactionType == "DIVIDEND")

    dividends.size should ===(24)

    dividends.map(_.amount).sum should ===(602.53)


    val presentValue = 27849.25

    val cashFlow: List[CashFlowEvent] = contributions.map(CashFlowEvent.fromF01KTransaction)
    val irr = TimeValueMoney.irr(presentValue, cashFlow)

    val expectedIrr = 0.1293
//    println(s"irr calculated is $irr")
    assert(scala.math.abs((irr - expectedIrr) / expectedIrr) < 0.001)

    val fv = TimeValueMoney.future_value(cashFlow, Instant.now, irr)

    assert(scala.math.abs((fv - presentValue) / presentValue) < 0.01)

    val grouped: Map[String, List[F01KTransaction]] = transactions.groupBy(_.investment)

  }

}
