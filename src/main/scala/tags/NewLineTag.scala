package tags

import scala.util.matching.Regex

/**
  * converts the newlines (\n) to <br/>
  */
class NewLineTag extends Tag {

  override val regex: Regex = """\n""".r

  val htmlTag = "<br/>"

  override def convert(nativeMarkdownInput: String): String = {
    regex.replaceAllIn(nativeMarkdownInput, htmlTag)
  }
}
