package kvstore

import akka.actor._
import akka.util.Timeout
import kvstore.Persistence.{Persisted, Persist}
import scala.util.Random
import scala.concurrent.duration._


object Persistence {

  case class Persist(key: String, valueOption: Option[String], id: Long)

  case class Persisted(key: String, id: Long)

  class PersistenceException extends Exception("Persistence failure")

  def props(flaky: Boolean): Props = Props(classOf[Persistence], flaky)
}

class Persistence(flaky: Boolean) extends Actor with ActorLogging{

  import Persistence._

  def receive = {
    case Persist(key, _, id) =>
      if (!flaky || Random.nextBoolean()) {
        log.info("[e] to persist: {}", id)
        sender ! Persisted(key, id)
      }else {
        log.info("[e] not to persist: {}", id)
        throw new PersistenceException
      }
    case _ =>
      log.info("[e] someting else")
  }

}
