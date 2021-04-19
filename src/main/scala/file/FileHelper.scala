package file

import java.io.{BufferedWriter, File, FileNotFoundException, FileWriter}

import logging.Logger

import scala.io.Source
import scala.util.{Failure, Success, Try}

/**
  * Helper object for IO operations
  */
object FileHelper {

  /**
    * reads the given file and returns the contents if successful
    * if not successful logs the exception and returns Failure
    * @param fileName
    * @return
    */
  def readFile(fileName: String): Try[String] = {
    try {
      val resultString: String = Source.fromFile(fileName).mkString
      Success(resultString)
    } catch {
      case e: FileNotFoundException => {
        Logger.error(s"could not read file: $fileName, file is not found!", e)
        Failure(e)
      }
      case e: Exception => {
        Logger.error(s"unknown error occurred while reading file: $fileName", e)
        Failure(e)
      }
    }
  }

  /**
    * writes the given content to the given file, if any exception occurs logs the exception
    * @param fileName
    * @param outputString
    */
  def writeFile(fileName: String, outputString: String): Unit = {
    try {
      val file = new File(fileName)
      val bufferedWriter = new BufferedWriter(new FileWriter(file))
      bufferedWriter.write(outputString)
      bufferedWriter.close
    } catch {
      case e: Exception => {
        Logger.error(s"an error occurred while writing to file: $fileName", e)
      }
    }
  }


}
