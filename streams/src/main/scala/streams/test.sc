package streams


object test {
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet
  
  val level =     """ST
          |oo
          |oo""".stripMargin                      //> level  : String = ST
                                                  //| oo
                                                  //| oo

  val vectLevel = (level.split("\n") map (x => x.toVector)) .toVector
                                                  //> vectLevel  : Vector[Vector[Char]] = Vector(Vector(S, T), Vector(o, o), Vecto
                                                  //| r(o, o))
  vectLevel(0)(0)                                 //> res0: Char = S
  
  case class Pos(x: Int, y: Int) {
    /** The position obtained by changing the `x` coordinate by `d` */
    def dx(d: Int) = copy(x = x + d)

    /** The position obtained by changing the `y` coordinate by `d` */
    def dy(d: Int) = copy(y = y + d)
  }
  
  def terrainFunction(levelVector: Vector[Vector[Char]]): Pos => Boolean = ( p =>levelVector(p.x)(p.y) != '-' )
                                                  //> terrainFunction: (levelVector: Vector[Vector[Char]])streams.test.Pos => Bool
                                                  //| ean
    
  terrainFunction(vectLevel)(Pos(1,1))            //> res1: Boolean = true
  
   
}