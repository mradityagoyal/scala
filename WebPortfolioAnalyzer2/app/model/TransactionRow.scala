package model
import java.util.Date

import scala.reflect.internal.util.Statistics.Quantity

final case class TransactionRow(ID : Long = 0L,
                                symbol: String,
                                action: String,
                                quantity: Double,
                                price: Double,
                                amount: Double,
                                date: java.util.Date,
                                commission: Double,
                                desciption: String)
