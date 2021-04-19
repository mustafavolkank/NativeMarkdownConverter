package main

import logging.Logger

import converter.NativeMarkdownConverter
import file.FileHelper

import scala.util.{Failure, Success, Try}

object Main {

  def main(args: Array[String]): Unit = {
    val inputFileName: String = "input.nnml"
    val outputFileName: String = "output.html"

    val inputTry: Try[String] = FileHelper.readFile(inputFileName)

    inputTry match {
      case Failure(e) => // exception is already logged, so do nothing
      case Success(inputString: String) => {
        val nativeMarkdownConverter: NativeMarkdownConverter = new NativeMarkdownConverter
        val htmlOutput: String = nativeMarkdownConverter.convertToHtml(inputString)
        FileHelper.writeFile(outputFileName, htmlOutput)
        Logger.info("Conversion successfull. The converted html text is written to output.html file...")

      }
    }
  }

}
