package logging

import java.util.logging
import java.util.logging.Level

/**
  * Global Logger instance
  */
object Logger {

  private val logger: logging.Logger = logging.Logger.getGlobal

  def error(message: String): Unit = {
    logger.log(Level.SEVERE, message)
  }

  def error(message: String, exception: Throwable): Unit = {
    logger.log(Level.SEVERE, message, exception)
  }

  def warn(message: String): Unit = {
    logger.log(Level.WARNING, message)
  }

  def warn(message: String, exception: Throwable): Unit = {
    logger.log(Level.WARNING, message, exception)
  }

  def info(message: String): Unit = {
    logger.log(Level.INFO, message)
  }

  def info(message: String, exception: Throwable): Unit = {
    logger.log(Level.INFO, message, exception)
  }

}
