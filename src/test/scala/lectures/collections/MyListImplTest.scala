package lectures.collections

import lectures.collections.MyListImpl.{MyList, MyListBuffer, MyIndexedList}
import org.scalatest.{FunSuite, Matchers}

import scala.collection.mutable.{ArrayBuffer, ListBuffer}

class MyListImplTest extends FunSuite with Matchers {

  val defaultList = List(1, 2, 3, 4, 5, 6)
  val defaultListBuffer: ListBuffer[Long] = ListBuffer(1, 2, 3, 4, 5, 6)
  val defaultArrayBuffer = ArrayBuffer.empty[Float]

  test("MyList map should work correctly") {
    MyList[Int, List[Int]](defaultList).map(p => p * 2).data shouldBe List(2, 4, 6, 8, 10, 12)
  }

  test("MyList filter should work correctly") {
    MyList[Long, ListBuffer[Long]](defaultListBuffer).filter(_ % 2 == 0).data shouldBe List(2, 4, 6)
  }

  test("MyList foldLeft should work correctly") {
    MyList[Int, List[Int]](defaultList).foldLeft(0)((tpl) => tpl._1 + tpl._2) shouldBe 21
    MyList[Float, IndexedSeq[Float]](defaultArrayBuffer).foldLeft(0)((tpl) => tpl._1 + tpl._2) shouldBe 0
  }

  test("MyListBuffer filter should work correctly") {
    new MyListBuffer[Long](defaultListBuffer).filter(_ % 2 == 0).data shouldBe List(2, 4, 6)
  }

  test("MyIndexedList foldLeft should work correctly") {
    new MyIndexedList[Float](defaultArrayBuffer).foldLeft(0)((tpl) => tpl._1 + tpl._2) == 0
  }

}
