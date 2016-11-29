package entities

import spray.json.DefaultJsonProtocol
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport

trait JenkinsJsonSupport extends SprayJsonSupport  with DefaultJsonProtocol {
  
  implicit val testCaseROFormat = jsonFormat4(TestCaseRO)
  implicit val testCasesROFormat = jsonFormat1(TestCasesRO)
  implicit val testReportROFormat = jsonFormat5(TestReportRO)
}