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
class TablesRepository @Inject()(dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) extends Tables {

  override val profile = slick.jdbc.H2Profile
  // We want the JdbcProfile for this provider
  protected val dbConfig = dbConfigProvider.get[JdbcProfile]

  // These imports are important, the first one brings db into scope, which will let you do the actual db operations.
  // The second one brings the Slick DSL into scope, which lets you define the table and other queries.
  import dbConfig._
  import profile.api._
  // NOTE: GetResult mappers for plain SQL are only generated for tables where Slick knows how to map the types of all columns.

  /**
   * List all the Transactions in the database.
   */
  def list(): Future[Seq[TransactionsRow]] = db.run {
    Transactions.result
  }

  def addFidelityTransactions(fts: Seq[FidelityTransaction]): Future[Option[Int]] = db.run {
    Transactions ++= (fts map FidelityTransaction.toTransactionsRow)
  }


}

