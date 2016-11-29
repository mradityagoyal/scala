

import java.util.concurrent.TimeUnit

import scala.concurrent.duration.Duration

import org.scalatest.BeforeAndAfterAll
import org.scalatest.Finders
import org.scalatest.Matchers
import org.scalatest.WordSpecLike

import akka.actor.ActorSystem
import akka.testkit.ImplicitSender
import akka.testkit.TestKit
import akka.util.Timeout
import akka.actor.Props
import actors.TestReportsComparisonActor
import akka.testkit.TestActorRef
import actors.TestReportsComparisonActor
import entities.TestCaseRO
import akka.pattern.ask
import scala.util.Success

class ActorTest extends TestKit(ActorSystem("MySpec")) with ImplicitSender
    with WordSpecLike with Matchers with BeforeAndAfterAll {

  implicit val timeout = Timeout(Duration.apply(100, TimeUnit.SECONDS))
  implicit val executionContext = system.dispatcher

  override def afterAll {
    TestKit.shutdownActorSystem(system)
  }
  
  "A ReportComparisonActor " must {
    "reply with a map" in {
      val reportCompariosnActor = TestActorRef[TestReportsComparisonActor]
      val url = "/view/Platform/job/Platform_c1_Intg_Manual/26" + "/testReport/api/json"
      reportCompariosnActor ! url
      val reply = receiveOne(Duration(10 , TimeUnit.SECONDS))
      val r = reply match {
        case (recUrl: String, grouping: Map[String, List[TestCaseRO]]) =>{
          assert(recUrl == url, "url is equal")
          println(grouping("FAILED"))
          assert(!grouping.isEmpty, "grouping should not be empty")
        }
      }
    }
  }
  
//  val reply1 = reportCompariosnActor ? url

}