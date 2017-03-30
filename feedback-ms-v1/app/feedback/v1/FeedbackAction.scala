package feedback.v1

import model.Question

object FeedbackAction {

  /**
   * returns a list of questios for a user and page.
   * @param user
   * @param page
   * @return questions to be asked to the user
   */
  def getQuestion(user: String, page: String): Option[Iterable[Question]] = {
    Some(List("On a scale of 1 to 10, how satisfied are you with this page?",
        "Comments? " ,
        "Fill Survey Link").map { text => Question(text, "") })
  }

}