package model


import java.sql.Date

import javax.inject.{Inject, Singleton}
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}

/**
 * A repository for people.
 *
 * @param dbConfigProvider The Play db config provider. Play will inject this for you.
 */
@Singleton
class Repository @Inject()(dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) extends Tables {

  override val profile = slick.jdbc.H2Profile
  // We want the JdbcProfile for this provider
  protected val dbConfig = dbConfigProvider.get[JdbcProfile]

  // These imports are important, the first one brings db into scope, which will let you do the actual db operations.
  // The second one brings the Slick DSL into scope, which lets you define the table and other queries.
  import dbConfig._
  import profile.api._
  // NOTE: GetResult mappers for plain SQL are only generated for tables where Slick knows how to map the types of all columns.
  import slick.jdbc.{GetResult => GR}

  /**
   * List all the Transactions in the database.
   */
  def list(): Future[Seq[TransactionsRow]] = db.run {
    Transactions.result
  }


  /**
    *
    * @param symbol ticker of security
    * @param action buy / sell etc
    * @param quantity - num of shares
    * @param price - price per share
    * @param date - run Date
    * @return
    */
  def create(symbol: String, action: String, quantity: Long, price: Long, date: java.util.Date): Future[TransactionsRow] = db.run {
    // We create a projection of just the ticker, numShares and action columns, since we're not inserting a value for the id column
    (Transactions.map(t => (t.symbol, t.action, t.quantity, t.price, t.date))
      // Now define it to return the id, because we want to know what id was generated for the person
      returning Transactions.map(_.id)
      // And we define a transformation for the returned value, which combines our original parameters with the
      // returned id
      into ((values, id) => TransactionsRow(id, values._1, values._2, values._3, values._4, values._5, None, None, None, None, None, None, None)
      // And finally, insert the person into the database
      ) += (Some(symbol), Some(action), Some(quantity), Some(price), Some(new java.sql.Date(date.getTime))))
  }

  def insertAll(transactions: Seq[FidelityTransaction]) = db.run {
    val dbTransactions: Seq[TransactionsRow] = transactions map toTransactionsRow
    val toBeInserted = dbTransactions.map{ row => Transactions.insertOrUpdate(row)}
    val inOneGo = DBIO.sequence(toBeInserted)
    inOneGo
  }

  def toTransactionsRow(ft: FidelityTransaction): TransactionsRow = ft match {
    case t : F01KTransaction => toTransactionsRow(t)
    case t :IRATransaction => toTransactionsRow(t)
  }

  def toTransactionsRow(t: F01KTransaction) : TransactionsRow = TransactionsRow(0, symbol = Some(t.investment), action = Some(t.action),
    quantity = Some(t.shares.longValue), price = Some((t.amount.getOrElse(0.0) / t.shares).longValue), date = t.date.map(Date.valueOf(_)) , amount =  t.amount.map(_.longValue),
    commission = None, fee = None, securityType = None, accuredInterest = None, settlementDate = None, description = None )

  def toTransactionsRow(t: IRATransaction) : TransactionsRow = TransactionsRow(0, symbol = Some(t.symbol), action = Some(t.action),
    quantity = t.quantity.map(_.longValue), price = (t.amount, t.quantity) match {
      case (_, Some(0)) => None
      case (Some(amt), Some(qty)) => Some((amt / qty).longValue)
      case _ => None
    }, date = t.date.map(Date.valueOf(_)) , amount =  t.amount.map(_.longValue),
    commission = t.commission.map(_.longValue), fee = t.fee.map(_.longValue), securityType = Some(t.securityType), accuredInterest = None,
    settlementDate = t.settlementDate.map(Date.valueOf(_)), description = Some(t.description) )
}

