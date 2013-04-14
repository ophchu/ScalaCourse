package week2

 import FunSets._

object settest {
 val s1 = singletonSet(1)                         //> s1  : Int => Boolean = <function1>
 val s2 = singletonSet(3)                         //> s2  : Int => Boolean = <function1>
 val un = union(s1, s2)                           //> un  : Int => Boolean = <function1>
 val inter = intersect(s1, s2)                    //> inter  : Int => Boolean = <function1>
 val di = diff(s1, s2)                            //> di  : Int => Boolean = <function1>
 
 val check = di                                   //> check  : Int => Boolean = <function1>
 contains(check, 1)                               //> res0: Boolean = true
 contains(check, 2)                               //> res1: Boolean = false
 contains(check, 3)                               //> res2: Boolean = false
 
 
}