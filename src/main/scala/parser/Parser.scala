package parser

import util.FilesUtil.{getFiles, readFile}
import zio.ZIO

/**
 * Parser for files` content
 *
 */
object Parser {

  def parseFilesContent(path: String): ZIO[Any, Throwable, List[List[String]]] = for {
    fiberFiles <- getFiles(path).fork
    files <- fiberFiles.join
    contentLines <- ZIO.foreachPar(files) { file =>
      readFile(zio.nio.file.Path.fromJava(file.toPath))
    }
  } yield contentLines

}