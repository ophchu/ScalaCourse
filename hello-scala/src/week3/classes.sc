package week3

object classes {
  def error(msg: String) = throw new Error(msg)   //> error: (msg: String)Nothing

  val x = null                                    //> x  : Null = null
  val y: String = x                               //> y  : String = null
  
  if (true) 1 else false                          //> res0: AnyVal = 1

}