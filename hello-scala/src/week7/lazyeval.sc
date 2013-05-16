package week7

object lazyeval {
  def expr = {
    val x = { print("x"); 1 }
    lazy val y = { print("y"); 2 }
    def z = { print("z"); 3 }
    z + y + x + z + y + x
 }                                                //> expr: => Int
 //xzyz
 expr                                             //> xzyzres0: Int = 12
 val a = { print("a"); 3 }                        //> aa  : Int = 3
}