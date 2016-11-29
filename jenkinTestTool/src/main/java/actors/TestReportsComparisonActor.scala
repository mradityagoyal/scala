package actors
import akka.actor.Actor
import akka.actor.ActorLogging
import scala.concurrent.Future
import entities.TestReportRO
import entities.TestCaseRO
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.http.scaladsl.Http
import akka.stream.scaladsl.Source
import akka.http.scaladsl.model.HttpRequest
import akka.http.scaladsl.model.Uri
import akka.http.scaladsl.model.ContentTypes
import akka.http.scaladsl.model.Uri.Path
import akka.stream.scaladsl.Sink
import entities.JenkinsJsonSupport
import akka.stream.ActorMaterializer
import util.JenkinsJsonApiClient
import util.TestReportUtils

class TestReportsComparisonActor extends Actor with ActorLogging  {
  
  implicit val system = context.system
  implicit val executionContext = context.dispatcher
  
  override def receive: Receive = {
    case (url: String) =>
      log.debug(url)
      log.debug("received Start")
      val future = TestReportUtils.groupCases(TestReportUtils.fetchTestReport(url))
      //can't close over sender otherwise. 
      val frozenSender = sender
      future onComplete { response =>
        {
          log.debug("future complete, replying")
          frozenSender ! (url, response.get)
        }
      }
  }
}