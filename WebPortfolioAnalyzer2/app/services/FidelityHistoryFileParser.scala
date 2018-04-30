package services

import java.nio.file.Path

import model.{F01KTransaction, FidelityTransaction, IRATransaction}

import scala.io.Source
import scala.util.Success

trait HistoryFileParser {


  /**
    * Read a history file and return a sequence of FidelityTransactions
    * @param path
    * @return
    */
  def readFile(path: String): Seq[FidelityTransaction]

}

object FidelityHistoryFileParser extends  HistoryFileParser {

  //These two constants are used to determine if the hisotry file is IRA / 401K
  //first file in 401K history file
  val F01KHeader = "Date,Investment,Transaction Type,Amount,Shares/Unit"
  //first file in 401K history file
  val IRAHeader = "Run Date,Action,Symbol,Security Description,Security Type,Quantity,Price ($),Commission ($),Fees ($),Accrued Interest ($),Amount ($),Settlement Date"


  override def readFile(path: String): Seq[FidelityTransaction] = {

    val lines = Source.fromFile(path).getLines()
    val header = lines.next
    header match {
      case F01KHeader => lines.map(F01KTransaction.parseLine).collect{case Success(t)=>t}.toSeq
      case IRAHeader => lines.map(IRATransaction.parseLine).collect{case Success(t)=>t}.toSeq
    }
  }
}
