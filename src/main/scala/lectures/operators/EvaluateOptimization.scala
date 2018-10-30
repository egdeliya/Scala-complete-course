package lectures.operators

import lectures.functions.{Computation, CurriedComputation, Data, FunctionalComputation}

/**
  * В задачке из lectures.functions.Computations мы реализовали
  * один и тот же метод 3-мя разными способами
  *
  * Пришло время оценить, насколько разные имплементации
  * отличаются друг от друга по производительности
  *
  * Для этого
  *   * в классах CurriedComputation и FunctionalComputation уберите extends App, оставьте extends Data
  *   * раскомментируйте код, выполните в циклах вызов 3-х имплементаций,
  *   * оцените разницу во времени выполнения и объясните ее происхожение
  *
  */
object EvaluateOptimization extends App with Data {

  val computationStartTimestamp = System.currentTimeMillis()

  val gen = 1 to 100

  // ВЫПОЛНИТЬ В ЦИКЛЕ ОТ 1 ДО 100 Computation.computation

  // --------------------------------------------------------------------------
  // Вызовы
    // filterData.split(" ")
    // dataProducer.filter
  // будут осуществляться каждый раз при вызове computation, то есть 100 раз,
  // поэтому время ~ 100 * (t1 + t2),
  // где t1 - время выполнения filterData.split,
  // t2 - dataProducer.filter
  // --------------------------------------------------------------------------
  for (_ <- gen) {
    Computation.computation(filterData, dataArray)
  }

  println("Elapsed time in computation(): " + (System.currentTimeMillis() - computationStartTimestamp))



  val partiallyAppliedStartTimestamp = System.currentTimeMillis()

  // ВЫПОЛНИТЬ В ЦИКЛЕ ОТ 1 ДО 100 CurriedComputation.partiallyAppliedCurriedFunction

  // --------------------------------------------------------------------------
  // partiallyAppliedCurriedFunction  работает по сути также,
  // как computation, только сначала фиксируется первый агрумент,
  // и когда поступает второй аргумент - осуществляется вызов
  // функции.
  // Время работы ~ 100 * (t1 + t2),
  // где t1 - это время выполнения filterData.split,
  // t2 - dataProducer.filter
  // --------------------------------------------------------------------------
  for (_ <- gen) {
    CurriedComputation.partiallyAppliedCurriedFunction(dataArray)
  }

  val partiallyAppliedDuration = System.currentTimeMillis() - partiallyAppliedStartTimestamp
  println("Elapsed time in partiallyAppliedCurriedFunction(): " + partiallyAppliedDuration)

  val filterAppliedStartTimestamp = System.currentTimeMillis()

  // ВЫПОЛНИТЬ В ЦИКЛЕ ОТ 1 ДО 100 FunctionalComputation.filterApplied

  // --------------------------------------------------------------------------
  // Вызов filterData.split
  // будут выполнен 1 раз при инициализации функции filterApplied,
  // далее будет вызываться внутренняя функция computation
  // поэтому время ~ t1 + 100 * t2,
  // где t1 - это время выполнения filterData.split
  // t2 - время выполнения dataProducer.filter
  // --------------------------------------------------------------------------
  for (_ <- gen) {
    FunctionalComputation.filterApplied(dataArray)
  }

  val filterAppliedDuration = System.currentTimeMillis() - filterAppliedStartTimestamp
  println("Elapsed time in filterApplied():" + filterAppliedDuration)

  // ВЫВЕСТИ РАЗНИЦУ В ПРОДОЛЖИТЕЛЬНОСТИ ВЫПОЛНЕНИЯ МЕЖДУ КАРРИРОВАННОЙ ВЕРСИЕЙ
  // И ФУНКЦИОНАЛЬНОЙ

  val diff = partiallyAppliedDuration - filterAppliedDuration

  println(s"Difference is about $diff milliseconds")
}

