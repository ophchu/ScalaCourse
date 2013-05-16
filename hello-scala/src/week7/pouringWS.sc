package week7

object pouringWS {
  val problem = new Pouring(Vector(4, 7))         //> problem  : week7.Pouring = week7.Pouring@18d9850
  problem.moves                                   //> res0: scala.collection.immutable.IndexedSeq[Product with Serializable with we
                                                  //| ek7.pouringWS.problem.Move] = Vector(Empty(0), Empty(1), Fill(0), Fill(1), Po
                                                  //| ur(0,1), Pour(1,0))

  problem.pathSets                                //> res1: Stream[Set[week7.pouringWS.problem.Path]] = Stream(Set(-->Vector(0, 0)
                                                  //| ), ?)
  
  
  problem.solutions(6)                            //> res2: Stream[week7.pouringWS.problem.Path] = Stream(Fill(1) Pour(1,0) Empty(
                                                  //| 0) Pour(1,0) Fill(1) Pour(1,0)-->Vector(4, 6), ?)

}