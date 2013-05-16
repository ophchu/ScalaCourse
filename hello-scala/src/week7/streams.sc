package week7

object streams {
  def isPrime(n: Int): Boolean = (2 to n - 1) forall (k => n % k != 0)
                                                  //> isPrime: (n: Int)Boolean

  ((100 to 10000) filter isPrime)(1)              //> res0: Int = 103

  val s1 = Stream.cons(1, Stream.cons(2, Stream.Empty))
                                                  //> s1  : Stream.Cons[Int] = Stream(1, ?)
  Stream(1, 2, 3)                                 //> res1: scala.collection.immutable.Stream[Int] = Stream(1, ?)
  val s2 = (1 to 1000).toStream                   //> s2  : scala.collection.immutable.Stream[Int] = Stream(1, ?)

  ('a' to 'z').toStream                           //> res2: scala.collection.immutable.Stream[Char] = Stream(a, ?)
  ((100 to 10000).toStream filter isPrime)        //> res3: scala.collection.immutable.Stream[Int] = Stream(101, ?)

  def streamRange(lo: Int, hi: Int): Stream[Int] = {
    print(lo + " ")
    if (lo >= hi) Stream.empty
    else Stream.cons(lo, streamRange(lo + 1, hi))
  }                                               //> streamRange: (lo: Int, hi: Int)Stream[Int]
  streamRange(1, 10).take(3).toList               //> 1 2 3 res4: List[Int] = List(1, 2, 3)
}