package com.addy.google


/*
## Question: 
Imagine a on-screen keyboard on your smart tv. You are presented with alphabets arranged in a 5 X 6 grid as..

A B C D E
F G H I J
K L M N O 
P Q R S T
U V W X Y 
Z 

Your tv remote has Left (L), Right (R), Up (U), Down (D) and OK (*) keys. 
Initially, the cursor is at top left corner in the grid. To type a letter, you use L R U and D keys to navigate 
the grid, and press OK. The cursor stays at the char where you pressed OK. 
eg To type BOY you would need to press R * D D R R R * D D * 
Note: the rows are not circular linked, i.e you can not go from E to A by going right, or go from A to E by going left.  

Find the sequence of keys that you need to press on your remote to type the input string. 
*/

object OnScrKB {

  //onscreen kb has a grid with rowWidth. 
  //A B C D 
  //E F G H
  //I J K L
  protected val rowWidth = 4

  val alphabets = 'A' to 'Z'
  protected val withIndex: List[(Char, Int)] = (alphabets.zipWithIndex).toList
  //A map of Char to its coordinates. 
  val coords: Map[Char, (Int, Int)] = (withIndex.map {
    case (char, idx) => (char, (idx / rowWidth, idx % rowWidth))
  }).toMap

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
    horizontal + vertical + "*"
  }

  def getKeyStrokes(input: String, startChar: Char = 'A'): String = {
    val zero = ("", startChar)
    //(acc, last) + next => (acc+ aToB , next) 
    def foldHelper(zero: (String, Char), next: Char): (String, Char) = zero match {
      case (acc, last) => (acc + path(last, next), next)
    }
    val result = input.foldLeft(zero)(foldHelper)
    result._1
  }
  
  def devideAndConquer(input: String): String = {
    def splitAndMerge(in: String, startChar: Char): String = {
      if(in.length() < 4){
        //if length is <4 then dont split.. as you might end up with one side having only 1 char. 
        getKeyStrokes(in, startChar)
      }else{
        //split
        val (x, y) = in.splitAt(in.length()/2)
        splitAndMerge(x, startChar) + splitAndMerge(y, x.last)
      }
    }
    splitAndMerge(input, 'A')
  }
  
  protected case class PathAcc(text: String, path: String)
  def parallelFold(input: String): String = {
    def op(left: PathAcc, right: PathAcc): PathAcc = {
      PathAcc(text = left.text + right.text, path = left.path + path(left.text.last, right.text.head) + right.path)
    }
    val mapped = input.map{x => PathAcc(text = "" + x, path = "")}
    val z = PathAcc(text = "A", path = "")
    val foldresult = mapped.fold(z)(op)
    foldresult.path
  }

}