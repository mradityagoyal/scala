package com.mradityagoyal.akkademy

import org.scalatest.FunSpecLike
import org.scalatest.Matchers
import org.scalatest.BeforeAndAfterEach
import akka.actor.ActorSystem
import com.mradityagoyal.akkademy.messages.SetRequest
import akka.testkit.TestActorRef

class AkkademyDbSpec extends FunSpecLike with Matchers with BeforeAndAfterEach {
  implicit val system = ActorSystem()
  
  describe("akkademyDB") {
    describe("given SetRequest") {
      it("should place key/value into map") {
        val actorRef = TestActorRef(new AkkademyDB) 
        actorRef ! SetRequest("key", "value")
        val akkademyDb = actorRef.underlyingActor 
        akkademyDb.map.get("key") should equal(Some("value"))
      }
    }
  }
}