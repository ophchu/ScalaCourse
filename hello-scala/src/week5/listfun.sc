package week5

object listfun {
  val sl = List("ophir", "cohen", "anat")         //> sl  : List[String] = List(ophir, cohen, anat)
  val l1 = List(1, 2, -9, 4, -2, 3, 6)            //> l1  : List[Int] = List(1, 2, -9, 4, -2, 3, 6)

  l1 filter (x => x > 0)                          //> res0: List[Int] = List(1, 2, 4, 3, 6)
  l1 filterNot (x => x > 0)                       //> res1: List[Int] = List(-9, -2)
  l1 partition (x => x > 0)                       //> res2: (List[Int], List[Int]) = (List(1, 2, 4, 3, 6),List(-9, -2))

  l1 takeWhile (x => x > 0)                       //> res3: List[Int] = List(1, 2)
  l1 dropWhile (x => x > 0)                       //> res4: List[Int] = List(-9, 4, -2, 3, 6)
  l1 span (x => x > 0)                            //> res5: (List[Int], List[Int]) = (List(1, 2),List(-9, 4, -2, 3, 6))

  def pack[T](xs: List[T]): List[List[T]] = xs match {
    case Nil => Nil
    case x :: xs1 =>
      val (first, rest) = xs span (y => y == x)
      first :: pack(rest)
  }                                               //> pack: [T](xs: List[T])List[List[T]]

  val packL = List("a", "a", "a", "b", "c", "c", "a")
                                                  //> packL  : List[String] = List(a, a, a, b, c, c, a)
  pack(packL)                                     //> res6: List[List[String]] = List(List(a, a, a), List(b), List(c, c), List(a))
                                                  //| 
  def encode[T](xs: List[T]): List[(T, Int)] =
  pack(xs) map (ys => (ys.head, ys.length))       //> encode: [T](xs: List[T])List[(T, Int)]
  
  encode(packL)                                   //> res7: List[(String, Int)] = List((a,3), (b,1), (c,2), (a,1))

}