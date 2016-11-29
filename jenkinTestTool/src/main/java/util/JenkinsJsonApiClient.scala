package util

import entities.TestReportRO
import entities.TestCaseRO
import entities.JenkinsJsonSupport
import spray.json._
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import scala.concurrent.Future
import akka.http.scaladsl.model.HttpRequest
import akka.http.scaladsl.model.Uri
import akka.http.scaladsl.model.Uri.Path
import akka.stream.scaladsl.Source
import akka.http.scaladsl.Http
import akka.stream.scaladsl.Sink
import akka.http.scaladsl.model.ContentTypes
import akka.stream.scaladsl.Flow

object JenkinsJsonApiClient extends JenkinsJsonSupport {
  implicit val system: ActorSystem = ActorSystem("JenkinsApiClientSystem")
  implicit val materializer = ActorMaterializer()
  implicit val executionContext = system.dispatcher

  //pretty=true
  val queryParam = "tree=duration,empty,failCount,passCount,skipCount,suites[cases[className,status,skipped,name]]"


  def getTestReport(path: String): Future[TestReportRO] = {
    val request = HttpRequest(uri = Uri(path = Path(path)).withQuery(Uri.Query(queryParam)))
    val source = Source.single(request)
    val flow = Http().outgoingConnection("pgbuci.us.oracle.com", 8080).mapAsync(1) { r =>
      Unmarshal(r.entity.withContentType(ContentTypes.`application/json`)).to[TestReportRO]
    }
    source.via(flow).runWith(Sink.head)
  }


}