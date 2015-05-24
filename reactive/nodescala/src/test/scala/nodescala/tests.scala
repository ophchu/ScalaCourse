package nodescala

import nodescala.NodeScala._
import org.junit.runner.RunWith
import org.scalatest._
import org.scalatest.junit.JUnitRunner

import scala.collection._
import scala.concurrent._
import scala.concurrent.duration._
import scala.language.postfixOps
import scala.concurrent.ExecutionContext.Implicits.global


@RunWith(classOf[JUnitRunner])
class NodeScalaSuite extends FunSuite
with GivenWhenThen
with Matchers
with BeforeAndAfter
{

  test("A Future should always be completed") {
    val always = Future.always(517)

    assert(Await.result(always, 0 nanos) == 517)


    val always2 = Future.always(5+4)
    assert(Await.result(always2, 0 nanos) == 9)

    assert(Await.result(Future.always("ophir"), 0 nanos) == "ophir")
    assert(Await.result(Future.always(5/0), 0 nanos) == 0)
  }

  test("A Future should never be completed") {
    val never = Future.never[Int]

    try {
      Await.result(never, 1 second)
      assert(false)
    } catch {
      case t: TimeoutException => // ok!
    }
  }

  def expensiveOp: Boolean = {
    Thread.sleep(1000)
    false
  }

  def expensiveOp2: Int = {
    Thread.sleep(1000)
    5
  }

  test("A List of Futures return the Future that completes first") {
    val futures: List[Future[Boolean]] = List(Future(expensiveOp), Future(true), Future(expensiveOp))
    val any = Future.any(futures)
    assert(Await.result(any, 1 second))


  }

  test("A List of Futures return the Future that completes first2") {
    val futures: List[Future[Int]] = List(Future(expensiveOp2), Future(10), Future(expensiveOp2))
    val any = Future.any(futures)
    assert(Await.result(any, 1 second) == 10)
  }


//  test("userInput test") {
//    val uiFuture = Future.userInput("Hello, please enter you line: ")
//
//    val res = Await.result(uiFuture, 1 minute)
//
//    res should equal("hello")
//  }

  
  
  class DummyExchange(val request: Request) extends Exchange {
    @volatile var response = ""
    val loaded = Promise[String]()
    def write(s: String) {
      response += s
    }
    def close() {
      loaded.success(response)
    }
  }

  class DummyListener(val port: Int, val relativePath: String) extends NodeScala.Listener {
    self =>

    @volatile private var started = false
    var handler: Exchange => Unit = null

    def createContext(h: Exchange => Unit) = this.synchronized {
      assert(started, "is server started?")
      handler = h
    }

    def removeContext() = this.synchronized {
      assert(started, "is server started?")
      handler = null
    }

    def start() = self.synchronized {
      started = true
      new Subscription {
        def unsubscribe() = self.synchronized {
          started = false
        }
      }
    }

    def emit(req: Request) = {
      val exchange = new DummyExchange(req)
      if (handler != null) handler(exchange)
      exchange
    }
  }

  class DummyServer(val port: Int) extends NodeScala {
    self =>
    val listeners = mutable.Map[String, DummyListener]()

    def createListener(relativePath: String) = {
      val l = new DummyListener(port, relativePath)
      listeners(relativePath) = l
      l
    }

    def emit(relativePath: String, req: Request) = this.synchronized {
      val l = listeners(relativePath)
      l.emit(req)
    }
  }
  test("Server should serve requests") {
    val dummy = new DummyServer(8191)
    val dummySubscription = dummy.start("/testDir") {
      request => for (kv <- request.iterator) yield (kv + "\n").toString
    }

    // wait until server is really installed
    Thread.sleep(500)

    def test(req: Request) {
      val webpage = dummy.emit("/testDir", req)
      val content = Await.result(webpage.loaded.future, 1 second)
      val expected = (for (kv <- req.iterator) yield (kv + "\n").toString).mkString
      assert(content == expected, s"'$content' vs. '$expected'")
    }

    test(immutable.Map("StrangeRequest" -> List("Does it work?")))
    test(immutable.Map("StrangeRequest" -> List("It works!")))
    test(immutable.Map("WorksForThree" -> List("Always works. Trust me.")))

    dummySubscription.unsubscribe()
  }

}




