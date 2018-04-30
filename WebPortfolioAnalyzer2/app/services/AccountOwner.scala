package services

sealed trait AccountOwner{
  def fullName: String
}

case class Addy() extends AccountOwner{
  override def fullName: String = "Aditya Goyal"
}
case class Ragini() extends AccountOwner{
  override def fullName: String = "Ragini Mangla"
}

object AccountOwner {
  def getFromName(name: String): AccountOwner = name match {
    case "Aditya Goyal" => Addy()
    case "Ragini Mangla" => Ragini()
  }
}

