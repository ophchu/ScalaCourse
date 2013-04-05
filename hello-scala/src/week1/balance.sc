package week1

object balance {

  /**
   * Exercise 2
   */
  def balance(chars: List[Char]): Boolean = {
  	if (chars.isEmpty) true else
  	if (chars.head=='(') !balance(chars.tail) else
  	if (chars.head==')') !balance(chars.tail) else
  	balance(chars.tail)
  }                                               //> balance: (chars: List[Char])Boolean

  balance("(Just( ((small)) example (to) test my program(!!!)".toList)
                                                  //> res0: Boolean = true
}