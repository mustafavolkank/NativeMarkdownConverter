package tags

import scala.util.matching.Regex

trait Tag {

  val regex: Regex

  def convert(nativeMarkdownInput: String): String

}
