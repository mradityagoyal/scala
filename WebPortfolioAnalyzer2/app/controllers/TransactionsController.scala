package controllers

import model.FidelityTransaction
import play.api.libs.json._
import play.api.mvc.{Action, Controller}
import services.{Addy, FidelityHistoryFileParser, Ragini}

class TransactionsController extends Controller {

  val addyRothPath = "resources/roth/ROTH_ALL.csv"
  val addyIRAPath = "resources/roth/Addy_IRA.csv"
  val addyF01KPath = "resources/401K/401KHistoryAll.csv"
  val ragsRothPath = "resources/roth/Rags_ROTH_ALL.csv"

//  val allHistoryFiles = List(addyRothPath,  addyIRAPath,  addyF01KPath,  ragsRothPath)

  def loadAll = Action {

    val allTransactions: Seq[FidelityTransaction] = Seq(addyF01KPath, addyIRAPath, addyRothPath)
      .flatMap(FidelityHistoryFileParser.readFile(_, Addy())) ++ FidelityHistoryFileParser.readFile(ragsRothPath, Ragini())



    Ok("Loaded everything")
  }

  def welcome = Action {
    Ok("Hello There")
  }

}
