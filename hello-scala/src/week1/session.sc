package week1

object session {

  def sqrt(x: Double) = {
    def abs(x: Double) = if (x >= 0) x else -x

    def sqrIter(guess: Double): Double =
      if (isGoodEnough(guess)) guess
      else sqrIter(improve(guess))

    def isGoodEnough(guess: Double) =
      abs(guess * guess - x) < 0.001

    def improve(guess: Double) =
      (guess + x / guess) / 2

    sqrIter(1.0)

  }                                               //> sqrt: (x: Double)Double
  sqrt(2000)                                      //> res0: Double = 44.721359560127915
  sqrt(4)                                         //> res1: Double = 2.0000000929222947

}