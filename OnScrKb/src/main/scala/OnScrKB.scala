object OnScrKB {

  //onscreen kb has a grid with rowWidth. 
  //A B C D 
  //E F G H
  //. . . ..
  //z <SpaceChar>
  protected val rowWidth = 4
  val alphabets = ('A' to 'Z') :+ ' ' //adding blank space. 
  protected val withIndex: List[(Char, Int)] = (alphabets.zipWithIndex).toList
  //A map of Char to its coordinates. 
  val coords: Map[Char, (Int, Int)] = (withIndex.map {
    case (char, idx) => (char, (idx / 4, idx % 4))
  }).toMap

  //returns the keystrokes Left Right Up Down or Ok to go from start to end in the grid
  def path(start: Char, end: Char): String = {
    val (xstart, ystart) = coords(start)
    val (xend, yend) = coords(end)
    //key strokes in horizontal direction
    val horizontal = if (ystart <= yend) {
      // go right
      List.fill(yend - ystart)('R').mkString
    } else {
      //go left
      List.fill(ystart - yend)('L').mkString
    }
    //keystrokes in vertical direction
    val vertical = if (xstart <= xend) {
      // go Down
      List.fill(xend - xstart)('D').mkString
    } else {
      //go Up
      List.fill(xstart - xend)('U').mkString
    }
    horizontal + vertical + "*" //* represents OK
  }

  /**
   *
   * @param input - the input word.
   * @param startChar - the starting position on the grid. Default is top left A.
   * @return returns the keystrokes to type the input word using the onScreenkeyboard
   * Uses FoldLeft.
   */
  def keystrokesByFL(input: String, startChar: Char = 'A'): String = {
    val zero = ("", startChar)
    //(acc, last) + next => (acc+ aToB , next) 
    def op(zero: (String, Char), next: Char): (String, Char) = zero match {
      case (acc, last) => (acc + path(last, next), next)
    }
    val result = input.foldLeft(zero)(op)
    result._1
  }

  /**
   * @param input the input word / string.
   * @param startChar - the starting position on the grid. Default is top left A.
   * @return returns the keystrokes to type the input word using the onScreenkeyboard
   * Uses devide and conquer mechanism. The algorithm is similar to merge sort.
   * We split the input word into two if the length is > 3
   * we recursively call the subroutine to get the path of left and right halfs from the split.
   * In the end.. we add the keystrokes for first + keystrokes from end of first string to start of second string + keystrokes for second.
   * Essentially we devide the input string intwo smaller halves till we get to size 4. for smaller than 4 we use the fold right.
   */
  def keystrokesByDnQ(input: String, startChar: Char = 'A'): String = {
    def splitAndMerge(in: String, startChar: Char): String = {
      if (in.length() < 4) {
        //if length is <4 then dont split.. as you might end up with one side having only 1 char. 
        keystrokesByFL(in, startChar)
      } else {
        //split
        val (x, y) = in.splitAt(in.length() / 2)
        splitAndMerge(x, startChar) + splitAndMerge(y, x.last)
      }
    }
    splitAndMerge(input, startChar)
  }

  //case class helpful in writing the fold operation 
  protected case class PathAcc(text: String, path: String)

  /**
 * @param input
 * @param startChar - the starting position on the grid. Default is top left A.
 * @return returns the keystrokes to type the input word using the onScreenkeyboard
 * uses the property that the underlying operation is associative (but not commutative). 
 * For eg.. the keystrokes("ABCDEFGHI", startChar = 'A') ==  keystrokes("ABC", startChar='A')+keystrokes("DEF", 'C') + keystrokes("GHI", 'F')
 */
def keystrokesByF(input: String, startChar: Char = 'A'): String = {
    val mapped = input.map { x => PathAcc(text = "" + x, path = "") } // map each character in input to case class PathAcc("CharAsString", "")
    val z = PathAcc(text = ""+startChar, path = "") //the starting char. 

    def op(left: PathAcc, right: PathAcc): PathAcc = {
      PathAcc(text = left.text + right.text, path = left.path + path(left.text.last, right.text.head) + right.path)
    }
    val foldresult = mapped.fold(z)(op)

    foldresult.path
  }

}