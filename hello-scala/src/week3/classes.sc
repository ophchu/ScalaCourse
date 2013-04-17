package week3

object classes {
  def error(msg: String) = throw new Error(msg)   //> error: (msg: String)Nothing

  val x = null                                    //> x  : Null = null
  val y: String = x                               //> y  : String = null

  if (true) 1 else false                          //> res0: AnyVal = 1

  def singelton[T](elem: T) = new Cons[T](elem, new Nil[T])
                                                  //> singelton: [T](elem: T)week3.Cons[T]
  val s1 = singelton[Int](1)                      //> s1  : week3.Cons[Int] = week3.Cons@336e8a49
  val s2 = singelton(2)                           //> s2  : week3.Cons[Int] = week3.Cons@1e38d900

  def ntn[T](n: Int, head: List[T]) = {
    def itr(idx: Int, head: List[T]): T =
    	if (head.isEmpty) throw new IndexOutOfBoundsException("List to small!")
      else if (idx == n) head.head
      else itr(idx + 1, head.tail)

    itr(0, head)
  }                                               //> ntn: [T](n: Int, head: week3.List[T])T

val l1 = new Cons(1, new Cons(2, new Cons (3, new Nil)))
                                                  //> l1  : week3.Cons[Int] = week3.Cons@262e9e80
    ntn(-1, l1)                                   //> java.lang.IndexOutOfBoundsException: List to small!
                                                  //| 	at week3.classes$$anonfun$main$1.itr$1(week3.classes.scala:17)
                                                  //| 	at week3.classes$$anonfun$main$1.ntn$1(week3.classes.scala:21)
                                                  //| 	at week3.classes$$anonfun$main$1.apply$mcV$sp(week3.classes.scala:25)
                                                  //| 	at org.scalaide.worksheet.runtime.library.WorksheetSupport$$anonfun$$exe
                                                  //| cute$1.apply$mcV$sp(WorksheetSupport.scala:76)
                                                  //| 	at org.scalaide.worksheet.runtime.library.WorksheetSupport$.redirected(W
                                                  //| orksheetSupport.scala:65)
                                                  //| 	at org.scalaide.worksheet.runtime.library.WorksheetSupport$.$execute(Wor
                                                  //| ksheetSupport.scala:75)
                                                  //| 	at week3.classes$.main(week3.classes.scala:3)
                                                  //| 	at week3.classes.main(week3.classes.scala)
}