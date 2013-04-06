package week1

object balance {

  /**
   * Exercise 2
   */
  def balance(chars: List[Char]): Boolean = {
    def next(count: Int, chars: List[Char]): Boolean = {
      if (chars.isEmpty) count==0 else
      if (chars.head == '(') next(count + 1, chars.tail) else
      if (chars.head == ')') count > 0 && next (count-1, chars.tail) else
      next(count, chars.tail)
    }
    
    next(0, chars)
  }                                               //> balance: (chars: List[Char])Boolean
  
balance("()()".toList)                            //> res0: Boolean = true
  balance("((Just (small) example (to) test my program(!!!))".toList)
                                                  //> res1: Boolean = false

  balance("())(".toList)                          //> res2: Boolean = false
  balance("I told him (that it's not (yet) done).\n(But he wasn't listening)".toList)
                                                  //> res3: Boolean = true
  balance("()!!!Hello (ad (hi (( there))))".toList)
                                                  //> res4: Boolean = true
  balance("".toList)                              //> res5: Boolean = true
  balance("x())".toList)                          //> res6: Boolean = false
  balance("x(()(xxx".toList)                      //> res7: Boolean = false
  balance("x)".toList)                            //> res8: Boolean = false
  balance("(*))".toList)                          //> res9: Boolean = false
}