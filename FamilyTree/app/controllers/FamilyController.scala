package controllers

import java.util.UUID
import javax.inject.{Inject, Singleton}

import model.{Male, Person, PersonId}
import play.api.mvc._
import services.FamilyService

/**
  * Created by aditygoy on 5/8/2017.
  */
@Singleton
class FamilyController @Inject()(service: FamilyService) extends Controller{


  def listMembers = Action{
    Ok(service.members.mkString(" , "))
  }

  def addMember = Action {
    val p : Person = Person("aditya", "goyal", PersonId(UUID.randomUUID()), Male)
    service.addMember(p)
    Ok("Success")
  }

}
