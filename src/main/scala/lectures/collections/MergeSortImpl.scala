package lectures.collections

/**
  * Постарайтесь не использовать мутабильные коллекции и var
  * Подробнее о сортировке можно подсмотреть здесь - https://en.wikipedia.org/wiki/Merge_sort
  *
  */
object MergeSortImpl extends App {

  private def merge(left: Seq[Int], right: Seq[Int]): Seq[Int] = (left, right) match {
      case (Nil, rightSeq) => rightSeq
      case (leftSeq, Nil) => leftSeq
      case (leftHead :: leftTail, rightHead :: rightTail) =>
          if (leftHead < rightHead) Seq[Int](leftHead) ++ merge(leftTail, right)
          else Seq[Int](rightHead) ++ merge(left, rightTail)
  }

  private def mergeSort(left: Int, right: Int, data: Seq[Int]): Seq[Int] = {
    if (right - left < 2) data.slice(left, right)
    else {
      val mid = (left + right) / 2
      val leftArr = mergeSort(left, mid, data)
      val rightArr = mergeSort(mid, right, data)
      merge(leftArr, rightArr)
    }
  }

  def mergeSort(data: Seq[Int]): Seq[Int] = {
    mergeSort(0, data.size, data)
  }

}
