package kvstore

import akka.actor._
import kvstore.Arbiter._
import kvstore.Persistence.{Persisted, Persist, PersistenceException}
import scala.collection.immutable.Queue
import akka.actor.SupervisorStrategy.{Stop, Restart}
import scala.annotation.tailrec
import akka.pattern.{ask, pipe}
import scala.concurrent.duration._
import akka.util.Timeout

object Replica {

  sealed trait Operation {
    def key: String

    def id: Long
  }

  case class Insert(key: String, value: String, id: Long) extends Operation

  case class Remove(key: String, id: Long) extends Operation

  case class Get(key: String, id: Long) extends Operation

  sealed trait OperationReply

  case class OperationAck(id: Long) extends OperationReply

  case class OperationFailed(id: Long) extends OperationReply

  case class GetResult(key: String, valueOption: Option[String], id: Long) extends OperationReply

  def props(arbiter: ActorRef, persistenceProps: Props): Props = Props(new Replica(arbiter, persistenceProps))
}

class Replica(val arbiter: ActorRef, persistenceProps: Props) extends Actor with ActorLogging {

  import Replica._
  import Replicator._
  import Persistence._
  import context.dispatcher
  import PersistenceManager._

  /*
   * The contents of this actor is just a suggestion, you can implement it in any way you like.
   */

  val persistenceManager = context.actorOf(PersistenceManager.props(persistenceProps))
  var kv = Map.empty[String, String]
  // a map from secondary replicas to replicators
  var secondaries = Map.empty[ActorRef, ActorRef]
  // the current set of replicators
  var replicators = Set.empty[ActorRef]


  var persistMap = Map.empty[String, (ActorRef, Long)]

  var _seqCounter = 0L

  def nextSeq = {
    val ret = _seqCounter
    _seqCounter += 1
    ret
  }

  arbiter ! Join

  def receive = {
    case JoinedPrimary => context.become(leader)
    case JoinedSecondary => context.become(replica)
  }

  /* TODO Behavior for  the leader role. */
  val leader: Receive = {
    case Insert(key, value, id) =>
      kv += key -> value
      secondaries foreach (_._2 ! Replicate(key, Some(value), id))

      sender ! OperationAck(id)
    case Remove(key, id) =>
      kv -= key
      secondaries foreach (_._2 ! Replicate(key, None, id))
      sender ! OperationAck(id)
    case Get(key, id) =>
      sender ! GetResult(key, kv.get(key), id)
    case Replicas(replicas) =>
      val newSecondary = replicas filterNot (rep => rep == self) intersect secondaries.keySet head
      val cooReplactor = context.actorOf(Replicator.props(newSecondary))
      context.watch(newSecondary)

      secondaries += newSecondary -> cooReplactor
      replicators += cooReplactor

      //todo how to validate the approved id
      kv foreach (kvPairs => cooReplactor ! Replicate(kvPairs._1, Some(kvPairs._2), nextSeq))

    case Terminated(replicator) =>
      secondaries.get(replicator).foreach(context.stop(_))
      secondaries -= replicator
      replicators -= replicator

    case _ =>
  }

  var seqCounter = 0L
  /* TODO Behavior for the replica role. */
  val replica: Receive = {
    case Get(key, id) =>
      sender ! GetResult(key, kv.get(key), id)
    case Snapshot(key, value, seq) =>
      log.info("[r] Got snapshot: {}", key)
      if (seqCounter > seq) {
        sender ! SnapshotAck(key, seq)
      } else if (seqCounter == seq) {
        value match {
          case None => kv -= key
            log.info("[r] None: {}", key)
          case Some(someValue) =>
            log.info("[r] Some: {}", key)
            kv += key -> someValue
        }
        seqCounter += 1

        persistenceManager ! Persist(key, value, seq)
        //        val persister = context.actorOf(persistenceProps)
        //        persister ! Persist(key, value, seq)
        persistMap += key ->(sender, seq)
      }


    case PersistSuccess(key) =>
      log.info("[r] Persisten success: {}", key)
      persistMap.get(key).foreach(elem => elem._1 ! SnapshotAck(key, elem._2))
      persistMap -= key

    case _ =>
  }

}






