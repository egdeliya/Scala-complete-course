package lectures.collections

/**
  * Постарайтесь не использовать мутабильные коллекции и var
  * Подробнее о сортировке можно подсмотреть здесь - https://en.wikipedia.org/wiki/Merge_sort
  *
  */
object MergeSortImpl extends App {

  private def merge(left: Seq[Int], right: Seq[Int]): Seq[Int] = {
    def mergeAcc(curLeft: Int, curRight: Int, acc: Seq[Int]): Seq[Int] = {
      if (curLeft >= left.size) acc ++ right.slice(curRight, right.size)
      else if (curRight>= right.size) acc ++ left.slice(curLeft, left.size)
      else if (left(curLeft) < right(curRight)) mergeAcc(curLeft + 1, curRight, acc :+ left(curLeft))
      else mergeAcc(curLeft, curRight + 1, acc :+ right(curRight))
    }
    mergeAcc(0, 0, Seq.empty)
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
