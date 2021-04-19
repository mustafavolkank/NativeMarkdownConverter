package tags

import scala.util.matching.Regex

/**
  * converts "\i[sometext]" to "<em>sometext</em>"
  */
class ItalicTag extends StandardTag {

  override val regex: Regex = """\\i[\s]*\[(.*?)\]""".r

  override val htmlStartTag: String = "<em>"
  override val htmlEndTag: String = "</em>"

}
