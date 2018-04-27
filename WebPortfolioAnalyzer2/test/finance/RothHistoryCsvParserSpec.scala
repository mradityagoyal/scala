package finance

import model.IRATransaction
import org.scalatest.{FlatSpec, Matchers}
import services.finance.CashFlowEvent

/**
  * Created by addy on 5/19/17.
  */
class RothHistoryCsvParserSpec extends FlatSpec with Matchers{

  "CSV parser " should "parse History-27Feb17-15May17.csv correctly  " in {
    val path = "resources/roth/History-27Feb17-15May17.csv"
    val trans = IRATransaction.fromFile(path)
    trans.length should ===(20) //expect 20 transactons in 27thFeb to 15may history.
  }
  it should "parse AddyRoth-19May2016To30Mar2017.csv file correctly in  " in {
    val path = "resources/roth/AddyRoth-19May2016To30Mar2017.csv"
    val transactions = IRATransaction.fromFile(path)
    transactions.length should ===(48)

    val cf = transactions.map(CashFlowEvent.fromFidelityTransaction)

    cf.size should ===(48)
  }
}
