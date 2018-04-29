package model

// AUTO-GENERATED Slick data model
/** Stand-alone Slick data model for immediate use */
object Tables extends {
  val profile = slick.jdbc.H2Profile
} with Tables

/** Slick data model trait for extension, choice of backend or usage in the cake pattern. (Make sure to initialize this late.) */
trait Tables {
  val profile: slick.jdbc.JdbcProfile
  import profile.api._
  import slick.model.ForeignKeyAction
  // NOTE: GetResult mappers for plain SQL are only generated for tables where Slick knows how to map the types of all columns.
  import slick.jdbc.{GetResult => GR}

  /** DDL for all tables. Call .create to execute. */
  lazy val schema: profile.SchemaDescription = Transactions.schema
  @deprecated("Use .schema instead of .ddl", "3.0")
  def ddl = schema

  /** Entity class storing rows of table Transactions
   *  @param id Database column ID SqlType(BIGINT), AutoInc, PrimaryKey
   *  @param symbol Database column SYMBOL SqlType(VARCHAR)
   *  @param action Database column ACTION SqlType(VARCHAR)
   *  @param quantity Database column QUANTITY SqlType(BIGINT)
   *  @param price Database column PRICE SqlType(BIGINT)
   *  @param date Database column DATE SqlType(DATE)
   *  @param amount Database column AMOUNT SqlType(BIGINT)
   *  @param commission Database column COMMISSION SqlType(BIGINT)
   *  @param fee Database column FEE SqlType(BIGINT)
   *  @param securityType Database column SECURITY_TYPE SqlType(VARCHAR)
   *  @param accuredInterest Database column ACCURED_INTEREST SqlType(BIGINT)
   *  @param settlementDate Database column SETTLEMENT_DATE SqlType(DATE)
   *  @param description Database column DESCRIPTION SqlType(VARCHAR) */
  case class TransactionsRow(id: Long, symbol: Option[String], action: Option[String], quantity: Option[Long], price: Option[Long], date: Option[java.sql.Date], amount: Option[Long], commission: Option[Long], fee: Option[Long], securityType: Option[String], accuredInterest: Option[Long], settlementDate: Option[java.sql.Date], description: Option[String])
  /** GetResult implicit for fetching TransactionsRow objects using plain SQL queries */
  implicit def GetResultTransactionsRow(implicit e0: GR[Long], e1: GR[Option[String]], e2: GR[Option[Long]], e3: GR[Option[java.sql.Date]]): GR[TransactionsRow] = GR{
    prs => import prs._
    TransactionsRow.tupled((<<[Long], <<?[String], <<?[String], <<?[Long], <<?[Long], <<?[java.sql.Date], <<?[Long], <<?[Long], <<?[Long], <<?[String], <<?[Long], <<?[java.sql.Date], <<?[String]))
  }
  /** Table description of table TRANSACTIONS. Objects of this class serve as prototypes for rows in queries. */
  class Transactions(_tableTag: Tag) extends Table[TransactionsRow](_tableTag, "TRANSACTIONS") {
    def * = (id, symbol, action, quantity, price, date, amount, commission, fee, securityType, accuredInterest, settlementDate, description) <> (TransactionsRow.tupled, TransactionsRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), symbol, action, quantity, price, date, amount, commission, fee, securityType, accuredInterest, settlementDate, description).shaped.<>({r=>import r._; _1.map(_=> TransactionsRow.tupled((_1.get, _2, _3, _4, _5, _6, _7, _8, _9, _10, _11, _12, _13)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column ID SqlType(BIGINT), AutoInc, PrimaryKey */
    val id: Rep[Long] = column[Long]("ID", O.AutoInc, O.PrimaryKey)
    /** Database column SYMBOL SqlType(VARCHAR) */
    val symbol: Rep[Option[String]] = column[Option[String]]("SYMBOL")
    /** Database column ACTION SqlType(VARCHAR) */
    val action: Rep[Option[String]] = column[Option[String]]("ACTION")
    /** Database column QUANTITY SqlType(BIGINT) */
    val quantity: Rep[Option[Long]] = column[Option[Long]]("QUANTITY")
    /** Database column PRICE SqlType(BIGINT) */
    val price: Rep[Option[Long]] = column[Option[Long]]("PRICE")
    /** Database column DATE SqlType(DATE) */
    val date: Rep[Option[java.sql.Date]] = column[Option[java.sql.Date]]("DATE")
    /** Database column AMOUNT SqlType(BIGINT) */
    val amount: Rep[Option[Long]] = column[Option[Long]]("AMOUNT")
    /** Database column COMMISSION SqlType(BIGINT) */
    val commission: Rep[Option[Long]] = column[Option[Long]]("COMMISSION")
    /** Database column FEE SqlType(BIGINT) */
    val fee: Rep[Option[Long]] = column[Option[Long]]("FEE")
    /** Database column SECURITY_TYPE SqlType(VARCHAR) */
    val securityType: Rep[Option[String]] = column[Option[String]]("SECURITY_TYPE")
    /** Database column ACCURED_INTEREST SqlType(BIGINT) */
    val accuredInterest: Rep[Option[Long]] = column[Option[Long]]("ACCURED_INTEREST")
    /** Database column SETTLEMENT_DATE SqlType(DATE) */
    val settlementDate: Rep[Option[java.sql.Date]] = column[Option[java.sql.Date]]("SETTLEMENT_DATE")
    /** Database column DESCRIPTION SqlType(VARCHAR) */
    val description: Rep[Option[String]] = column[Option[String]]("DESCRIPTION")
  }
  /** Collection-like TableQuery object for table Transactions */
  lazy val Transactions = new TableQuery(tag => new Transactions(tag))
}
