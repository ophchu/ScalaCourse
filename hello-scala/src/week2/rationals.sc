package week2

object rationals {
  val x = new Rational(1, 3)                      //> x  : week2.Rational = 1/3
  val y = new Rational(5, 7)                      //> y  : week2.Rational = 5/7
  val z = new Rational(3, 2)                      //> z  : week2.Rational = 3/2
  x - y - z                                       //> res0: week2.Rational = -79/42
  y +y                                            //> res1: week2.Rational = 10/7
  x - y                                           //> res2: week2.Rational = 8/-21
  x max y                                         //> res3: week2.Rational = 5/7

  val fl = new Rational(2)                        //> fl  : week2.Rational = 2/1

  x + y                                           //> res4: week2.Rational = 22/21
  x + y                                           //> res5: week2.Rational = 22/21
}
class Rational(x: Int, y: Int) {
  require(y != 0, "Denum must be nonzero")
  private def gcd(a: Int, b: Int): Int = if (b == 0) a else gcd(b, a % b)
  private val g = gcd(x, y)
  def numer = x / g
  def denom = y / g

  def <(that: Rational) = numer * that.denom < that.numer * denom
  def max(that: Rational) = if (this < that) that else this

  
  def this(x: Int) = this(x, 1)
  def +(that: Rational): Rational = {
    new Rational(
      numer * that.denom + that.numer * denom,
      denom * that.denom)
  }
  def unary_-(): Rational = {
    new Rational(-numer, denom)
  }

  def -(that: Rational): Rational = this + -that

  override def toString() = numer + "/" + denom

}