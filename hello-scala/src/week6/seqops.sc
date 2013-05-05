package week6


object seqops {
  val r1 = 1 to 100 by 3                          //> r1  : scala.collection.immutable.Range = Range(1, 4, 7, 10, 13, 16, 19, 22, 2
                                                  //| 5, 28, 31, 34, 37, 40, 43, 46, 49, 52, 55, 58, 61, 64, 67, 70, 73, 76, 79, 82
                                                  //| , 85, 88, 91, 94, 97, 100)

  r1 exists (x => x > 40)                         //> res0: Boolean = true
  r1 exists (x => x % 3 == 0)                     //> res1: Boolean = false
  r1 forall(x => x > 10)                          //> res2: Boolean = false
  
  val str1 = "Hello World"                        //> str1  : String = Hello World
  val v1 = Vector(1,4,3)                          //> v1  : scala.collection.immutable.Vector[Int] = Vector(1, 4, 3)
  
  val zip1 = str1 zip v1                          //> zip1  : scala.collection.immutable.IndexedSeq[(Char, Int)] = Vector((H,1), (
                                                  //| e,4), (l,3))
 zip1 unzip                                       //> res3: (scala.collection.immutable.IndexedSeq[Char], scala.collection.immutab
                                                  //| le.IndexedSeq[Int]) = (Vector(H, e, l),Vector(1, 4, 3))
 
 str1 flatMap(ch => Array('.', ch))               //> res4: String = .H.e.l.l.o. .W.o.r.l.d
 
 val r2 = 1 to 10 by 2                            //> r2  : scala.collection.immutable.Range = Range(1, 3, 5, 7, 9)
 r2 flatMap(x => List(x, x*x))                    //> res5: scala.collection.immutable.IndexedSeq[Int] = Vector(1, 1, 3, 9, 5, 25,
                                                  //|  7, 49, 9, 81)
  r2 max                                          //> res6: Int = 9

}