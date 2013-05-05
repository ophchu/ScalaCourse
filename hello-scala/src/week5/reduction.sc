package week5

object reduction {
  val sl = List("ophir", "cohen", "anat")         //> sl  : List[String] = List(ophir, cohen, anat)
  val l1 = List(1, 2, -9, 4, -2, 3, 6)            //> l1  : List[Int] = List(1, 2, -9, 4, -2, 3, 6)
  
  val l2 = l1 filter (x => x >0)                  //> l2  : List[Int] = List(1, 2, 4, 3, 6)
  def sum (xs :List[Int]) = (0 :: xs) reduceLeft((x, y) => x + y)
                                                  //> sum: (xs: List[Int])Int
  def sum2 (xs :List[Int]) = (0 :: xs) reduceLeft(_*_)
                                                  //> sum2: (xs: List[Int])Int
  def product (xs :List[Int]) = (1 :: xs) reduceLeft((x, y) => x * y)
                                                  //> product: (xs: List[Int])Int
  def product2 (xs :List[Int]) = (xs foldLeft 1) (_*_)
                                                  //> product2: (xs: List[Int])Int
  
  sum(l2)                                         //> res0: Int = 16
  product(l2)                                     //> res1: Int = 144
  product2(l2)                                    //> res2: Int = 144
}