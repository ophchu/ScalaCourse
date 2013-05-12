package week6

object fors {
  val l1 = List(1, 2, 10,30)                      //> l1  : List[Int] = List(1, 2, 10, 30)
  l1 map (x => x +3)                              //> res0: List[Int] = List(4, 5, 13, 33)
  
  
  val l2 = l1 withFilter(x => x>4)                //> l2  : scala.collection.generic.FilterMonadic[Int,List[Int]] = scala.collecti
                                                  //| on.TraversableLike$WithFilter@a45f686
           l2                                               //> res1: scala.collection.generic.FilterMonadic[Int,List[Int]] = scala.collecti
                                                  //| on.TraversableLike$WithFilter@a45f686
}