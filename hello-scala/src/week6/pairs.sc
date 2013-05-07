package week6

object pairs {

  def isPrime(n: Int): Boolean = (2 to n - 1) forall (d => n % d != 0)
                                                  //> isPrime: (n: Int)Boolean
  val n = 7                                       //> n  : Int = 7
  def ijPrime(n: Int) = {
    (1 until n) flatMap (i =>
      (1 until i) map (j => (i, j))) filter (pair => isPrime(pair._1 + pair._2))

  }                                               //> ijPrime: (n: Int)scala.collection.immutable.IndexedSeq[(Int, Int)]

  // filter (pair => isPrime(pair._1 + pair._2)
  ijPrime(7)                                      //> res0: scala.collection.immutable.IndexedSeq[(Int, Int)] = Vector((2,1), (3,2
                                                  //| ), (4,1), (4,3), (5,2), (6,1), (6,5))
  //We can do it better!
  for {
    i <- 1 until n
    j <- i until n
    if isPrime(i + j)
  } yield (i, j)                                  //> res1: scala.collection.immutable.IndexedSeq[(Int, Int)] = Vector((1,1), (1,2
                                                  //| ), (1,4), (1,6), (2,3), (2,5), (3,4), (5,6))


 def scalarProduct1(xs: Vector[Double], ys: Vector[Double]): Double =
    (xs zip ys).map { case (x, y) => x * y }.sum  //> scalarProduct1: (xs: Vector[Double], ys: Vector[Double])Double
    
     def scalarProductFor(xs: Vector[Double], ys: Vector[Double]): Double =
    (for ((x,y) <- xs zip ys) yield x * y).sum    //> scalarProductFor: (xs: Vector[Double], ys: Vector[Double])Double
}