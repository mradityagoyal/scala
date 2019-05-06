package model

sealed trait AccountOwner{
  def fullName: String
}

case object Addy extends AccountOwner{
  override def fullName: String = "Aditya Goyal"
}
case object Ragini extends AccountOwner{
  override def fullName: String = "Ragini Mangla"
}

object AccountOwner {
  def getFromName(name: String): AccountOwner = name match {
    case "Aditya Goyal" => Addy
    case "Ragini Mangla" => Ragini
  }

}

