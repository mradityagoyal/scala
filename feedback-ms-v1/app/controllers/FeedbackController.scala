package controllers

import play.api.mvc.Controller
import feedback.v1.FeedbackAction
import play.api.mvc.Action
import play.api.libs.json.Json
import model.Question

class FeedbackController extends Controller{
  
  
  /**
   * returns a question to ask. 
   */
  def getQuestions(user: String, page: String) = Action{
    val maybeQuestions: Option[Iterable[Question]] = FeedbackAction.getQuestion(user, page)
    
    maybeQuestions match {
      case Some(questions) => Ok(Json.toJson(questions))
      case None => Ok(Json.toJson(Question("Nothing to ask", "none")))
    }
  }
  
}