package model

import java.util.UUID

/**
  * Created by aditygoy on 5/8/2017.
  * Acts as nodes in the graph.
  */
case class Person(firstName: String, lastName: String, id: PersonId, sex: Sex)

case class PersonId(id: UUID)


sealed trait Sex{}
object Male extends Sex
object Female extends Sex
object Other extends Sex