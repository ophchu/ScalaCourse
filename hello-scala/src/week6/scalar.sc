package week6

object scalar {
  def scalarProduct(xs: Vector[Double], ys: Vector[Double]): Double =
    (xs zip ys).map(xy => xy._1 * xy._2).sum      //> scalarProduct: (xs: Vector[Double], ys: Vector[Double])Double

  def scalarProduct1(xs: Vector[Double], ys: Vector[Double]): Double =
    (xs zip ys).map { case (x, y) => x * y }.sum  //> scalarProduct1: (xs: Vector[Double], ys: Vector[Double])Double

  def isPrime(n: Int): Boolean = (2 to n - 1) forall (d => n % d != 0)
                                                  //> isPrime: (n: Int)Boolean

  isPrime(4)                                      //> res0: Boolean = false
  isPrime(5)                                      //> res1: Boolean = true
  
  def ijPrime(n: Int) =
  (1 until n) map (i =>
  	(1 until i) map (j => (i,j)))             //> ijPrime: (n: Int)scala.collection.immutable.IndexedSeq[scala.collection.immu
                                                  //| table.IndexedSeq[(Int, Int)]]
  	
  	
  	ijPrime(5)                                //> res2: scala.collection.immutable.IndexedSeq[scala.collection.immutable.Index
                                                  //| edSeq[(Int, Int)]] = Vector(Vector(), Vector((2,1)), Vector((3,1), (3,2)), V
                                                  //| ector((4,1), (4,2), (4,3)))
}