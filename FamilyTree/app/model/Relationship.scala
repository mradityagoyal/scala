package model

/**
  * Created by aditygoy on 5/8/2017.
  */
case class Relationship(source: PersonId, destination: PersonId, relationType: RelationType)

sealed trait RelationType {
  def label: String
}

object Spouse extends RelationType{
  override def label = "Spouse"
}

object ParentOf extends RelationType{
  override def label = "Parent"
}

//sealed trait ChildOf extends RelationType

//object Husband extends Spouse {
//  override def label: String = "Husband"
//}
//
//object Wife extends Spouse {
//  override def label: String = "Wife"
//}

//object Father extends ParentOf {
//  override def label: String = "Father"
//}
//
//object Mother extends ParentOf {
//  override def label: String = "Mother"
//}

//object Son extends ChildOf {
//  override def label: String = "Son"
//}
//
//object Daughter extends ChildOf {
//  override def label: String = "Daughter"
//}


