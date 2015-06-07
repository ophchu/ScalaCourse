package kvstore

import akka.actor.SupervisorStrategy.{Stop, Restart}
import akka.actor._
import akka.util.Timeout
import kvstore.Persistence.{PersistenceException, Persist, Persisted}
import scala.concurrent.duration._

object Replicator {

  case class Replicate(key: String, valueOption: Option[String], id: Long)

  case class Replicated(key: String, id: Long)

  case class Snapshot(key: String, valueOption: Option[String], seq: Long)

  case class SnapshotAck(key: String, seq: Long)

  def props(replica: ActorRef): Props = Props(new Replicator(replica))
}

class Replicator(val replica: ActorRef) extends Actor with ActorLogging {

  import Replicator._
  import Replica._
  import context.dispatcher
  import akka.actor.SupervisorStrategy._
  import Persistence._

  /*
   * The contents of this actor is just a suggestion, you can implement it in any way you like.
   */

  // map from sequence number to pair of sender and request
  var acks = Map.empty[Long, (ActorRef, Replicate)]
  // a sequence of not-yet-sent snapshots (you can disregard this if not implementing batching)
  var pending = Vector.empty[Snapshot]

  var _seqCounter = 0L

  def nextSeq = {
    val ret = _seqCounter
    _seqCounter += 1
    ret
  }

  context.system.scheduler.schedule(100 millisecond, 100 millisecond) {
    acks foreach {
      case (seq, (sender, replicate)) =>
        replica ! Snapshot(replicate.key, replicate.valueOption, seq)
    }
  }

  /* TODO Behavior for the Replicator. */
  def receive: Receive = {
    case Replicate(key, value, id) =>
      log.info("Got Replicate {}", key)
      val currentSeq = nextSeq
      acks += currentSeq ->(sender, Replicate(key, value, id))
      replica ! Snapshot(key, value, currentSeq)
    case SnapshotAck(key, seq) =>
      acks.get(seq).foreach(elem => elem._1 ! Replicated(elem._2.key, elem._2.id))
      acks -= seq
    case _ =>
  }

}
