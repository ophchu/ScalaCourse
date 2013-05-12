package week6

object anagramsws {
  /** A word is simply a `String`. */
  type Word = String

  /** A sentence is a `List` of words. */
  type Sentence = List[Word]
  type Occurrences = List[(Char, Int)]
  val str = "opdhirRrfdeeE"                       //> str  : String = opdhirRrfdeeE

  val sMap = (str groupBy ((ch: Char) => (ch.toLower))).toList sorted
                                                  //> sMap  : List[(Char, String)] = List((d,dd), (e,eeE), (f,f), (h,h), (i,i), (o
                                                  //| ,o), (p,p), (r,rRr))
  def wordOccurrences(w: Word): Occurrences = ((w groupBy ((ch: Char) => (ch.toLower))).toList sorted) map (x => (x._1, x._2.length()))
                                                  //> wordOccurrences: (w: week6.anagramsws.Word)week6.anagramsws.Occurrences

  wordOccurrences(str)                            //> res0: week6.anagramsws.Occurrences = List((d,2), (e,3), (f,1), (h,1), (i,1),
                                                  //|  (o,1), (p,1), (r,3))
  val w1 = List("ophir", "Cohen")                 //> w1  : List[String] = List(ophir, Cohen)
  def sentenceOccurrences(s: Sentence): Occurrences = wordOccurrences(s.flatten mkString)
                                                  //> sentenceOccurrences: (s: week6.anagramsws.Sentence)week6.anagramsws.Occurren
                                                  //| ces

  val l1 = List("abcd", "Bcde", "bEf", "gh")      //> l1  : List[String] = List(abcd, Bcde, bEf, gh)
  sentenceOccurrences(l1)                         //> res1: week6.anagramsws.Occurrences = List((a,1), (b,3), (c,2), (d,2), (e,2),
                                                  //|  (f,1), (g,1), (h,1))

  val anList = List("ate", "eat", "tea", "it")    //> anList  : List[String] = List(ate, eat, tea, it)
  val anList2 = List("it")                        //> anList2  : List[String] = List(it)

  val ocMap = ((anList map (x => (x, wordOccurrences(x)))) groupBy (x => x._2)) map (x => (x._1, x._2 map (x => x._1)))
                                                  //> ocMap  : scala.collection.immutable.Map[week6.anagramsws.Occurrences,List[St
                                                  //| ring]] = Map(List((i,1), (t,1)) -> List(it), List((a,1), (e,1), (t,1)) -> Li
                                                  //| st(ate, eat, tea))

  def wordAnagrams(word: Word): List[String] = (ocMap filter (x => x._1 == wordOccurrences(word))).head._2
                                                  //> wordAnagrams: (word: week6.anagramsws.Word)List[String]

wordAnagrams("tea")                               //> res2: List[String] = List(ate, eat, tea)






























}