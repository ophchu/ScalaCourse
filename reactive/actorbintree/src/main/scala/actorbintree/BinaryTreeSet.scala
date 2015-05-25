/**
 * Copyright (C) 2009-2013 Typesafe Inc. <http://www.typesafe.com>
 */
package actorbintree

import actorbintree.BinaryTreeNode.{CopyTo, CopyFinished}
import actorbintree.BinaryTreeSet._
import akka.actor._

import scala.collection.immutable.Queue
import scala.util.Random

object BinaryTreeSet {

  trait Operation {
    def requester: ActorRef

    def id: Int

    def elem: Int
  }

  trait OperationReply {
    def id: Int
  }

  /** Request with identifier `id` to insert an element `elem` into the tree.
    * The actor at reference `requester` should be notified when this operation
    * is completed.
    */
  case class Insert(requester: ActorRef, id: Int, elem: Int) extends Operation

  /** Request with identifier `id` to check whether an element `elem` is present
    * in the tree. The actor at reference `requester` should be notified when
    * this operation is completed.
    */
  case class Contains(requester: ActorRef, id: Int, elem: Int) extends Operation

  /** Request with identifier `id` to remove the element `elem` from the tree.
    * The actor at reference `requester` should be notified when this operation
    * is completed.
    */
  case class Remove(requester: ActorRef, id: Int, elem: Int) extends Operation

  /** Request to perform garbage collection */
  case object GC

  /** Holds the answer to the Contains request with identifier `id`.
    * `result` is true if and only if the element is present in the tree.
    */
  case class ContainsResult(id: Int, result: Boolean) extends OperationReply

  /** Message to signal successful completion of an insert or remove operation. */
  case class OperationFinished(id: Int) extends OperationReply

}


class BinaryTreeSet extends Actor with ActorLogging {

  import BinaryTreeSet._

  def createRoot: ActorRef = context.actorOf(BinaryTreeNode.props(0, initiallyRemoved = true))

  var root = createRoot

  // optional
  var pendingQueue = Queue.empty[Operation]

  // optional
  def receive = normal

  // optional
  /** Accepts `Operation` and `GC` messages. */
  val normal: Receive = {
    case GC =>
      pendingQueue = Queue.empty[Operation]
      val newRoot = createRoot
      root ! CopyTo(newRoot)
      context become garbageCollecting(newRoot)
    case op: Operation => root ! op
  }

  // optional
  /** Handles messages while garbage collection is performed.
    * `newRoot` is the root of the new binary tree where we want to copy
    * all non-removed elements into.
    */
  def garbageCollecting(newRoot: ActorRef): Receive = {
    case op: Operation =>
      pendingQueue = pendingQueue enqueue op
    case CopyFinished =>
      pendingQueue.foreach(op => newRoot ! op)
      val tmpRoot = root
      root = newRoot
      tmpRoot ! PoisonPill
      context become normal
  }
}

object BinaryTreeNode {

  trait Position

  case object Left extends Position

  case object Right extends Position

  case class CopyTo(treeNode: ActorRef)

  case object CopyFinished

  case object CheckFinished


  def props(elem: Int, initiallyRemoved: Boolean) = Props(classOf[BinaryTreeNode], elem, initiallyRemoved)
}

class BinaryTreeNode(val elem: Int, initiallyRemoved: Boolean) extends Actor with ActorLogging {

  import BinaryTreeNode._

  var subtrees = Map[Position, ActorRef]()
  var removed = initiallyRemoved

  val rndIdForRemove: Int = Random.nextInt(1000000)

  // optional
  def receive = normal

  // optional
  /** Handles `Operation` messages and `CopyTo` requests. */
  val normal: Receive = {
    case Insert(req, id, reqElem) =>
      if (elem == reqElem) {
        removed = false
        req ! OperationFinished(id)
      } else handleInsert(compare(elem, reqElem), req, id, reqElem)
    case Contains(req, id, reqElem) =>
      if (elem == reqElem) {
        req ! ContainsResult(id, !removed)
      } else handleContains(compare(elem, reqElem), req, id, reqElem)
    case Remove(req, id, reqElem) =>
      if (elem == reqElem) {
        removed = true
        req ! OperationFinished(id)
      } else handleRemove(compare(elem, reqElem), req, id, reqElem)

    case CopyTo(treeNode) =>
      if (subtrees.isEmpty && removed) {
        context.parent ! CopyFinished
      } else {
        context.become(copying(subtrees.values.toSet, removed))
        subtrees foreach (node => node._2 ! CopyTo(treeNode))
        if (!removed) treeNode ! Insert(self, rndIdForRemove, elem)
      }

  }


  // optional
  /** `expected` is the set of ActorRefs whose replies we are waiting for,
    * `insertConfirmed` tracks whether the copy of this node to the new tree has been confirmed.
    */
  def copying(expected: Set[ActorRef], insertConfirmed: Boolean): Receive = {
    case OperationFinished(id) =>
      if (id == rndIdForRemove) {
        context.become(copying(expected, true))
      }
      self ! CheckFinished

    case CopyFinished =>
      if (expected contains(sender)) sender ! PoisonPill
      context.become(copying(expected.filter(ref => ref != sender), insertConfirmed))
      self ! CheckFinished

    case CheckFinished =>
      if (expected.isEmpty && insertConfirmed) {
        context.parent ! CopyFinished
      }
  }


  def compare(leftElem: Int, rightElem: Int): Position = {
    leftElem < rightElem match {
      case true => Left
      case false => Right
    }
  }

  def handleInsert(pos: Position, req: ActorRef, id: Int, elem: Int) = {
    subtrees.get(pos) match {
      case Some(ref) => ref ! Insert(req, id, elem)
      case None =>
        subtrees += (pos -> context.actorOf(BinaryTreeNode.props(elem, initiallyRemoved = false)))
        req ! OperationFinished(id)
    }
  }

  def handleContains(pos: Position, req: ActorRef, id: Int, elem: Int) = {
    subtrees.get(pos) match {
      case Some(ref) => ref ! Contains(req, id, elem)
      case None => req ! ContainsResult(id, false)
    }
  }


  def handleRemove(pos: Position, req: ActorRef, id: Int, elem: Int) = {
    subtrees.get(pos) match {
      case Some(ref) => ref ! Remove(req, id, elem)
      case None => req ! OperationFinished(id)
    }
  }

  //  @throws[Exception](classOf[Exception])
  //  override def postStop(): Unit = log.info("Actor: {}, elem: {} stopped", self, elem)
}
