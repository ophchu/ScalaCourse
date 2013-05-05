package week5

object listsops {
  val l1 = List(1, 2, 3, 4)                       //> l1  : List[Int] = List(1, 2, 3, 4)
  println(l1)                                     //> List(1, 2, 3, 4)
  l1.head                                         //> res0: Int = 1
  l1.tail                                         //> res1: List[Int] = List(2, 3, 4)

  l1.last                                         //> res2: Int = 4
  l1.init                                         //> res3: List[Int] = List(1, 2, 3)

  l1 take 2                                       //> res4: List[Int] = List(1, 2)
  l1 drop 2                                       //> res5: List[Int] = List(3, 4)

  l1(2)                                           //> res6: Int = 3

  val l2 = List(11, 12, 13, 14)                   //> l2  : List[Int] = List(11, 12, 13, 14)

  l1 ++ l2                                        //> res7: List[Int] = List(1, 2, 3, 4, 11, 12, 13, 14)
  l2.reverse                                      //> res8: List[Int] = List(14, 13, 12, 11)
  
  l1 updated (2, 4)                               //> res9: List[Int] = List(1, 2, 4, 4)

  l1 indexOf (3)                                  //> res10: Int = 2
  l1.indexOf(10)                                  //> res11: Int = -1
  l1 contains (10)                                //> res12: Boolean = false

  def init[T](xs: List[T]): List[T] = xs match {
    case List() => throw new Error("Empty list")
    case List(x) => List()
    case y :: ys => y :: init(ys)
  }                                               //> init: [T](xs: List[T])List[T]
  init(l1)                                        //> res13: List[Int] = List(1, 2, 3)
  def reverse[T](xs :List[T]): List[T] = xs match{
  	case List() => xs
  	case y :: ys => reverse(ys) ++ List(y)
  }                                               //> reverse: [T](xs: List[T])List[T]
  reverse(l1)                                     //> res14: List[Int] = List(4, 3, 2, 1)
  
  def removeAt[T](xs: List[T], n: Int): List[T] = {
  	def removeAtLocal[T](xs: List[T], currIdx: Int): List[T] = xs match {
  	case List() => xs
  	case y :: ys =>
  		if (currIdx == n) removeAtLocal(ys, currIdx + 1)
  		else y :: removeAtLocal(ys, currIdx + 1)
  	}
  	removeAtLocal(xs, 0)
  }                                               //> removeAt: [T](xs: List[T], n: Int)List[T]
  
  
  
 def removeAt2[T](xs: List[T], n: Int): List[T] = (xs take n) ::: (xs drop n + 1)
                                                  //> removeAt2: [T](xs: List[T], n: Int)List[T]
  
  removeAt(l1, 2)                                 //> res15: List[Int] = List(1, 2, 4)
  removeAt2(l1, 2)                                //> res16: List[Int] = List(1, 2, 4)
  
  
  
  
  
  
}