package lectures.collections

import scala.collection.mutable.ListBuffer

/**
  * Представим, что по какой-то причине Вам понадобилась своя обертка над списком целых чисел List[Int]
  *
  * Вы приняли решение, что будет достаточно реализовать 4 метода:
  * * * * * def flatMap(f: (Int => MyList)) -  реализуете на основе соответствующего метода из List
  * * * * * метод map(f: (Int) => Int) - с помощью только что полученного метода flatMap класса MyList
  * * * * * filter(???) - через метод flatMap класса MyList
  * * * * * foldLeft(acc: Int)(???) - через декомпозицию на head и tail
  *
  * Для того, чтобы выполнить задание:
  * * * * * раскомментируйте код
  * * * * * замените знаки вопроса на сигнатуры и тела методов
  * * * * * не используйте var и мутабильные коллекции
  *
  */
object MyListImpl extends App {

  case class MyList[T, DataType <: Seq[T]](data: DataType) {

    def flatMap[U](f: (T => Seq[U])): MyList[U, Seq[U]] = {
      MyList(data.flatMap(inp => f(inp)))
    }

    def map[U](f: T => U): MyList[U, Seq[U]] = {
      flatMap[U](elem => Seq(f(elem)))
    }

    def foldLeft(acc: T)(f: ((T, T)) => T): T = data match {
        case Seq() => acc
        case head +: tail => MyList[T, Seq[T]](tail).foldLeft(f(acc, head))(f)
    }

    def filter(f: T => Boolean): MyList[T, Seq[T]] =
      flatMap[T](elem => {
        if (f(elem)) Seq(elem)
        else Seq.empty
      })
    }



  class MyListBuffer[T](data: ListBuffer[T]) extends MyList[T, ListBuffer[T]](data) {}

  class MyIndexedList[T](data: IndexedSeq[T]) extends MyList[T, IndexedSeq[T]](data) {}

}