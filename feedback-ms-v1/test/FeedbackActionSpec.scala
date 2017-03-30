import org.scalatest.FlatSpec
import org.scalatest.Matchers
import feedback.v1.FeedbackAction


class FeedbackActionSpec extends FlatSpec with Matchers{
  
  "A FeedbackAction" should "return a non empty list of questions" in {
    assert(!FeedbackAction.getQuestion("test", "test").isEmpty)
  }
  
}