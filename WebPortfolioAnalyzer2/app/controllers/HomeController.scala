package controllers

import javax.inject._

import model.F01KTransaction
import play.api._
import play.api.mvc._

/**
  * This controller creates an `Action` to handle HTTP requests to the
  * application's home page.
  */
@Singleton
class HomeController @Inject() extends Controller {

  /**
    * Create an Action to render an HTML page with a welcome message.
    * The configuration in the `routes` file means that this method
    * will be called when the application receives a `GET` request with
    * a path of `/`.
    */
  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def transactions = Action {

    //    Ok(views.html.transactions("Hello world"))
    val path = "resources/401K/401KHistory1May2015to18May2017.csv"

    val transactions: List[F01KTransaction] = F01KTransaction.fromFile(path)
//    val messages : List[F01KTransaction] = List("Aditya", "Goyal", "Trying")
    Ok(views.html.trans2("401K Transactions ", transactions))
  }

}
