package util

import scala.collection.immutable.List
import scala.collection.immutable.Map
import scala.concurrent.Future
import scala.util.Success

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import entities.TestCaseRO
import entities.TestReportRO
import scala.util.Failure

object TestReportUtils {

  implicit val system: ActorSystem = ActorSystem()
  implicit val executionContext = system.dispatcher
  implicit val materializer: ActorMaterializer = ActorMaterializer()

  def fetchTestReport(url: String): Future[TestReportRO] = JenkinsJsonApiClient.getTestReport(url)

  //takes a future of a test report and returns the future of list of testCaseRO
  def getTestCases(report: Future[TestReportRO]): Future[List[TestCaseRO]] = report.map(x => x.suites.flatMap(_.cases))

  //takes a future of a test report and returns a future of a map where status is key and list of testCases is value. 
  def groupCases(report: Future[TestReportRO]): Future[Map[String, List[TestCaseRO]]] =
    getTestCases(report).map(c => c.groupBy(_.status))

  def getFailedTestCases(report: Future[TestReportRO]): Future[List[TestCaseRO]] = {
    val grouping = groupCases(report)
    grouping.map(getFailedTestCases(_))
  }

  def getFailedTestCases(grouping: Map[String, List[TestCaseRO]]): List[TestCaseRO] = getTestsByStatus(grouping)(failedTestsPredicate)

  def failedTestsPredicate = (status: String) => {
    status.equals("FAILED") || status.equals("REGRESSION")
  }

  def getTestsByStatus(grouping: Map[String, List[TestCaseRO]])(statusPredicate: String => Boolean): List[TestCaseRO] = grouping.keys.filter(statusPredicate).flatMap { x => grouping(x) }.toList
  /**
   * gets build url b1 and b2
   *  fetches test results and returns a map
   */
    def getCombinations(a: String, b: String): Map[String, List[TestCaseRO]] = {
      val groupingFirst = groupCases(fetchTestReport(a))
      val groupingSecond = groupCases(fetchTestReport(b))
      groupingFirst.value.get match { case Success(grp) => {
        val f1 = getFailedTestCases(grp)
        groupingSecond.value.get match{
          case Success(grp2) => {
            val f2 = getFailedTestCases(grp2)
           Map(("intersection" -> (f1 intersect f2)), ("union" -> (f1 union f2)), ("a - b " -> (f1 diff f2)),
          ("b - a " -> (f2 diff f1)))
          }
          case Failure(f) => Map.empty
        }
      }
      case Failure(f) => Map.empty
      }
    }

}