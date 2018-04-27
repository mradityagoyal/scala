package finance

import java.time.Instant

import model.F01KTransaction
import org.scalatest.{FlatSpec, Matchers}
import services.finance.{CashFlowEvent, TimeValueMoney}

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

    val totalContribution = contributions.map(_.amount.get).sum
    totalContribution should ===(24301.99)


    val dividends = transactions.filter(_.transactionType == "DIVIDEND")

    dividends.size should ===(24)

    dividends.map(_.amount.get).sum should ===(602.53)


    val presentValue = 27849.25

    val cashFlow: List[CashFlowEvent] = contributions.map(CashFlowEvent.fromFidelityTransaction)
    val irr = TimeValueMoney.irr(presentValue, cashFlow, Instant.parse("2017-05-22T00:00:00.00Z"))

    val fv = TimeValueMoney.future_value(cashFlow, Instant.now, irr)

    assert(scala.math.abs((fv - presentValue) / presentValue) < 0.01)

    val grouped: Map[String, List[F01KTransaction]] = transactions.groupBy(_.investment)

  }

}
