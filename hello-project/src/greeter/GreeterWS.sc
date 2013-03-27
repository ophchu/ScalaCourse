package greeter

object GreeterWS {
    val x = 2                                     //> x  : Int = 2
  def increase(i: Int) = i + 1                    //> increase: (i: Int)Int
  increase(x)                                     //> res0: Int = 3
}