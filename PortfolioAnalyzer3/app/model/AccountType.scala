package model

sealed trait AccountType{
  def accType: String
}

final case object ROTH extends AccountType{
  override def accType: String = "ROTH"
}

final case object F01K extends AccountType{
  override def accType: String = "401K"
}

object AccountType {
  def fromString(accType: String): AccountType = accType match {
    case "ROTH" => ROTH
    case "401K" => F01K
  }
}


