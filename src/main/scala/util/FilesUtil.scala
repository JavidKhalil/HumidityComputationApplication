package util

import exception.HumidityException.{FileNotFoundInPath, HumidityAppException, NotDirectoryException}
import zio.{Task, ZIO}
import zio.nio.charset.Charset

import java.io.{File, IOException}
import zio.nio.file.Path
import zio.nio.file.Files.readAllLines

/**
 * File utils:
 * read
 * list files
 *
 */
object FilesUtil {

  def readFile(path: Path): ZIO[Any, IOException, List[String]] = {
    readAllLines(path, Charset.forName("UTF8"))
  }

  def getFiles(path: String): ZIO[Any, Throwable, List[File]] =
    for {
      fibErrorOrLines <- listFilesFromDirectory(path).fork
      fibAwait <- fibErrorOrLines.join
      files <- fibAwait match {
        case Left(err) => ZIO.fail(err)
        case Right(filesList) => ZIO.succeed(filesList)
      }
    } yield files

  def listFilesFromDirectory(path: String): Task[Either[HumidityAppException, List[File]]] =
    ZIO.attempt(zio.nio.file.Path(path)).fold(
      (err: Throwable) => Left(FileNotFoundInPath(err.getMessage)),
      (path: zio.nio.file.Path) => {
        val file = path.toFile
        if (file.isDirectory) {
          Right(file.listFiles().toList.filter(_.getAbsolutePath.endsWith(".csv")))
        } else {
          Left(NotDirectoryException(s"$path is not a directory"))
        }
      }
    )

}