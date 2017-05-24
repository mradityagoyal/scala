package com.goyal.addy.finance.ira.model

import com.goyal.addy.finance.CashFlowEvent
import com.goyal.addy.finance.ira.model.RothTransaction
import org.scalatest.{FlatSpec, Matchers}

/**
  * Created by addy on 5/19/17.
  */
class RothHistoryCsvParserSpec extends FlatSpec with Matchers{

  "CSV parser " should "parse History-27Feb17-15May17.csv correctly  " in {
    val path = "resources/roth/History-27Feb17-15May17.csv"
    val trans = RothTransaction.fromFile(path)
    trans.length should ===(20) //expect 20 transactons in 27thFeb to 15may history.
  }
  it should "parse AddyRoth-19May2016To30Mar2017.csv file correctly in  " in {
    val path = "resources/roth/AddyRoth-19May2016To30Mar2017.csv"
    val transactions = RothTransaction.fromFile(path)
    transactions.length should ===(48)

    val cf = transactions.map(CashFlowEvent.fromRothTransaction)

    cf.size should ===(48)
  }
}