package tags

import scala.util.matching.Regex

/**
  * converts the "\aç[sometext]" to "<u>sometext</u>"
  */
class UnderlineTag extends StandardTag {

  override val regex: Regex = """\\aç[\s]*\[(.*?)\]""".r

  override val htmlStartTag: String = "<u>"
  override val htmlEndTag: String = "</u>"

}
