package week6

object maps {
  val map1 = Map("I" -> 1, "V" -> 5, "X" -> 10)   //> map1  : scala.collection.immutable.Map[String,Int] = Map(I -> 1, V -> 5, X ->
                                                  //|  10)
  val map2 = Map("a" -> "A", "b" -> "B", "c" -> "C")
                                                  //> map2  : scala.collection.immutable.Map[String,String] = Map(a -> A, b -> B, 
                                                  //| c -> C)
  
  
  map1("I")                                       //> res0: Int = 1
 map2 get "a"                                     //> res1: Option[String] = Some(A)
 map2 get "SAFDSA"                                //> res2: Option[String] = None
 
 def getLetter(letter: String) = map2.get(letter) match {
 case Some(let) => let
 case None => "Missing Data"
 }                                                //> getLetter: (letter: String)String
 
 getLetter("a")                                   //> res3: String = A
 getLetter("asd")                                 //> res4: String = Missing Data
 
 val l1 = List("ophir", "anat", "yosef", "ana")   //> l1  : List[String] = List(ophir, anat, yosef, ana)
 l1 sorted                                        //> res5: List[String] = List(ana, anat, ophir, yosef)
 
 //l1 sortWith(_.)
 l1 groupBy(_.head)                               //> res6: scala.collection.immutable.Map[Char,List[String]] = Map(y -> List(yose
                                                  //| f), a -> List(anat, ana), o -> List(ophir))
  l1 sortWith(_.length > _.length)                //> res7: List[String] = List(ophir, yosef, anat, ana)
 
 
 val qd1 = map2 withDefaultValue("Unknowen")      //> qd1  : scala.collection.immutable.Map[String,String] = Map(a -> A, b -> B, c
                                                  //|  -> C)
       
 qd1("a")                                         //> res8: String = A
 qd1("aaa")                                       //> res9: String = Unknowen
}