package com.addy.google

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
    case (char, idx) => (char, (idx / 4, idx % 4))
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