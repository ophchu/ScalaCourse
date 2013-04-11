package week2

object sumhighorder {
  def sum(f: Int => Int, a: Int, b: Int): Int = {
    def loop(a: Int, acc: Int): Int = {
      if (a > b) acc
      else loop(a + 1, f(a) + acc)
    }
    loop(a, 0)
  }                                               //> sum: (f: Int => Int, a: Int, b: Int)Int

  sum(x => x * x, 1, 3)                           //> res0: Int = 14

  def sumNew(f: Int => Int): (Int, Int) => Int = {
    def sumF(a: Int, b: Int): Int =
      if (a > b) 0
      else f(a) + sumF(a + 1, b)
    sumF
  }                                               //> sumNew: (f: Int => Int)(Int, Int) => Int
  def sumInts = sumNew(x	 => x * x)        //> sumInts: => (Int, Int) => Int
  sumInts(1,3)                                    //> res1: Int = 14
  
  sumNew(x => x)(1, 9)                            //> res2: Int = 45
  
  
  def sumNew2(f: Int => Int)(a: Int, b: Int): Int =
      if (a > b) 0 else f(a) + sumNew2(f)(a + 1, b)
                                                  //> sumNew2: (f: Int => Int)(a: Int, b: Int)Int
      
      sumNew2(x => x * x *x)(1,3)                 //> res3: Int = 36
}