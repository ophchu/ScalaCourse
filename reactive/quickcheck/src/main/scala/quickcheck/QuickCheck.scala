package quickcheck

import org.scalacheck.Arbitrary._
import org.scalacheck.Prop._
import org.scalacheck.Gen._
import org.scalacheck._

abstract class QuickCheckHeap extends Properties("Heap") with IntHeap {

  property("min1") = forAll { a: Int =>
    val h = insert(a, empty)
    findMin(h) == a
  }

  property("minOutOfTwo") = forAll { (a: Int, b: Int) =>
    val h = insert(b, insert(a, empty))
    findMin(h) == Math.min(a, b)
  }

  property("isEmpty1") = forAll { a: Int =>
    val h = deleteMin(insert(a, empty))
    isEmpty(h)
  }

  property("genOrderedMin") = forAll(genHeap) { (h: H) =>
    checkOrdered(h)
  }

  property("minOutOfTwo") = forAll { (intList: List[Int]) =>
    def checkMinExists(h1: H, resList: List[Int]): Boolean =
      isEmpty(h1) match {
        case true => resList.isEmpty
        case false => findMin(h1) == resList.head && checkMinExists(deleteMin(h1), resList.tail)
      }
    val h1 = intList.foldRight(empty)((x, h) => insert(x, h))
    checkMinExists(h1, intList.sorted)
  }

  def checkOrdered(h: H): Boolean = {
    isEmpty(h) match {
      case true => true
      case false =>
        isEmpty(deleteMin(h)) match {
          case true => true
          case false => ord.lteq(findMin(h), findMin(deleteMin(h))) && checkOrdered(deleteMin(h))
        }
    }
  }

  property("genMin1") = forAll(genHeap) { (h: H) =>
    val m = if (isEmpty(h)) 0 else findMin(h)
    findMin(insert(m, h)) == m
  }

  property("minMeld") = forAll { (h: H, h1: H) =>
    findMin(meld(h, h1)) == Math.min(findMin(h), findMin(h1))
  }

  property("delMinMeld") = forAll { (h: H, h1: H) =>
    val melds = meld(h, h1)
    findMin(melds) == ord.min(findMin(h), findMin(h1))
  }

  property("minOfTwo") = forAll(genHeap) { (h: H) =>
    val m = if (isEmpty(h)) 0 else findMin(h)
    findMin(insert(m, h)) == m
  }

  property("put100") = forAll { (h: H) =>
    val originCnt = count(h)
    val h1 = populate(100, h)
    val cnt = count(h1)

    cnt == originCnt + 100
  }

  def count(h: H): Int = {
    isEmpty(h) match {
      case true => 0
      case false => 1 + count(deleteMin(h))
    }
  }

  def populate(n: Int, h: H): H = {
    if (n > 0) {
      insert(n, populate(n - 1, h))
    } else {
      h
    }
  }

  lazy val gen2Heap = const(genHeap, genHeap)


  lazy val genHeap: Gen[H] = for {
    v <- arbitrary[A]
    h <- oneOf(const(empty), genHeap)
  } yield insert(v, h)

  implicit lazy val arbHeap: Arbitrary[H] = Arbitrary(genHeap)

}
