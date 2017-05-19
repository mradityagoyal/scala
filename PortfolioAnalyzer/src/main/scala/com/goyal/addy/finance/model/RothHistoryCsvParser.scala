package com.goyal.addy.finance.model

import scala.io.Source
import scala.util.{Failure, Success, Try}

/**
  * Created by addy on 5/19/17.
  */
object RothHistoryCsvParser {

  def readFile(path: String): List[RothTransaction] = {
    val src = Source.fromFile(path)
    val lines = src.getLines().toList
    val trans : List[Try[RothTransaction]] = lines.map(RothTransaction.parse(_))
    trans.collect{case Success(t)=>t} //collect lines that were parsed successfully.
  }

}
