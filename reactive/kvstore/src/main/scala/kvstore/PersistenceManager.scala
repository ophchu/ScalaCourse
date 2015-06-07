package kvstore

/**
 * Created by ophchu on 6/7/15.
 */

import akka.actor._
import akka.actor.SupervisorStrategy.{Restart, Stop}
import akka.util.Timeout
import kvstore.Persistence.{Persisted, PersistenceException, Persist}
import org.slf4j.LoggerFactory
import scala.concurrent.duration._

object PersistenceManager {

  sealed trait PersistResult {
    val key: String
  }

  case class PersistSuccess(key: String) extends PersistResult

  case class PersistFailed(key: String) extends PersistResult


  def props(persistenceProps: Props): Props = Props(classOf[PersistenceManager], persistenceProps)
}

class PersistenceManager(persistenceProps: Props) extends Actor with ActorLogging{

  import PersistenceManager._

  var id2persistRequests = Map.empty[Long, ActorRef]

  implicit val timeout = Timeout(100 millisecond)

//  override val supervisorStrategy = OneForOneStrategy() {
//    case ex: PersistenceException =>
//      log.info("[p] failing: {}", ex.getMessage)
//      Restart
//    case _ => Stop
//  }

  def receive = {
    case persist: Persist =>
      log.info("[p] to persist: {}", persist.id)
      val persister = context.actorOf(persistenceProps)
      context.watch(persister)
      id2persistRequests += persist.id -> sender
      persister ! persist
//      context.watch(persister)

    case Persisted(key, id) =>
      log.info("[p] persisted: {}", id)
      id2persistRequests.get(id).foreach(_ ! PersistSuccess(key))
      id2persistRequests -= id

    case Terminated(persister) =>
      log.info("[p] terminated")
    case _ =>
      log.info("[p] got this: {}")
    //    case Terminated(persister) =>
    //      sender2PersisterMap.get(sender).foreach(elem => elem._1 ! PersistFailed(elem._2))
    //      sender2PersisterMap -= persister
  }
}

