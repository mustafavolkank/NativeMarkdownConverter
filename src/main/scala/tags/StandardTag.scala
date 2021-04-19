package tags

import scala.collection.immutable.Seq
import scala.util.matching.Regex.Match

/**
  * StandardTag represents the tags that does not contain any attributes, only contains start and end html tags
  * examples:
  * \b[sometext]  => <b>sometext</b>
  * \i[sometext]  => <em>sometext</em>
  * \aç[sometext] => <u>sometext</u>
  *
  */
trait StandardTag extends Tag {

  val htmlStartTag: String
  val htmlEndTag: String

  /**
    * converts the given Native Markdown Input to the HTML
    * @param nativeMarkdownInput
    * @return converted html of the given Native Markdown Input
    */
  override def convert(nativeMarkdownInput: String): String = {
    val matchList: Seq[Match] = regex.findAllMatchIn(nativeMarkdownInput).toList
    convertRecursive(matchList, nativeMarkdownInput)
  }

  /**
    * this method replaces the given matches in the given input recursively (for the nested tags)
    * @param matchList
    * @param inputStr
    * @return
    */
  private def convertRecursive(matchList: Seq[Match], inputStr: String): String = {
    matchList.foldLeft(inputStr) { (outputAccumulator, nextMatch) =>
      val matchedText: String = nextMatch.group(1)
      val innerMatchList: Seq[Match] = regex.findAllMatchIn(matchedText).toList

      val (replacedInput: String, replaceText: String) = if (innerMatchList.nonEmpty) {
        val innerReplacedText = convertRecursive(innerMatchList, matchedText)
        val replacedAccumulator = outputAccumulator.replace(matchedText, innerReplacedText)
        (replacedAccumulator, innerReplacedText)
      } else {
        (outputAccumulator, matchedText)
      }

      val outputString = regex.replaceFirstIn(replacedInput, s"$htmlStartTag$replaceText$htmlEndTag")
      outputString
    }
  }

}
