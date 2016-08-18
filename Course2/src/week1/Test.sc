package week1

object Test {
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet
  
 val f: PartialFunction[String , String] =  {case "ping" => "pong"}
                                                  //> f  : PartialFunction[String,String] = <function1>
 
 f.isDefinedAt("bala")                            //> res0: Boolean = false
 f("ping")                                        //> res1: String = pong
 
 val f1: PartialFunction[List[Int], String] = {
   case Nil => "one"
   case x :: y :: rest => "two"
 }                                                //> f1  : PartialFunction[List[Int],String] = <function1>
 
 val g: PartialFunction[List[Int], String] = {
   case Nil => "one"
   case x :: rest => rest match {
     case Nil => "two"
   }
 }                                                //> g  : PartialFunction[List[Int],String] = <function1>

 g.isDefinedAt(List(1,2,3))                       //> res2: Boolean = true
 g(List(1,2,3))                                   //> scala.MatchError: List(2, 3) (of class scala.collection.immutable.$colon$col
                                                  //| on)
                                                  //| 	at week1.Test$$anonfun$main$1$$anonfun$3.applyOrElse(week1.Test.scala:18
                                                  //| )
                                                  //| 	at week1.Test$$anonfun$main$1$$anonfun$3.applyOrElse(week1.Test.scala:16
                                                  //| )
                                                  //| 	at scala.runtime.AbstractPartialFunction.apply(AbstractPartialFunction.s
                                                  //| cala:36)
                                                  //| 	at week1.Test$$anonfun$main$1.apply$mcV$sp(week1.Test.scala:24)
                                                  //| 	at org.scalaide.worksheet.runtime.library.WorksheetSupport$$anonfun$$exe
                                                  //| cute$1.apply$mcV$sp(WorksheetSupport.scala:76)
                                                  //| 	at org.scalaide.worksheet.runtime.library.WorksheetSupport$.redirected(W
                                                  //| orksheetSupport.scala:65)
                                                  //| 	at org.scalaide.worksheet.runtime.library.WorksheetSupport$.$execute(Wor
                                                  //| ksheetSupport.scala:75)
                                                  //| 	at week1.Test$.main(week1.Test.scala:3)
                                                  //| 	at week1.Test.main(week1.Test.scala)

 
}