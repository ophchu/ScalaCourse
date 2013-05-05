package week6

object scalar {
  def scalarProduct(xs: Vector[Double], ys: Vector[Double]): Double =
    (xs zip ys).map(xy => xy._1 * xy._2).sum

  def scalarProduct1(xs: Vector[Double], ys: Vector[Double]): Double =
    (xs zip ys).map { case (x, y) => x * y }.sum

def isPrime(n: Int): Boolean = (2 to n-1) forall (d => n%du != 0)
 
 isPrime(4)
  isPrime(5)
}