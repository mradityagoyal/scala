package controllers

import javax.inject._
import model.{FidelityTransaction, Repository}
import play.api.mvc._
import services.TransactionFileReader

import scala.concurrent.ExecutionContext

class TransactionsController @Inject()(repo: Repository,
                                      cc: MessagesControllerComponents
                                )(implicit ec: ExecutionContext)
  extends MessagesAbstractController(cc) {

//  implicit val transactionsRowFormat = Json.format[TransactionsRow]



  /**
   * The mapping for the person form.
   */
//  val transactionForm: Form[CreateTransactionForm] = Form {
//    mapping(
//      "ticker" -> nonEmptyText,
//      "numShares" -> number.verifying(min(0), max(140)),
//      "action" -> nonEmptyText
//    )(CreateTransactionForm.apply)(CreateTransactionForm.unapply)
//  }

  /**
   * The index action.
   */
//  def index = Action { implicit request =>
//    Ok(views.html.transactions(transactionForm))
//  }

  /**
   * A REST endpoint that gets all the people as JSON.
   */
//  def getTransactions = Action.async { implicit request =>
//    repo.list().map { transactions =>
//      Ok(Json.toJson(transactions))
//    }
//  }

  def transactionTable = Action { implicit request =>
    val msg = "This is Transactions Table."
    val allTransactions = TransactionFileReader.allTransactions
    Ok(views.html.table(msg, allTransactions))
  }

  def addTransactions = Action { implicit request => {
    val all = TransactionFileReader.allTransactions
    repo.insertAll(all)
    Ok("All Inserted")
  }}
}
