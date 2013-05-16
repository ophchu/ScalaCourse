package week7

object infinite {
  def from(n: Int): Stream[Int] = n #:: from(n + 1)
                                                  //> from: (n: Int)Stream[Int]

  val nats = from(0)                              //> nats  : Stream[Int] = Stream(0, ?)
  val m4s = nats map (_ * 4)                      //> m4s  : scala.collection.immutable.Stream[Int] = Stream(0, ?)

  m4s take 2 toList                               //> res0: List[Int] = List(0, 4)

  def sieve(s: Stream[Int]): Stream[Int] =
    s.head #:: sieve(s.tail filter (_ % s.head != 0))
                                                  //> sieve: (s: Stream[Int])Stream[Int]

  val primes = sieve(from(2))                     //> primes  : Stream[Int] = Stream(2, ?)

  primes take 2 toList                            //> res1: List[Int] = List(2, 3)

  def sqrtStream(x: Double): Stream[Double] = {
    def improve(guess: Double) = (guess + x / guess) / 2
    lazy val guess: Stream[Double] = 1 #:: (guess map improve)
    guess
  }                                               //> sqrtStream: (x: Double)Stream[Double]

  sqrtStream(4).take(10).toList                   //> res2: List[Double] = List(1.0, 2.5, 2.05, 2.000609756097561, 2.0000000929222
                                                  //| 947, 2.000000000000002, 2.0, 2.0, 2.0, 2.0)
  sqrtStream(81).take(10).toList                  //> res3: List[Double] = List(1.0, 41.0, 21.48780487804878, 12.628692450375128, 
                                                  //| 9.521329066772005, 9.014272376994608, 9.000011298790216, 9.000000000007091, 
                                                  //| 9.0, 9.0)

  //We sperated the 'good enough' from the implmentation,
  //Now we can:

  def isGoodEnough(guess: Double, x: Double) =
    math.abs((guess * guess - x) / x) < 0.0001    //> isGoodEnough: (guess: Double, x: Double)Boolean
  
  sqrtStream(4) filter (isGoodEnough(_, 4)) take 10 toList
                                                  //> res4: List[Double] = List(2.0000000929222947, 2.000000000000002, 2.0, 2.0, 2
                                                  //| .0, 2.0, 2.0, 2.0, 2.0, 2.0)
}