//package controllers
//
//import javax.inject.Inject
//import model.{FidelityTransaction, TablesRepository}
//import play.api.libs.json._
//import play.api.mvc.{Action, Controller, MessagesAbstractController, MessagesControllerComponents}
//import services.{Addy, FidelityHistoryFileParser, Ragini}
//
//import scala.concurrent.{ExecutionContext, Future}
//
//class TransactionsController @Inject()(repo: TablesRepository,
//                                       cc: MessagesControllerComponents
//                                      )(implicit ec: ExecutionContext)
//  extends MessagesAbstractController(cc) {
//
//  val addyRothPath = "resources/roth/ROTH_ALL.csv"
//  val addyIRAPath = "resources/roth/Addy_IRA.csv"
//  val addyF01KPath = "resources/401K/401KHistoryAll.csv"
//  val ragsRothPath = "resources/roth/Rags_ROTH_ALL.csv"
//
////  val allHistoryFiles = List(addyRothPath,  addyIRAPath,  addyF01KPath,  ragsRothPath)
//
//  def loadAll = Action {
//
//    val allFidelityTransactions: Seq[FidelityTransaction] = Seq(addyF01KPath, addyIRAPath, addyRothPath)
//      .flatMap(FidelityHistoryFileParser.readFile(_, Addy)) ++ FidelityHistoryFileParser.readFile(ragsRothPath, Ragini)
//
//    val future: Future[Option[Int]] = repo.addFidelityTransactions(allFidelityTransactions)
//
////    val totalRecordsLoaded = for {
////      maybeSaved <- future
////      saved <- maybeSaved
////    } yield saved
//
//
//
//
//    Ok(s"Loaded  records to DB")
//  }
//
//  def list = Action {
//    Ok( s"Hi"
////      for {
////        maybeTransactions <- repo.list
////        transactions <- maybeTransactions
////      } yield transactions
//    )
//  }
//
//}
