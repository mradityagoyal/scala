package model

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
class TransactionRepository @Inject()(dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) {
  // We want the JdbcProfile for this provider
  private val dbConfig = dbConfigProvider.get[JdbcProfile]

  // These imports are important, the first one brings db into scope, which will let you do the actual db operations.
  // The second one brings the Slick DSL into scope, which lets you define the table and other queries.
  import dbConfig._
  import profile.api._
  // NOTE: GetResult mappers for plain SQL are only generated for tables where Slick knows how to map the types of all columns.
  import slick.jdbc.{GetResult => GR}

  /** Entity class storing rows of table Transactions
    *  @param id Database column ID SqlType(BIGINT), AutoInc, PrimaryKey
    *  @param ticker Database column TICKER SqlType(VARCHAR)
    *  @param numShares Database column NUM_SHARES SqlType(VARCHAR)
    *  @param action Database column ACTION SqlType(VARCHAR) */
  case class TransactionRow(id: Long, ticker: String, numShares: Option[Int], action: Option[String])
  /** GetResult implicit for fetching TransactionsRow objects using plain SQL queries */
  implicit def GetResultTransactionsRow(implicit e0: GR[Long], e1: GR[String], e2: GR[Option[String]]): GR[TransactionRow] = GR{
    prs => import prs._
      TransactionRow.tupled((<<[Long], <<[String], <<?[Int], <<?[String]))
  }

  /** Table description of table TRANSACTIONS. Objects of this class serve as prototypes for rows in queries. */
  class Transaction(_tableTag: Tag) extends Table[TransactionRow](_tableTag, "TRANSACTIONS") {
    def * = (id, ticker, numShares, action) <> (TransactionRow.tupled, TransactionRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(ticker), numShares, action).shaped.<>({r=>import r._; _1.map(_=> TransactionRow.tupled((_1.get, _2.get, _3, _4)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column ID SqlType(BIGINT), AutoInc, PrimaryKey */
    val id: Rep[Long] = column[Long]("ID", O.AutoInc, O.PrimaryKey)
    /** Database column TICKER SqlType(VARCHAR) */
    val ticker: Rep[String] = column[String]("TICKER")
    /** Database column NUM_SHARES SqlType(VARCHAR) */
    val numShares: Rep[Option[Int]] = column[Option[Int]]("NUM_SHARES")
    /** Database column ACTION SqlType(VARCHAR) */
    val action: Rep[Option[String]] = column[Option[String]]("ACTION")
  }
  /** Collection-like TableQuery object for table Transactions */
  lazy val transaction =  TableQuery[Transaction]

  /**
   * Create a person with the given name and age.
   *
   * This is an asynchronous operation, it will return a future of the created person, which can be used to obtain the
   * id for that person.
   */
  def create(ticker: String, numShares: Int, action: String): Future[TransactionRow] = db.run {
    // We create a projection of just the tocker, numShares and action columns, since we're not inserting a value for the id column
    (transaction.map(t => (t.ticker, t.numShares, t.action))
      // Now define it to return the id, because we want to know what id was generated for the person
      returning transaction.map(_.id)
      // And we define a transformation for the returned value, which combines our original parameters with the
      // returned id
      into ((values, id) => TransactionRow(id, values._1, values._2, values._3))
    // And finally, insert the person into the database
    ) += (ticker, Some(numShares), Some(action))
  }

  /**
   * List all the people in the database.
   */
  def list(): Future[Seq[TransactionRow]] = db.run {
    transaction.result
  }
}
