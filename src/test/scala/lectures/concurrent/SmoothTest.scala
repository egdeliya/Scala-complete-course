package lectures.concurrent

import org.scalatest.{AsyncFlatSpec, Matchers}
import scala.concurrent.Future

class SmoothTest extends AsyncFlatSpec with Matchers {

  it should "not run code if previous invocation has not completed yet" in {
    var p: Int = 1
    val res = Smooth {
      Thread.sleep(1000)
      p = p + 1
      p
    }

    val futureRes1: Future[Int] = res()
    val futureRes2: Future[Int] = res()

    Future
      .sequence(Seq(futureRes1, futureRes2))
      .map(seq => assert(seq.head == seq.last))
  }

  it should "run code if previous invocation has completed already" in {
    var p: Int = 1
    val res = Smooth {
      p = p + 1
      p
    }

    val futureRes1: Future[Int] = res()
    Thread.sleep(200)
    val futureRes2: Future[Int] = res()

    Future
      .sequence(Seq(futureRes1, futureRes2))
      .map(seq => assert(seq.head != seq.last))
  }
}
