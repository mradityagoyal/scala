import org.scalatest.{FlatSpec, Matchers}

import scala.util.Random

class OnScrKbSpec extends FlatSpec with Matchers  {

  "OnScreenKb" should " return coordinates correctly" in {
    OnScrKB.coords('A') should ===((0, 0))
    OnScrKB.coords('B') should ===((0, 1))
  }

  it should "return keystrokes correctly" in {
    val aToB = OnScrKB.path('A', 'B')
    aToB should ===("R*")
    OnScrKB.path('A', 'C') should ===("RR*")
    OnScrKB.path('A', 'E') should ===("D*")
    OnScrKB.path('A', 'F') should ===("RD*")
  }

  it should "return path correctly " in {
    val numtests = 100
    val lengths = (0 to numtests).map(_ => Random.nextInt(30))
    for (len <- lengths) {
      val cleaned = List.fill(len)(Random.nextInt(OnScrKB.alphabets.length())).map(OnScrKB.alphabets(_)).mkString
      val foldLeftResult = OnScrKB.keystrokesByFL(cleaned)
      val devideAndConqRes = OnScrKB.keystrokesByDnQ(cleaned)
      val foldResult = OnScrKB.keystrokesByF(cleaned)
      foldLeftResult should ===(devideAndConqRes)
      devideAndConqRes should ===(foldResult)
    }

  }

}