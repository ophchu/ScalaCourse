package kvstore

import akka.actor._
import akka.util.Timeout
import kvstore.Persistence.{Persisted, Persist}
import scala.util.Random
import scala.concurrent.duration._

object PersistenceManager {

  case class PersistSuccess(id: Long)

  case class PersistFailed(id: Long)

  def props(persistenceProps: Props): Props = Props(classOf[Persistence], persistenceProps)


}

class PersistenceManager(persistenceProps: Props) extends Actor {

  import PersistenceManager._

  override val supervisorStrategy = SupervisorStrategy.stoppingStrategy

  implicit val timeout = Timeout(1 seconds)

  var sender2PersisterMap = Map.empty[ActorRef, (ActorRef, Long)]


  def receive = {
    case persist: Persist =>
      val persister = context.actorOf(persistenceProps)
      sender2PersisterMap += persister ->(sender, persist.id)
      //todo do we want forward???
      persister ! persist
      context.watch(persister)

    case Persisted(key, id) =>
      sender2PersisterMap.get(sender).foreach(_._1 ! PersistSuccess(id))
      sender2PersisterMap -= sender

    case Terminated(persister) =>
      sender2PersisterMap.get(sender).foreach(elem => elem._1 ! PersistFailed(elem._2))
      sender2PersisterMap -= persister
  }
}

object Persistence {

  case class Persist(key: String, valueOption: Option[String], id: Long)

  case class Persisted(key: String, id: Long)

  class PersistenceException extends Exception("Persistence failure")

  def props(flaky: Boolean): Props = Props(classOf[Persistence], flaky)
}

class Persistence(flaky: Boolean) extends Actor {

  import Persistence._

  def receive = {
    case Persist(key, _, id) =>
      if (!flaky || Random.nextBoolean()) sender ! Persisted(key, id)
      else throw new PersistenceException
  }

}
