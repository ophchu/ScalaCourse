package week2

object product {

  def product(f: Int => Int)(a: Int, b: Int): Int = {
  	if (a > b) 1 else f(a) * product(f)(a + 1, b)
  }                                               //> product: (f: Int => Int)(a: Int, b: Int)Int
  product(x => x)(1, 3)                           //> res0: Int = 6
  def fact(n: Int) = product(x => x)(1, n)        //> fact: (n: Int)Int
  fact(10)                                        //> res1: Int = 3628800
  
  def sumProd(f: Int => Int, basic: Int, su: (Int, Int) => Int)(a: Int, b: Int): Int = {
  	if (a > b) basic else su(f(a), sumProd(f, basic, su)(a + 1, b))
  }                                               //> sumProd: (f: Int => Int, basic: Int, su: (Int, Int) => Int)(a: Int, b: Int)I
                                                  //| nt
 def product1(f: Int => Int)(a: Int, b: Int): Int = sumProd(f, 1, (x, y) => x * y)(a, b)
                                                  //> product1: (f: Int => Int)(a: Int, b: Int)Int
   
                                                  
product(x => x)(1, 3)                             //> res2: Int = 6
product1(x => x)(1, 3)                            //> res3: Int = 6
}