package model

/** Entity class storing rows of table Transactions
  *
  *  @param id Database column ID SqlType(BIGINT), AutoInc, PrimaryKey
  *  @param symbol Database column SYMBOL SqlType(VARCHAR)
  *  @param action Database column ACTION SqlType(VARCHAR)
  *  @param quantity Database column QUANTITY SqlType(DECIMAL)
  *  @param price Database column PRICE SqlType(DECIMAL)
  *  @param amount Database column AMOUNT SqlType(DECIMAL)
  *  @param date Database column DATE SqlType(DATE)
  *  @param commission Database column COMMISSION SqlType(DECIMAL)
  *  @param description Database column DESCRIPTION SqlType(VARCHAR)
  *  @param accountType Database column ACCOUNT_TYPE SqlType(VARCHAR)
  *  @param owner Database column OWNER SqlType(VARCHAR) */
case class TransactionsRow(id: Long, symbol: Option[String], action: String, quantity: Option[scala.math.BigDecimal],
                           price: Option[scala.math.BigDecimal], amount: scala.math.BigDecimal, date: java.sql.Date,
                           commission: scala.math.BigDecimal, description: Option[String], accountType: AccountType, owner: AccountOwner)
