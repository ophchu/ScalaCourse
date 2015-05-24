/**
 * Copyright (C) 2009-2015 Typesafe Inc. <http://www.typesafe.com>
 */
package actorbintree

import akka.actor.{ActorRef, ActorSystem, Props}
import akka.testkit.{ImplicitSender, TestKit, TestProbe}
import org.scalatest.{BeforeAndAfter, BeforeAndAfterAll, FunSuiteLike, Matchers}

import scala.concurrent.duration._
import scala.util.Random

class BinaryTreeSuite(_system: ActorSystem) extends TestKit(_system)
with FunSuiteLike
with Matchers with
BeforeAndAfterAll
with ImplicitSender
with BeforeAndAfter {

  var ids: Iterator[Int] = _
  before {
    ids = Stream.from(200).iterator
  }

  def this() = this(ActorSystem("BinaryTreeSuite"))

  override def afterAll: Unit = system.shutdown()

  import actorbintree.BinaryTreeSet._

  def receiveN(requester: TestProbe, ops: Seq[Operation], expectedReplies: Seq[OperationReply]): Unit =
    requester.within(5.seconds) {
      val repliesUnsorted = for (i <- 1 to ops.size) yield try {
        requester.expectMsgType[OperationReply]
      } catch {
        case ex: Throwable if ops.size > 10 => fail(s"failure to receive confirmation $i/${ops.size}", ex)
        case ex: Throwable => fail(s"failure to receive confirmation $i/${ops.size}\nRequests:" + ops.mkString("\n    ", "\n     ", ""), ex)
      }
      val replies = repliesUnsorted.sortBy(_.id)
      if (replies != expectedReplies) {
        val pairs = (replies zip expectedReplies).zipWithIndex filter (x => x._1._1 != x._1._2)
        fail("unexpected replies:" + pairs.map(x => s"at index ${x._2}: got ${x._1._1}, expected ${x._1._2}").mkString("\n    ", "\n    ", ""))
      }
    }

  def verify(probe: TestProbe, ops: Seq[Operation], expected: Seq[OperationReply]): Unit = {
    val topNode = system.actorOf(Props[BinaryTreeSet])

    ops foreach { op =>
      topNode ! op
    }

    receiveN(probe, ops, expected)
    // the grader also verifies that enough actors are created
  }

  test("Insert only test") {
    val topNode = system.actorOf(Props[BinaryTreeSet])

    topNode ! Insert(testActor, id = 1, 1)
    expectMsg(OperationFinished(1))

    topNode ! Insert(testActor, id = 2, 2)
    expectMsg(OperationFinished(2))

  }

  test("Remove only test") {
    val topNode = system.actorOf(Props[BinaryTreeSet])

    topNode ! Insert(testActor, id = 1, 1)
    expectMsg(OperationFinished(1))

    topNode ! Remove(testActor, id = 2, 1)
    expectMsg(OperationFinished(2))

    topNode ! Contains(testActor, id = 3, 1)
    expectMsg(ContainsResult(3, false))
  }

  test("proper inserts and lookups") {
    val topNode = system.actorOf(Props[BinaryTreeSet])

    topNode ! Contains(testActor, id = 1, 1)
    expectMsg(ContainsResult(1, false))

    topNode ! Insert(testActor, id = 2, 1)
    topNode ! Contains(testActor, id = 3, 1)

    expectMsg(OperationFinished(2))
    expectMsg(ContainsResult(3, true))
  }

  test("proper inserts, lookups and removes") {
    val topNode = system.actorOf(Props[BinaryTreeSet])

    topNode ! Contains(testActor, id = 1, 1)
    expectMsg(ContainsResult(1, false))

    topNode ! Insert(testActor, id = 2, 1)
    topNode ! Contains(testActor, id = 3, 1)

    expectMsg(OperationFinished(2))
    expectMsg(ContainsResult(3, true))

    topNode ! Insert(testActor, id = 4, 1)
    topNode ! Contains(testActor, id = 5, 2)
    expectMsg(OperationFinished(4))
    expectMsg(ContainsResult(5, false))

    topNode ! Remove(testActor, id = 6, 1)
    topNode ! Contains(testActor, id = 7, 1)
    expectMsg(OperationFinished(6))
    expectMsg(ContainsResult(7, false))

    topNode ! Insert(testActor, id = 8, 1)
    topNode ! Contains(testActor, id = 9, 1)
    expectMsg(OperationFinished(8))
    expectMsg(ContainsResult(9, true))

  }

  test("test simple GC") {
    val topNode = system.actorOf(Props[BinaryTreeSet])
    insertCheck(topNode, 3)
    containsCheck(topNode, ids.next, 20, true)
    removeCheck(topNode, ids.next, 20)
    containsCheck(topNode, ids.next, 20, false)
    topNode ! GC
    containsCheck(topNode, ids.next, 10, true)
    containsCheck(topNode, ids.next, 20, false)
    containsCheck(topNode, ids.next, 30, true)
  }
  test("test two sides tree") {
    val topNode = system.actorOf(Props[BinaryTreeSet])
    val inVals = List(-4, 5, -10,-12, -2, 3, 10, 15)

    val nonInVals = List(-20, -5, -11, -1, 4, 20)
    inVals foreach (ins => insert(topNode, ins))

//    inVals foreach (ins => contains(topNode, ins, true))
//    nonInVals foreach (ins => contains(topNode, ins, false))

    val toRemVals = List(-4, -2, 5, 20)
    toRemVals foreach (rm => remove(topNode, rm))

//    inVals diff toRemVals foreach (ins => contains(topNode, ins, true))
//    toRemVals foreach (ins => contains(topNode, ins, false))
//    nonInVals foreach (ins => contains(topNode, ins, false))
    topNode ! GC

    contains(topNode, -10, true)
//    inVals diff toRemVals foreach (ins => contains(topNode, ins, true))
//    toRemVals foreach (ins => contains(topNode, ins, false))
//    nonInVals foreach (ins => contains(topNode, ins, false))

  }

  def remove(node: ActorRef, value: Int) = {
    val id = ids.next
    node ! Remove(testActor, id, value)
    expectMsg(OperationFinished(id))
  }
  def insert(node: ActorRef, value: Int) = {
    val id = ids.next
    node ! Insert(testActor, id, value)
    expectMsg(OperationFinished(id))
  }

  def contains(node: ActorRef, value: Int, res: Boolean) = {
    containsCheck(node, ids.next, value, res)
  }
  def containsCheck(node: ActorRef, id: Int, value: Int, res: Boolean) = {
    println(s"Contains: $id:$value:$res")
    node ! Contains(testActor, id, value)
    expectMsg(ContainsResult(id, res))
  }
  def removeCheck(node: ActorRef, id: Int, value: Int) = {
    node ! Remove(testActor, id, value)
    expectMsg(OperationFinished(id))
  }
  def insertCheck(node: ActorRef, insertionNum: Int) = {
    for {
      i <- 1 to insertionNum
    } node ! Insert(testActor, i, i * 10)

    for (i <- 1 to insertionNum) {
      expectMsg(OperationFinished(i))
    }

    for (i <- 1 to insertionNum) {
      node ! Contains(testActor, i+insertionNum, i * 10)
    }
    for (i <- 1 to insertionNum) {
      expectMsg(ContainsResult(i+insertionNum, true))
    }
  }

  test("instruction example") {
    val requester = TestProbe()
    val requesterRef = requester.ref
    val ops = List(
      Insert(requesterRef, id = 100, 1),
      Contains(requesterRef, id = 50, 2),
      Remove(requesterRef, id = 10, 1),
      Insert(requesterRef, id = 20, 2),
      Contains(requesterRef, id = 80, 1),
      Contains(requesterRef, id = 70, 2)
    )

    val expectedReplies = List(
      OperationFinished(id = 10),
      OperationFinished(id = 20),
      ContainsResult(id = 50, false),
      ContainsResult(id = 70, true),
      ContainsResult(id = 80, false),
      OperationFinished(id = 100)
    )

    verify(requester, ops, expectedReplies)
  }

  test("behave identically to built-in set (includes GC)") {
    val rnd = new Random()
    def randomOperations(requester: ActorRef, count: Int): Seq[Operation] = {
      def randomElement: Int = rnd.nextInt(100)
      def randomOperation(requester: ActorRef, id: Int): Operation = rnd.nextInt(4) match {
        case 0 => Insert(requester, id, randomElement)
        case 1 => Insert(requester, id, randomElement)
        case 2 => Contains(requester, id, randomElement)
        case 3 => Remove(requester, id, randomElement)
      }

      for (seq <- 0 until count) yield randomOperation(requester, seq)
    }

    def referenceReplies(operations: Seq[Operation]): Seq[OperationReply] = {
      var referenceSet = Set.empty[Int]
      def replyFor(op: Operation): OperationReply = op match {
        case Insert(_, seq, elem) =>
          referenceSet = referenceSet + elem
          OperationFinished(seq)
        case Remove(_, seq, elem) =>
          referenceSet = referenceSet - elem
          OperationFinished(seq)
        case Contains(_, seq, elem) =>
          ContainsResult(seq, referenceSet(elem))
      }

      for (op <- operations) yield replyFor(op)
    }

    val requester = TestProbe()
    val topNode = system.actorOf(Props[BinaryTreeSet])
    val count = 1000

    val ops = randomOperations(requester.ref, count)
    val expectedReplies = referenceReplies(ops)

    ops foreach { op =>
      topNode ! op
      if (rnd.nextDouble() < 0.1) topNode ! GC
    }
    receiveN(requester, ops, expectedReplies)
  }
}
