package com.goyal.addy.finance.model

/**
  * Created by addy on 5/19/17.
  */
object FileReaderTest extends App{

  val path = "resources/History-27Feb17-15May17.csv"
  val trans = RothHistoryFileParser.readFile(path)

  trans.foreach(println)

}
