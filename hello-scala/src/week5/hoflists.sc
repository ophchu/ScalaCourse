package week5

object hoflists {
  def scaleList(xs: List[Double], factor: Double) =
    xs map (x => x * factor)                      //> scaleList: (xs: List[Double], factor: Double)List[Double]

  val dl1 = List(1.0, 5.0, 3.0)                   //> dl1  : List[Double] = List(1.0, 5.0, 3.0)
  scaleList(dl1, 3)                               //> res0: List[Double] = List(3.0, 15.0, 9.0)

  def squareList(xs: List[Int]): List[Int] = xs match {
    case Nil => xs
    case y :: ys => y * y :: squareList(ys)
  }                                               //> squareList: (xs: List[Int])List[Int]
  def mapSquareList(xs: List[Int]): List[Int] =
    xs map (x => x*x)                             //> mapSquareList: (xs: List[Int])List[Int]
  
  
  val intL = List(-4,1,-5, 3,5)                   //> intL  : List[Int] = List(-4, 1, -5, 3, 5)
  squareList(intL)                                //> res1: List[Int] = List(16, 1, 25, 9, 25)
  mapSquareList(intL)                             //> res2: List[Int] = List(16, 1, 25, 9, 25)
  
  def posElements(xs: List[Int]): List[Int] =
  xs filter(x => x >0)                            //> posElements: (xs: List[Int])List[Int]
  
  posElements(intL)                               //> res3: List[Int] = List(1, 3, 5)
}