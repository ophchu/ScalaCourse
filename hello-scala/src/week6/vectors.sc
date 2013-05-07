package week6

object vectors {
  val v1 = Vector(1, 2, 3)                        //> v1  : scala.collection.immutable.Vector[Int] = Vector(1, 2, 3)

  v1 :+ 4                                         //> res0: scala.collection.immutable.Vector[Int] = Vector(1, 2, 3, 4)
  v1.+:(4)                                        //> res1: scala.collection.immutable.Vector[Int] = Vector(4, 1, 2, 3)

  4 +: v1                                         //> res2: scala.collection.immutable.Vector[Int] = Vector(4, 1, 2, 3)

  v1 reduceRight ((x, y) => x * y)                //> res3: Int = 6

  val a1 = Array(1, 2, 5, 45)                     //> a1  : Array[Int] = Array(1, 2, 5, 45)
  a1 map (x => x * x)                             //> res4: Array[Int] = Array(1, 4, 25, 2025)
  a1 filter (x => x > 10)                         //> res5: Array[Int] = Array(45)
  
  
  val str1 = "Hello World!"                       //> str1  : String = Hello World!
  str1 map (x => x ^2)                            //> res6: scala.collection.immutable.IndexedSeq[Int] = Vector(74, 103, 110, 110,
                                                  //|  109, 34, 85, 109, 112, 110, 102, 35)
      str1 filter (ch => ch.isUpper)              //> res7: String = HW
      
      val r = 1 until 5                           //> r  : scala.collection.immutable.Range = Range(1, 2, 3, 4)
      val r2 = 1 to 5                             //> r2  : scala.collection.immutable.Range.Inclusive = Range(1, 2, 3, 4, 5)
      1 to 10 by 3                                //> res8: scala.collection.immutable.Range = Range(1, 4, 7, 10)
     6 to 1 by -2                                 //> res9: scala.collection.immutable.Range = Range(6, 4, 2)
     
 val r3 = 1 to 100 by 3                           //> r3  : scala.collection.immutable.Range = Range(1, 4, 7, 10, 13, 16, 19, 22, 
                                                  //| 25, 28, 31, 34, 37, 40, 43, 46, 49, 52, 55, 58, 61, 64, 67, 70, 73, 76, 79, 
                                                  //| 82, 85, 88, 91, 94, 97, 100)
 val r4 = r3 map (x => x ^2)                      //> r4  : scala.collection.immutable.IndexedSeq[Int] = Vector(3, 6, 5, 8, 15, 18
                                                  //| , 17, 20, 27, 30, 29, 32, 39, 42, 41, 44, 51, 54, 53, 56, 63, 66, 65, 68, 75
                                                  //| , 78, 77, 80, 87, 90, 89, 92, 99, 102)
 r4 reduceRight((x, y) => x + y)                  //> res10: Int = 1721
  (r3 foldLeft 0) (_ + _)                         //> res11: Int = 1717
}
 