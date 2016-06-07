package objsets

object WK3 {
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet
  val tweet1 = new Tweet("Addy" , "rocks", 1)     //> tweet1  : objsets.Tweet = User: Addy
                                                  //| Text: rocks [1]
  val tweet2 = new Tweet("ragini", "awesome", 2)  //> tweet2  : objsets.Tweet = User: ragini
                                                  //| Text: awesome [2]
  val tweet3 = new Tweet("aditya", "working", 3)  //> tweet3  : objsets.Tweet = User: aditya
                                                  //| Text: working [3]
  
  val ts1 = new Empty incl tweet1 incl tweet2 incl tweet3
                                                  //> ts1  : objsets.TweetSet = {{{.},Elem:User: ragini
                                                  //| Text: awesome [2],{.}},Elem:User: Addy
                                                  //| Text: rocks [1],{{.},Elem:User: aditya
                                                  //| Text: working [3],{.}}}
  ts1.filter( t => t.user == "ragini")            //> res0: objsets.TweetSet = {{.},Elem:User: ragini
                                                  //| Text: awesome [2],{.}}
}