import scala.io.Source

val src = Source.fromFile("/home/agoyal/timeZones.txt")

val lines = src.getLines().toList

case class TZTriplet(label: String, gmtCid: String, dstCid: String)

def fromLine(line: String): TZTriplet = {
  val split = line.split(",")
  assert(split.length == 5)
  val trimmed = split.map(_.trim)

  TZTriplet(trimmed(3), trimmed(2), trimmed(4))

}

val trimmed = lines.map(str => str.substring(str.indexOf("\""), str.lastIndexOf("\"")))


trimmed.head\