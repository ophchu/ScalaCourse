package week1


object tailrec extends App{
  
  def factorial(n: Int): Int =
    if (n == 0) 1 else n * factorial(n - 1)
  
    
    def tailFactorial(n: Int): Int =
    if (n == 0) 1 else factorial(n * (n - 1))
    
    def x = 10
    println(factorial(x))
    println(tailFactorial(x))
}