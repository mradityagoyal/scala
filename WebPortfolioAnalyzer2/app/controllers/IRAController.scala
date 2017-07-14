package controllers

import javax.inject.{Inject, Singleton}

import play.api.mvc.{Action, Controller}
import play.api.libs.json._
import services.{IRAFileService, IRAService}

/**
  * Created by agoyal on 5/26/17.
  */
@Singleton
class IRAController @Inject()(iraService: IRAFileService) extends Controller{


  def transactions = Action {
    Ok(views.html.ira.iratransactions("hello", iraService.transactions))
  }

}
