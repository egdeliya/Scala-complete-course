package lectures.concurrent

import java.util.concurrent.atomic.AtomicReference

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
/**
  * Smooth - это своебразный функциональный кэш, предназначенный для исключения повторных вызовов кода
  * до того, как получен результат первого вызова.
  * Он работает следующим образом:
  * * * * в объект Smooth в метод apply передается код, который может выполняться какое-то время, и возвращает какое-то значение
  * * * * apply создаст инстанс Smooth
  * * * * созданный инстанс при вызове apply возвращает Future
  * * * * * и запускает код, если код еще не запущен
  * * * * * и не запускает код, если код еще не завершился с момента предыдущего запуска
  *
  * Подсказка: можно использовать AtomicReference
  *
  */
object Smooth {
  def apply[T](thunk: => T): Smooth[T] = new Smooth(thunk)
}

class Smooth[T] private (body: => T) {
  private val workingFuture: AtomicReference[Future[T]] = new AtomicReference(null)

  def apply(): Future[T] = {

    if (workingFuture.get() == null || workingFuture.get().isCompleted) {
      val newFuture = Future {
        body
      }
      workingFuture.set(newFuture)
    }

    workingFuture.get()
  }
}
