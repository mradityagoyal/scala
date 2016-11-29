
import org.scalatest.FunSuite
import org.scalatest.concurrent.ScalaFutures
import scala.concurrent.Future

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import util.JenkinsJsonApiClient
import org.scalatest.time.Seconds
import org.scalatest.time.Millis
import org.scalatest.time.Span

@RunWith(classOf[JUnitRunner])
class JenkinsJsonApiClientTestSuite extends FunSuite with ScalaFutures {

  val apiSuffix = "/testReport/api/json"
  val b1 = "http://pgbuci.us.oracle.com:8080/view/Platform/job/Platform_c1_Intg_Manual/26"
  val b2 = "http://pgbuci.us.oracle.com:8080/view/Platform/job/Platform_c1_Intg_Manual/28"

  
  implicit val defailtPatience = PatienceConfig(timeout = Span(5, Seconds), interval = Span(500, Millis))
  
  val url = "/view/Platform/job/Platform_c1_Intg_Manual/26" + apiSuffix
  
  test("API Client works") {
    val testReportFuture = JenkinsJsonApiClient.getTestReport(url)
    whenReady(testReportFuture) { report =>
      {
        assert(!report.empty, "report should not be empty")
        assert(report.failCount == 12 , "fail count is 12")
        assert(report.passCount == 4274,  "pass shoudl be 4274")
        assert(report.skipCount == 103,  "skipped shoudl be 103")
        
      }
    }
  }

}