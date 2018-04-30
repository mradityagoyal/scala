package controllers

import model.FidelityTransaction
import play.api.libs.json._
import play.api.mvc.{Action, Controller}
import services.FidelityHistoryFileParser

class TransactionsController extends Controller {

  val addyRothPath = "resources/roth/ROTH_ALL.csv"
  val addyIRAPath = "resources/roth/Addy_IRA.csv"
  val addyF01KPath = "resources/401K/401KHistoryAll.csv"
  val ragsRothPath = "resources/roth/Rags_ROTH_ALL.csv"

  val allHistoryFiles = List(addyRothPath,  addyIRAPath,  addyF01KPath,  ragsRothPath)

  def loadAll = Action {

    val allTransactions: Seq[FidelityTransaction] = allHistoryFiles.flatMap(FidelityHistoryFileParser.readFile)



    Ok("Loaded everything")
  }

  def welcome = Action {
    Ok("Hello There")
  }

}
