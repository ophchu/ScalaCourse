package week4

abstract class MyBoolean {
	def ifThenElse[T](t: => T, e: => T) : T
	
	def && (x: => MyBoolean) : MyBoolean = ifThenElse(x, myFalse)
	def || (x: => MyBoolean) : MyBoolean = ifThenElse(myTrue, x)
	def unary_!(): MyBoolean = ifThenElse(myFalse, myTrue)
	
	def == (x: => MyBoolean) : MyBoolean = ifThenElse(x, x.unary_!)
	def != (x: => MyBoolean) : MyBoolean = ifThenElse(x.unary_!, x)
	
	def < (x: => MyBoolean) : MyBoolean = ifThenElse(myFalse, x)
	
	object myTrue extends MyBoolean{
	  def ifThenElse[T](t: => T, e: => T) : T = t
	}
	
	object myFalse extends MyBoolean{
	  def ifThenElse[T](t: => T, e: => T) : T = e
	}
	
}