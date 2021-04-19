package converter

import java.util.Base64

import scala.collection.immutable
import scala.util.matching.Regex
import scala.util.matching.Regex.Match

object SpecialCharHelper {

  val backSlash = """\"""
  val encodedBackSlash = Base64.getEncoder.encodeToString("""\""".getBytes)

  val regex: Regex = """\\b[\s]*\[(.*?)\][\s]*\((.*?)\)|(\\r[\s]*\[(.*?)\][\s]*\((.*?)\))""".r

  /**
    * encodes the backslashes in the "url" attribute of link tag and "url", "alt" attributes of the image tag
    * this is performed because of the following rules defined in the project description document
    *
    * rule ====> url​ specifications can’t contain any tags.
    * rule ====> alt​(alternative) text for the ​r ​tag(image) can’t contain any tags
    *
    * @param input
    * @return
    */
  def preProcess(input: String): String = {
    val matchList: immutable.Seq[Match] = regex.findAllMatchIn(input).toList
    val encodedInput: String = matchList.foldLeft(input) { (accumulatorOutput: String, nextMatch: Match) =>
      val linkTagUrl: String = Option(nextMatch.group(2)).getOrElse("")
      val imageTagAlt: String = Option(nextMatch.group(4)).getOrElse("")
      val imageTagUrl: String = Option(nextMatch.group(5)).getOrElse("")

      // encodes the url attribute of link tag
      val inputUrlAttrEncoded: String = if (linkTagUrl.nonEmpty) {
        val encodedLinkTagUrl: String = linkTagUrl.replace(backSlash, encodedBackSlash)
        accumulatorOutput.replace(linkTagUrl, encodedLinkTagUrl)
      } else {
        input
      }

      // encodes the alt attribute of image tag
      val inputAltAttrEncoded: String = if (imageTagAlt.nonEmpty) {
        val encodedImageTagAlt: String = imageTagAlt.replace(backSlash, encodedBackSlash)
        inputUrlAttrEncoded.replace(imageTagAlt, encodedImageTagAlt)
      } else {
        inputUrlAttrEncoded
      }

      // encodes the url attribute of image tag
      val encodedSingleMatchStr = if (imageTagUrl.nonEmpty) {
        val encodedImageTagUrl: String = imageTagUrl.replace(backSlash, encodedBackSlash)
        inputAltAttrEncoded.replace(imageTagUrl, encodedImageTagUrl)
      } else {
        inputAltAttrEncoded
      }

      encodedSingleMatchStr
    }

    encodedInput
  }

  /**
    * decodes the encoded backslash characters
    * @param input
    * @return
    */
  def postProcess(input: String): String = {
    input.replace(encodedBackSlash, backSlash)
  }

}
