package converter

import tags._

/**
  * This class converts the given Native Markdown Language input to the HTML
  */
class NativeMarkdownConverter {

  val tags: Seq[Tag] = Seq(
    new ItalicTag,
    new BoldTag,
    new UnderlineTag,
    new ImageTag,
    new LinkTag,
    new NewLineTag
  )

  /**
    * converts the given Native Markdown to HTML.
    * 1. in the preprocess step encodes the backslash characters inside the url and alt attributes
    * 2. loops through the tags and converts the input to html tags
    * 3. decodes the backslashes that encoded in the first step
    * @param nativeMarkdownInput
    * @return
    */
  def convertToHtml(nativeMarkdownInput: String): String = {
    val specialCharEncodedInput: String = SpecialCharHelper.preProcess(nativeMarkdownInput)
    val encodedHtmlOutput: String = tags.foldLeft(specialCharEncodedInput) { (outputAccumulator: String, nextTag: Tag) =>
      nextTag.convert(outputAccumulator)
    }
    val htmlOutput = SpecialCharHelper.postProcess(encodedHtmlOutput)
    htmlOutput
  }

}
