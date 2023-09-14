package exception

/**
 * Domain model exceptions
 *
 */
object HumidityException {

  trait HumidityAppException extends Throwable

  case class NotDirectoryException(message: String) extends HumidityAppException

  case class FileNotFoundInPath(message: String) extends HumidityAppException

}
