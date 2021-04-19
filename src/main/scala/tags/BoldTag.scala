package tags

import scala.util.matching.Regex

/**
  * converts the "\b[sometext]" to "<b>sometext</b>"
  */
class BoldTag extends StandardTag {

  override val regex: Regex = """\\k[\s]*\[(.*?)\]""".r

  override val htmlStartTag: String = "<b>"
  override val htmlEndTag: String = "</b>"

}
