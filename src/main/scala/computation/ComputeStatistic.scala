package computation

/**
 * Computation business logic
 *
 */
object ComputeStatistic {

  def measurementSensorsStatistic(listOfFilesContent: List[List[String]]) = {
    /**
     * Sort by id
     */
    val sorted: List[String] = listOfFilesContent.flatten.filter(!_.contains("sensor")).sortBy {
      _.split(",")(0)
    }

    /**
     * group results
     *
     */
    val res: List[String] = sorted.foldLeft(List.empty[String])((acc, item) => {
      (acc, item) match {
        case (Nil, item) =>
          List(item)
        case (head :: Nil, item) if head.startsWith(s"${item.take(2)}") =>
          List(head + item.drop(2))
        case (l, item) if (l.last.startsWith(s"${item.take(2)}")) =>
          l.init :+ (l.last + item.drop(2))
        case (l@_ :: last :: Nil, item) if !last.startsWith(s"${item.take(2)}") =>
          l ::: List(item)
        case (l, item) =>
          l ::: List(item)
      }
    })

    /**
     * Join results into tuples
     *
     */
    val finalComputation: List[(String, Any, Any, Any)] = res.map { line =>
      //val clean =
      val list: List[String] = line.drop(2).split(",").toList
      val numbersList = list.filter(_.matches("""^\d+$""")).map(_.trim.toInt)
      val size = numbersList.size
      val avg = if (size == 0) "NaN" else numbersList.sum / size
      val min = if (list.contains("NaN")) "NaN" else numbersList.min
      val max = if (size == 0) "NaN" else numbersList.max
      (line.take(2), min, avg, max)
    }

    /**
     * Customise the display results
     *
     */
    finalComputation.sortBy { line =>
      val avg = line._2
      if (avg.isInstanceOf[String]) {
        100
      } else {
        -avg.asInstanceOf[Int]
      }
    }.map(_.productIterator.mkString("(", ",", ")")).mkString("\n")

  }

  // number of measurements
  def measurementNumber(listOfFilesContent: List[List[String]]) =
    listOfFilesContent.foldLeft(0)((counter, list) => counter + list.size)

  // failed measurements number
  def measurementFailedNumber(listOfFilesContent: List[List[String]]) =
    listOfFilesContent.foldLeft(0)((counter, list) => counter + list.count(_.contains("NaN")))

}