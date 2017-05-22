package com.goyal.addy.finance.f01k.model

import com.goyal.addy.finance.ira.model.RothTransaction

import scala.io.Source
import scala.util.{Success, Try}

/**
  * Created by agoyal on 5/22/17.
  */
object F01KHistoryParser {

  def readFile(path: String): List[F01KTransaction] = {
    val lines = Source.fromFile(path).getLines().toList
    val trans : List[Try[F01KTransaction]] = lines.map(F01KTransaction.parse)
    trans.collect{case Success(t)=>t} //collect lines that were parsed successfully.
  }

}
