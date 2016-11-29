

import org.scalatest.FunSuite
import org.scalatest.concurrent.ScalaFutures
import scala.concurrent.Future

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import util.JenkinsJsonApiClient
import org.scalatest.time.Seconds
import org.scalatest.time.Millis
import org.scalatest.time.Span
import org.scalatest.concurrent.PatienceConfiguration.PatienceConfigParam
import util.TestReportUtils
import akka.testkit.TestActorRef
import actors.TestReportsComparisonActor
import entities.TestCaseRO
import java.util.concurrent.TimeUnit
import scala.concurrent.duration.Duration
import akka.actor.ActorSystem
import scala.util.Success
import akka.util.Timeout

@RunWith(classOf[JUnitRunner])
class TestReportUtilsTestSuite extends FunSuite with ScalaFutures {
  

  implicit val defailtPatience = PatienceConfig(timeout = Span(5, Seconds), interval = Span(500, Millis))
  val apiSuffix = "/testReport/api/json"

  test("ReportUtils  fetchTestReport works") {
    val url = "/view/Platform/job/Platform_c1_Intg_Manual/26" + apiSuffix
    val testReportFuture = TestReportUtils.fetchTestReport(url)

    whenReady(testReportFuture) { report =>
      {
        assert(!report.empty, "report should not be empty")
        assert(report.failCount == 12, "fail count is 12")
        assert(report.passCount == 4274, "pass shoudl be 4274")
        assert(report.skipCount == 103, "skipped shoudl be 103")

      }

    }
  }

  test("ReportUtils  testReportFuture works") {
    val url = "/view/Platform/job/Platform_c1_Intg_Manual/26" + apiSuffix
    val testReportFuture = TestReportUtils.fetchTestReport(url)
    val testCasesFuture = TestReportUtils.getTestCases(testReportFuture)
    whenReady(testCasesFuture) { cases =>
      {
        assert(!cases.isEmpty, "cases should not be empty")
        assert(cases.size == 4389, "size should be 4389")
      }
    }
    
    val failedCasesFuture = TestReportUtils.getFailedTestCases(testReportFuture)
    
    whenReady(failedCasesFuture) { cases =>
      {
        assert(!cases.isEmpty, "cases should not be empty")
        assert(cases.size == 12, "failed size should be 12")
      }
    }
    
    val groupingsFuture = TestReportUtils.groupCases(testReportFuture)
    whenReady(groupingsFuture) { group =>
      {
        assert(!group.isEmpty, "cases should not be empty")
        assert(group("SKIPPED").size == 103, "skipped size should be 103")
      }
    }
    
    
  }
  
  test("everything works") {
     val url1 = "/view/Platform/job/Platform_c1_Intg_Manual/26" + apiSuffix
     val url2 = "/view/Platform/job/Platform_c1_Intg_Manual/26" + apiSuffix
     val combinations = TestReportUtils.getCombinations(url1, url2)
     println(combinations)
  }
  

}