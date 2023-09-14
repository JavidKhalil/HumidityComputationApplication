package parser

import util.FilesUtil.{getFiles, readFile}
import zio.ZIO

/**
 * Parser for files` content
 *
 */
object Parser {

  def parseFilesContent(path: String): ZIO[Any, Throwable, List[List[String]]] = for {
    files <- getFiles(path)
    contentLines <- ZIO.foreach(files) { file =>
      readFile(zio.nio.file.Path.fromJava(file.toPath))
    }
  } yield contentLines

}