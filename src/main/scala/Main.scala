import computation.ComputeStatistic.{measurementFailedNumber, measurementNumber, measurementSensorsStatistic}
import parser.Parser.parseFilesContent
import zio._
import zio.Console.{printLine, readLine}

import java.io.IOException

object Main extends ZIOAppDefault {

  def displayParseResults(listOfFilesContent: List[List[String]]): ZIO[Any, IOException, Unit] = {
    for {
      _ <- printLine(s"Num of processed files: ${listOfFilesContent.size}")
      _ <- printLine(s"Num of processed measurements: ${measurementNumber(listOfFilesContent)}")
      _ <- printLine(s"Num of failed measurements: ${measurementFailedNumber(listOfFilesContent)}")
      _ <- printLine(s"Sensors with highest average humidity:\n")
      _ <- printLine("sensor-id,min,avg,max")
      _ <- printLine(measurementSensorsStatistic(listOfFilesContent))
    } yield ()
  }

  val program = for {
    _ <- printLine("Please, enter the path for loading reports..")
    path <- readLine
    listOfFilesContent <- parseFilesContent(path)
    _ <- displayParseResults(listOfFilesContent)
  } yield ()

  override def run: ZIO[Environment with ZIOAppArgs with Scope, Any, Any] =
    program.as(Exit.Success)
}