package week1

object balance {

  /**
   * Exercise 2
   */
  def balance(chars: List[Char]): Boolean = {
    def next(chars: List[Char]): Boolean = {
      if (chars.isEmpty) true else
      if (chars.head == '(') isClosed(chars.tail) else
      next(chars.tail)
    }
    def isClosed(chars: List[Char]): Boolean = {
      if (chars.isEmpty) false else
      if (chars.head == '(') isClosed(chars.tail) else
      if (chars.head == ')') true else
      next(chars.tail)
    }
    next(chars)
  }                                               //> balance: (chars: List[Char])Boolean

  balance("((Just (small) example (to) test my program(!!!))".toList)
                                                  //> res0: Boolean = true

  balance("())(".toList)                          //> res1: Boolean = true
  balance("I told him (that it's not (yet) done).\n(But he wasn't listening)".toList)
                                                  //> res2: Boolean = true
  balance("()!!!Hello (and (hi (( there)))".toList)
                                                  //> res3: Boolean = true
  balance("".toList)                              //> res4: Boolean = true
}