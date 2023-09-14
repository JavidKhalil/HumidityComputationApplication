import computation.ComputeStatistic
import zio.test._
import zio.test.Assertion._
import zio.test.ZIOSpecDefault

object ComputeStatisticSpec extends ZIOSpecDefault {

  def spec = suite("ComputeStatistic")(
    test("measurementSensorsStatistic should compute statistics correctly") {
      val listOfFilesContent = List(
        List("s1,NaN,54,98", "s1,78,82,88", "s2,65,79,93"),
        List("s2,NaN,62,90", "s3,78,82,88", "s3,65,79,93")
      )

      val expectedOutput = "(s3,65,80,93)\n(s1,NaN,80,98)\n(s2,NaN,77,93)"

      assert(ComputeStatistic.measurementSensorsStatistic(listOfFilesContent))(equalTo(expectedOutput))
    },
    test("measurement number should count measurements correctly") {
      val listOfFilesContent = List(
        List("s1,NaN,54,98", "s1,78,82,88", "s2,65,79,93"),
        List("s2,NaN,62,90", "s3,78,82,88", "s3,65,79,93")
      )

      val expectedCount = 6

      assert(ComputeStatistic.measurementNumber(listOfFilesContent))(equalTo(expectedCount))
    },
    test("measurement failed number should count failed measurements correctly") {
      val listOfFilesContent = List(
        List("s1,NaN,54,98", "s1,78,82,88", "s2,65,79,93"),
        List("s2,NaN,62,90", "s3,78,82,88", "s3,65,79,93")
      )

      val expectedCount = 2

      assert(ComputeStatistic.measurementFailedNumber(listOfFilesContent))(equalTo(expectedCount))
    }
  )
}