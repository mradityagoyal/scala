package controllers

import javax.inject._
import model.{TransactionRepository, TransactionRow}
import models._
import play.api.data.Form
import play.api.data.Forms._
import play.api.data.validation.Constraints._
import play.api.i18n._
import play.api.libs.json.Json
import play.api.mvc._

import scala.concurrent.{ExecutionContext, Future}

class TransactionController @Inject()(repo: TransactionRepository,
                                 cc: MessagesControllerComponents
                                )(implicit ec: ExecutionContext)
  extends MessagesAbstractController(cc) {

  implicit val transactionRowFormat = Json.format[TransactionRow]



  /**
   * The mapping for the person form.
   */
  val transactionForm: Form[CreateTransactionForm] = Form {
    mapping(
      "ticker" -> nonEmptyText,
      "numShares" -> number.verifying(min(0), max(140)),
      "action" -> nonEmptyText
    )(CreateTransactionForm.apply)(CreateTransactionForm.unapply)
  }

  /**
   * The index action.
   */
  def index = Action { implicit request =>
    Ok(views.html.transactions(transactionForm))
  }

  /**
   * The add person action.
   *
   * This is asynchronous, since we're invoking the asynchronous methods on PersonRepository.
   */
  def addTransaction = Action.async { implicit request =>
    // Bind the form first, then fold the result, passing a function to handle errors, and a function to handle succes.
    transactionForm.bindFromRequest.fold(
      // The error function. We return the index page with the error form, which will render the errors.
      // We also wrap the result in a successful future, since this action is synchronous, but we're required to return
      // a future because the person creation function returns a future.
      errorForm => {
        Future.successful(Ok(views.html.transactions(errorForm)))
      },
      // There were no errors in the from, so create the person.
      transaction => {
        repo.create(transaction.ticker, transaction.numShares, transaction.action).map { _ =>
          // If successful, we simply redirect to the index page.
          Redirect(routes.TransactionController.index).flashing("success" -> "user.created")
        }
      }
    )
  }

  /**
   * A REST endpoint that gets all the people as JSON.
   */
  def getTransactions = Action.async { implicit request =>
    repo.list().map { transactions =>
      Ok(Json.toJson(transactions))
    }
  }
}

/**
 * The create person form.
 *
 * Generally for forms, you should define separate objects to your models, since forms very often need to present data
 * in a different way to your models.  In this case, it doesn't make sense to have an id parameter in the form, since
 * that is generated once it's created.
 */
case class CreateTransactionForm(ticker: String, numShares: Int, action:String)
//ticker: String, numShares: Option[Int], action: Option[String]
