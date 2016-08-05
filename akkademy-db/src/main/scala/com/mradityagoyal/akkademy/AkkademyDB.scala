package com.mradityagoyal.akkademy

import akka.actor.Actor
import scala.collection.mutable.HashMap
import akka.event.Logging
import com.mradityagoyal.akkademy.messages.SetRequest

class AkkademyDB extends Actor {

  val map = new HashMap[String, Object]
  val log = Logging(context.system, this)
  override def receive = {
    case SetRequest(key, value) => {
      log.info("reveived SetRequest - key: {} value : {}", key, value)
      map.put(key, value)
    } case o => log.info("reveived unknown message: {}", o);
  }

}