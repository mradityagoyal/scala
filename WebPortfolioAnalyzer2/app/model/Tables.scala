package model

import services.{AccountOwner, AccountType}

// AUTO-GENERATED Slick data model
/** Stand-alone Slick data model for immediate use */
object Tables extends {
  val profile = slick.jdbc.H2Profile
} with Tables

/** Slick data model trait for extension, choice of backend or usage in the cake pattern. (Make sure to initialize this late.) */
trait Tables {
  val profile: slick.jdbc.JdbcProfile
  import profile.api._
  // NOTE: GetResult mappers for plain SQL are only generated for tables where Slick knows how to map the types of all columns.
  import slick.jdbc.{GetResult => GR}

  /** DDL for all tables. Call .create to execute. */
  lazy val schema: profile.SchemaDescription = Transactions.schema
  @deprecated("Use .schema instead of .ddl", "3.0")
  def ddl: profile.DDL = schema

  /** GetResult implicit for fetching TransactionsRow objects using plain SQL queries */
  implicit def GetResultTransactionsRow(implicit e0: GR[Long], e1: GR[Option[String]], e2: GR[String], e3: GR[Option[scala.math.BigDecimal]], e4: GR[scala.math.BigDecimal], e5: GR[java.sql.Date]): GR[TransactionsRow] = GR{
    prs => import prs._
      TransactionsRow.tupled((<<[Long], <<?[String], <<[String], <<?[scala.math.BigDecimal], <<?[scala.math.BigDecimal], <<[scala.math.BigDecimal], <<[java.sql.Date], <<[scala.math.BigDecimal], <<?[String], <<[AccountType], <<[AccountOwner]))
  }
  /** Table description of table TRANSACTIONS. Objects of this class serve as prototypes for rows in queries. */
  class Transactions(_tableTag: Tag) extends Table[TransactionsRow](_tableTag, "TRANSACTIONS") {

    implicit val accountOwnerMapper = MappedColumnType.base[AccountOwner, String](_.fullName, AccountOwner.getFromName)

    implicit val accountTypeMapper = MappedColumnType.base[AccountType, String](_.accType, AccountType.fromString)



    def * = (id, symbol, action, quantity, price, amount, date, commission, description, accountType, owner) <> (TransactionsRow.tupled, TransactionsRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), symbol, Rep.Some(action), quantity, price, Rep.Some(amount), Rep.Some(date), Rep.Some(commission), description, Rep.Some(accountType), Rep.Some(owner)).shaped.<>({r=>import r._; _1.map(_=> TransactionsRow.tupled((_1.get, _2, _3.get, _4, _5, _6.get, _7.get, _8.get, _9, _10.get, _11.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column ID SqlType(BIGINT), AutoInc, PrimaryKey */
    val id: Rep[Long] = column[Long]("ID", O.AutoInc, O.PrimaryKey)
    /** Database column SYMBOL SqlType(VARCHAR) */
    val symbol: Rep[Option[String]] = column[Option[String]]("SYMBOL")
    /** Database column ACTION SqlType(VARCHAR) */
    val action: Rep[String] = column[String]("ACTION")
    /** Database column QUANTITY SqlType(DECIMAL) */
    val quantity: Rep[Option[scala.math.BigDecimal]] = column[Option[scala.math.BigDecimal]]("QUANTITY")
    /** Database column PRICE SqlType(DECIMAL) */
    val price: Rep[Option[scala.math.BigDecimal]] = column[Option[scala.math.BigDecimal]]("PRICE")
    /** Database column AMOUNT SqlType(DECIMAL) */
    val amount: Rep[scala.math.BigDecimal] = column[scala.math.BigDecimal]("AMOUNT")
    /** Database column DATE SqlType(DATE) */
    val date: Rep[java.sql.Date] = column[java.sql.Date]("DATE")
    /** Database column COMMISSION SqlType(DECIMAL) */
    val commission: Rep[scala.math.BigDecimal] = column[scala.math.BigDecimal]("COMMISSION")
    /** Database column DESCRIPTION SqlType(VARCHAR) */
    val description: Rep[Option[String]] = column[Option[String]]("DESCRIPTION")
    /** Database column ACCOUNT_TYPE SqlType(VARCHAR) */
    val accountType: Rep[AccountType] = column[AccountType]("ACCOUNT_TYPE")
    /** Database column OWNER SqlType(VARCHAR) */
    val owner: Rep[AccountOwner] = column[AccountOwner]("OWNER")
  }
  /** Collection-like TableQuery object for table Transactions */
  lazy val Transactions = new TableQuery(tag => new Transactions(tag))

}
