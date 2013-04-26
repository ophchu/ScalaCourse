package week4

trait Exper {
	def eval :Int = this match {
	  case Number(n) => n
	  case Sum(e1, e2) => e1.eval + e2.eval
	}
}
case class Number(n: Int) extends Exper
case class Sum(e1: Exper, e2: Exper) extends Exper