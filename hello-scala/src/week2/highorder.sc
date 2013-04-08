package week2

object highorder {
	def cube(x: Int): Int= x * x * x          //> cube: (x: Int)Int
 	def sumCubes(a: Int, b: Int): Int =
 		if (a > b) 0
 		else cube(a) + sumCubes(a + 1, b) //> sumCubes: (a: Int, b: Int)Int
                                                  
                                                  
	sumCubes(3, 3)                            //> res0: Int = 27
	
	
	def sum (f: Int => Int, a: Int, b: Int): Int =
		if (a > b) 0
		else f(a) + sum(f, a + 1, b)      //> sum: (f: Int => Int, a: Int, b: Int)Int
		
		def id(x : Int) : Int = x         //> id: (x: Int)Int
		
		def sumInts(a: Int, b: Int) = sum(id, a, b)
                                                  //> sumInts: (a: Int, b: Int)Int
		def sumInts2(a: Int, b: Int) = sum(x => x*x, a, b)
                                                  //> sumInts2: (a: Int, b: Int)Int
            
	def dw = 4                                //> dw: => Int
		sumInts(1, dw)                    //> res1: Int = 10
		sumInts2(1, dw)                   //> res2: Int = 30
                                                  
}