package services

import javax.inject.Singleton

import model._

import scala.util.{Failure, Success, Try}

/**
  * Created by Aditya Goyal on 5/8/2017.
  */
@Singleton
class FamilyService {

  var members: List[Person] = List()
  var relations: List[Relationship] = List()


  /**
    *
    * @param p person
    * @return the parents of P
    */
  def getParents(p: Person): List[Person] = {
    relations.filter(relation => relation.destination == p.id && relation.relationType == ParentOf).map(_.source)
      .map(getPersonById).flatten
  }

  /**
    *
    * @param p person
    * @return the children of P
    */
  def getChildren(p: Person): List[Person] = {
    relations.filter(relation => relation.source == p.id && relation.relationType == ParentOf).map(_.destination)
      .map(getPersonById).flatten
  }

  /**
    *
    * @param p person
    * @return Father(s) of P
    */
  def getFather(p: Person): List[Person] = getParents(p).filter(_.sex == Male)

  /**
    *
    * @param p person
    * @return Mother(s) of P
    */
  def getMother(p: Person): List[Person] = getParents(p).filter(_.sex == Female)

  /**
    *
    * @param p person
    * @return Spouses of p
    */
  def getSpouse(p: Person): List[Person] = {
    relations.filter(relation => relation.relationType == Spouse && (relation.source == p.id || relation.destination == p.id))
      .map(relation => if (relation.source == p.id) relation.destination else relation.source).map(getPersonById).flatten
  }

  /**
    *
    * @param p person
    * @return the husbands of p
    */
  def getHusband(p: Person): List[Person] = getSpouse(p).filter(_.sex == Male)

  /**
    *
    * @param p person
    * @return Wife(s) of P
    */
  def getWife(p: Person): List[Person] = getSpouse(p).filter(_.sex == Female)

  def addMember(that: Person): Unit = {
    members = members.::(that)
  }

  def addRelationship(that: Relationship): Unit = {
    relations = relations.::(that)
  }

  def getPersonById(id: PersonId): Option[Person] = {
    members.filter(_.id == id) match {
      case p :: Nil => Some(p)
      case _ => None
    }
  }
}
