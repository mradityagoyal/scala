package model
import java.util.Date

import services.{AccountOwner, AccountType}

import scala.reflect.internal.util.Statistics.Quantity

final case class TransactionRow(ID : Long = 0L,
                                symbol: String,
                                action: String,
                                quantity: Double,
                                price: Option[Double],
                                amount: Double,
                                date: java.util.Date,
                                commission: Option[Double],
                                desciption: String,
                                accountType: AccountType,
                                owner: AccountOwner)
