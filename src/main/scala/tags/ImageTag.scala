package tags

import scala.collection.immutable.Seq
import scala.util.matching.Regex
import scala.util.matching.Regex.Match

/**
  * converts the "\r[sometext](url)" to "<img alt="sometext" url="url" />"
  */
class ImageTag extends tags.Tag {

  override val regex: Regex = """\\r[\s]*\[?(.*?)\]?[\s]*\((.+?)\)""".r

  override def convert(nativeMarkdownInput: String): String = {
    val matchList: Seq[Match] = regex.findAllMatchIn(nativeMarkdownInput).toList

    val outputString: String =  matchList.foldLeft(nativeMarkdownInput) { (outputAccumulator: String, nextMatch: Match) =>
      val matchedAltAttribute: String = nextMatch.group(1)
      val srcAttribute: String = nextMatch.group(2)

      /**
        * alternative text of image is optional, if it is empty, url without the extension part should be used
        * example from the document ==> \r(mycar.jpg)​ converted into ​<img src="mycar.jpg" alt="mycar" />
        */
      val altAttribute: String = if (matchedAltAttribute.isEmpty) {
        srcAttribute.split('.').head // srcAttribute cannot be empty, its guaranteed with the regex
      } else {
        matchedAltAttribute
      }

      regex.replaceFirstIn(outputAccumulator, s"""<img alt="$altAttribute" src="$srcAttribute"/>""")
    }

    outputString
  }

}
