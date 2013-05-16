package week6

object substruct {
/** A word is simply a `String`. */
  type Word = String

  /** A sentence is a `List` of words. */
  type Sentence = List[Word]
  type Occurrences = List[(Char, Int)]
  
  val x = List(('a', 1), ('d', 1), ('l', 1), ('r', 1))
                                                  //> x  : List[(Char, Int)] = List((a,1), (d,1), (l,1), (r,1))
val y = List(('r', 1))                            //> y  : List[(Char, Int)] = List((r,1))
  
  def subtract(x: Occurrences, y: Occurrences): Occurrences = x filter (xs => !y.contains(xs))
                                                  //> subtract: (x: week6.substruct.Occurrences, y: week6.substruct.Occurrences)we
                                                  //| ek6.substruct.Occurrences
  
  subtract(x, y)                                  //> res0: week6.substruct.Occurrences = List((a,1), (d,1), (l,1))
  
  
}