package model

import play.api.libs.json.Writes
import play.api.libs.json.JsValue
import play.api.libs.json.Json

case class Question(text: String, response: String)

object Question {

  /**
    * Mapping to write a Question out as a JSON value.
    */
  implicit val implicitWrites = new Writes[Question] {
    def writes(question: Question): JsValue = {
      Json.obj(
        "text" -> question.text,
        "response" -> question.response
      )
    }
  }
}