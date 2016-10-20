package scalashop

object Testiing {
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet

  val arr = Array(0, 1, 2, 3, 4, 5, 6, 7, 8)      //> arr  : Array[Int] = Array(0, 1, 2, 3, 4, 5, 6, 7, 8)
  val sum = rgba(
      arr.map(red(_)).sum / arr.length,
      arr.map(green(_)).sum / arr.length,
      arr.map(blue(_)).sum / arr.length,
      arr.map(alpha(_)).sum / arr.length
      )                                           //> sum  : scalashop.RGBA = 4

  val ran = 2 to 10 by 2                          //> ran  : scala.collection.immutable.Range = Range(2, 4, 6, 8, 10)

  val width = 10                                  //> width  : Int = 10
  val numTasks = 4                                //> numTasks  : Int = 4
  val stripWidth = width / numTasks               //> stripWidth  : Int = 2
  val splits = 0 to width by stripWidth           //> splits  : scala.collection.immutable.Range = Range(0, 2, 4, 6, 8, 10)

  val seq = splits.zip((splits.tail.toList ++ List(width)).distinct )
                                                  //> seq  : scala.collection.immutable.IndexedSeq[(Int, Int)] = Vector((0,2), (2,
                                                  //| 4), (4,6), (6,8), (8,10))
  
}