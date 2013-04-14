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
 
 val s10  = singletonSet(3)                       //> s10  : Int => Boolean = <function1>
 
 val fil = filter(x => x==3, s10)                 //> fil  : Int => Boolean = <function1>
 contains(fil, 3)                                 //> res3: Boolean = true
 
  val all: Set = x => x >= -10 && x <= 90         //> all  : Int => Boolean = <function1>
  contains(all, 1)                                //> res4: Boolean = true
  contains(all, 1000)                             //> res5: Boolean = false
  contains(all, 1001)                             //> res6: Boolean = false
  contains(all, -1)                               //> res7: Boolean = true
  contains(all, -1000)                            //> res8: Boolean = false
  
  val onetwo: Set = x => x==1 || x ==2            //> onetwo  : Int => Boolean = <function1>
  contains(onetwo, 1)                             //> res9: Boolean = true
  contains(onetwo, 2)                             //> res10: Boolean = true
  contains(onetwo, 3)                             //> res11: Boolean = false
  val int = union(onetwo, x => x==1)              //> int  : Int => Boolean = <function1>
  contains(int, 1)                                //> res12: Boolean = true
  contains(int, 2)                                //> res13: Boolean = true
  contains(int, 3)                                //> res14: Boolean = false
  
 exists(onetwo, int)                              //> res15: Boolean = false
}