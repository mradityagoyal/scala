package controllers.api

import javax.inject.{Inject, Singleton}

import play.api.libs.json._
import play.api.mvc.{Action, Controller}
import services.IRAFileService

/**
  * Created by agoyal on 5/26/17.
  */
@Singleton
class IraApiController @Inject()(iraService: IRAFileService) extends Controller{


  def transactions = Action {
    Ok(Json.toJson(iraService.transactions))
  }

}
