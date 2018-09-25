package model



import java.sql.Timestamp

import services.{AccountOwner, AccountType}
import slick.lifted.Tag
import slick.jdbc.H2Profile.api._


final class TransactionsTable(tag: Tag) extends Table[TransactionRow](tag, "TRANSACTIONS") {

  implicit val dateMapper = MappedColumnType.base[java.util.Date, java.sql.Timestamp](
    d => new java.sql.Timestamp(d.getTime),
    d => new java.util.Date(d.getTime))

  implicit val accountTypeMapper = MappedColumnType.base[AccountType, String](
    accountType => accountType.accType,
    stringAcctType => AccountType.getAccountTypeFromString(stringAcctType)
  )

  implicit val accountOwnerMapper = MappedColumnType.base[AccountOwner, String](
    owner => owner.fullName,
    name => AccountOwner.getFromName(name)
  )

  def id = column[Long]("ID", O.PrimaryKey, O.AutoInc)
  def symbol = column[String]("SYMBOL")
  def action = column[String]("ACTION")
  def quantity = column[Double]("QUANTITY")
  def price = column[Option[Double]]("PRICE")
  def amount = column[Double]("AMOUNT")
  def date = column[java.util.Date]("DATE")
  def commission = column[Option[Double]]("COMMISSION")
  def description = column[String]("DESCRIPTION")
  def accountType = column[AccountType]("ACCOUNT_TYPE")
  def owner = column[AccountOwner]("OWNER")
  def * = (id, symbol, action, quantity, price, amount, date, commission, description, accountType, owner).mapTo[TransactionRow]

}
