import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent._
import scala.util.{Failure, Success, Random}

val rnd = Random
val res = Future{
  println(s"Where in! ${Thread.currentThread().getId}")
  Thread.sleep(rnd.nextLong()%1000)

  rnd.nextInt(1000)
}

res.onComplete {
  case Success(x) => println(x)
  case Failure(e) => println(e.getMessage)
}


res.isCompleted
