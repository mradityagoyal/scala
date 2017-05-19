package com.goyal.addy.finance.model

import org.scalatest.{FlatSpec, Matchers}

/**
  * Created by addy on 5/19/17.
  */
class RothHistoryCsvParserSpec extends FlatSpec with Matchers{

  "CSV parser " should "parse History-27Feb17-15May17.csv correctly  " in {
    val path = "resources/History-27Feb17-15May17.csv"
    val trans = RothHistoryCsvParser.readFile(path)
    trans.length should ===(20) //expect 20 transactons in 27thFeb to 15may history.
  }
  it should "parse AddyRoth-19May2016To30Mar2017.csv file correctly in  " in {
    val path = "resources/roth/AddyRoth-19May2016To30Mar2017.csv"
    val transactions = RothHistoryCsvParser.readFile(path)
    transactions.length should ===(35)
  }



}
