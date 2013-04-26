package week4

object ListWS {
  val nums = List(4,2,12, 5)                      //> nums  : List[Int] = List(4, 2, 12, 5)
  val diag = List("a", "b", "c")                  //> diag  : List[String] = List(a, b, c)
  val nums2 = 1 :: 2 :: 3 :: Nil                  //> nums2  : List[Int] = List(1, 2, 3)
  
  nums.sorted                                     //> res0: List[Int] = List(2, 4, 5, 12)
  
  def isort (xs: List[Int]): List[Int] = xs match{
  	case List() => List()
  	case y :: ys => insert(y, isort(ys))
  }                                               //> isort: (xs: List[Int])List[Int]
  def insert (x: Int, xs: List[Int]): List[Int] = xs match {
  	case List() => List(x)
  	case y :: ys => if (x <= y) x :: xs else y :: insert(x, ys)
  }                                               //> insert: (x: Int, xs: List[Int])List[Int]
}