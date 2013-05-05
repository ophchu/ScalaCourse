package week5

object msort {
  val sl = List("ophir", "cohen", "anat")         //> sl  : List[String] = List(ophir, cohen, anat)
  val l1 = List(1, 4, 3, 6)                       //> l1  : List[Int] = List(1, 4, 3, 6)

  def msortOrd[T](xs: List[T])(implicit ord: Ordering[T]): List[T] = {
    val n = xs.length / 2
    if (n == 0) xs
    else {
      def merge(xs: List[T], ys: List[T]): List[T] = (xs, ys) match {
        case (Nil, ys) => ys
        case (xs, Nil) => xs
        case (x :: xs1, y :: ys1) =>
          if (ord.lt(x, y)) x :: merge(xs1, ys)
          else y :: merge(xs, ys1)
      }

      val (fst, snd) = xs splitAt n
      merge(msortOrd(fst), msortOrd(snd))
    }
  }                                               //> msortOrd: [T](xs: List[T])(implicit ord: Ordering[T])List[T]

  msortOrd(l1)                                    //> res0: List[Int] = List(1, 3, 4, 6)
  msortOrd(sl)                                    //> res1: List[String] = List(anat, cohen, ophir)
  val mix = List(4, 2, "ophir", "Anat")           //> mix  : List[Any] = List(4, 2, ophir, Anat)
  // msortOrd(mix)
  def msortOld(xs: List[Int]): List[Int] = {
    val n = xs.length / 2
    if (n == 0) xs
    else {
      def merge(xs: List[Int], ys: List[Int]): List[Int] =
        xs match {
          case Nil => ys
          case x :: xs1 => {
            ys match {
              case Nil => xs
              case y :: ys1 =>
                if (x < y) x :: merge(xs1, ys)
                else y :: merge(xs, ys1)
            }
          }
        }
      val (fst, snd) = xs splitAt n
      merge(msortOld(fst), msortOld(snd))
    }
  } //                                            //> msortOld: (xs: List[Int])List[Int]
  msortOld(l1)                                    //> res2: List[Int] = List(1, 3, 4, 6)

  l1                                              //> res3: List[Int] = List(1, 4, 3, 6)
  def msort[T](xs: List[T])(lt: (T, T) => Boolean): List[T] = {
    val n = xs.length / 2
    if (n == 0) xs
    else {
      def merge(xs: List[T], ys: List[T]): List[T] = (xs, ys) match {
        case (Nil, ys) => ys
        case (xs, Nil) => xs
        case (x :: xs1, y :: ys1) =>
          if (lt(x, y)) x :: merge(xs1, ys)
          else y :: merge(xs, ys1)
      }

      val (fst, snd) = xs splitAt n
      merge(msort(fst)(lt), msort(snd)(lt))
    }
  }                                               //> msort: [T](xs: List[T])(lt: (T, T) => Boolean)List[T]
  msort(l1)((x, y) => x < y)                      //> res4: List[Int] = List(1, 3, 4, 6)

  msort(sl)((str1: String, str2: String) => str1.compareTo(str2) < 0)
                                                  //> res5: List[String] = List(anat, cohen, ophir)
}