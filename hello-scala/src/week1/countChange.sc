package week1

object countChange {
  /**
   * Exercise 3
   */
  def countChange(money: Int, coins: List[Int]): Int = {
    def calc(sum: Int, coins: List[Int]): Int = {
    	if (coins.isEmpty) 0 else
	    if (sum == money) 1 else
	    if (sum < money) calc(sum + coins.head, coins) +
	    calc(sum, coins.tail)
	    else
	    0
    }
    calc(0, coins)
  }                                               //> countChange: (money: Int, coins: List[Int])Int
  countChange(3, List(1, 2,3))                    //> res0: Int = 3
  countChange(4, List(1, 2, 3))                   //> res1: Int = 4
  countChange(3, List(1, 2, 3))                   //> res2: Int = 3
  countChange(300,List(5,10,20,50,100,200,500))   //> res3: Int = 1022
  countChange(300,List(500,5,50,100,20,200,10))   //> res4: Int = 1022
  
}