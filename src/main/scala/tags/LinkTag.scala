package tags

import scala.util.matching.Regex
import scala.util.matching.Regex.Match

/**
  * converts the "\b[sometext](url)" to "<a href="url">sometext</a>"
  */
class LinkTag extends Tag {

  val regex: Regex = """\\b[\s]*\[(.*?)\][\s]*\((.*?)\)""".r

  override def convert(inputString: String): String = {
    val matchList = regex.findAllMatchIn(inputString).toList

    val outputString: String =  matchList.foldLeft(inputString) { (outputAccumulator: String, nextMatch: Match) =>
      val hrefAttribute: String = nextMatch.group(2)
      val matchedText: String = nextMatch.group(1)
      regex.replaceFirstIn(outputAccumulator, s"""<a href="$hrefAttribute">$matchedText</a>""")
    }

    outputString
  }

}
