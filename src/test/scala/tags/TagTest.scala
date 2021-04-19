package tags

import converter.NativeMarkdownConverter
import org.scalatest.FlatSpec
import org.scalatest.Matchers._

class TagTest extends FlatSpec {

  val nativeMarkdownConverter = new NativeMarkdownConverter
  val boldTag = new BoldTag
  val italicTag = new ItalicTag
  val underlineTag = new UnderlineTag
  val newLineTag = new NewLineTag
  val imageTag = new ImageTag
  val linkTag = new LinkTag


  "convertToHtml method" should "successfully convert the given native markdown input to the html tags" in {
    val input = """You are a contractor for a \k[government agency of a \i [third world country] ].
      | They want to develop a native and national \b[\k[markdown language]](http://wpblocked.com/Markdown)
      | (NNML) since they don’t trust \aç[foreign] technology. \r[logo](mylogo.bmp)""".stripMargin

    val expectedOutput = """You are a contractor for a <b>government agency of a <em>third world country</em> </b>.<br/> They want to develop a native and national <a href="http://wpblocked.com/Markdown"><b>markdown language</b></a><br/> (NNML) since they don’t trust <u>foreign</u> technology. <img alt="logo" src="mylogo.bmp"/>"""

    val output = nativeMarkdownConverter.convertToHtml(input)
    output should be(expectedOutput)
  }

  "boldTag convert method" should "successfully convert the native markdown bold tag to html bold tag" in {
    val testInput = """\k[some text]"""
    val expectedOutput = """<b>some text</b>"""
    val htmlOutput: String = boldTag.convert(testInput)
    htmlOutput should be(expectedOutput)
  }

  "italicTag convert method" should "successfully convert the native markdown italic tag to html italic tag" in {
    val testInput = """\i[some text]"""
    val expectedOutput = """<em>some text</em>"""
    val htmlOutput: String = italicTag.convert(testInput)
    htmlOutput should be(expectedOutput)
  }

  "underlineTag convert method" should "successfully convert the native markdown underline tag to html underline tag" in {
    val testInput = """\aç[some text]"""
    val expectedOutput = """<u>some text</u>"""
    val htmlOutput: String = underlineTag.convert(testInput)
    htmlOutput should be(expectedOutput)
  }

  "newLineTag convert method" should "successfully convert the newlines to html newLine tag" in {
    val testInput = "aaa\nbbb"
    val expectedOutput = """aaa<br/>bbb"""
    val htmlOutput: String = newLineTag.convert(testInput)
    htmlOutput should be(expectedOutput)
  }

  "imageTag convert method" should "successfully convert the native markdown image tag to html image tag" in {
    val testInput = """\r[some text](url)"""
    val expectedOutput = """<img alt="some text" src="url"/>"""
    val htmlOutput: String = imageTag.convert(testInput)
    htmlOutput should be(expectedOutput)
  }

  "imageTag convert method" should "use the url attribute without extension part for the alt attribute if alt attribute is omitted" in {
    val testInput = """\r(url.jpg)"""
    val expectedOutput = """<img alt="url" src="url.jpg"/>"""
    val htmlOutput: String = imageTag.convert(testInput)
    htmlOutput should be(expectedOutput)
  }

  "linkTag convert method" should "successfully convert the native markdown link tag to the html link tag" in {
    val testInput = """\b[some text](url)"""
    val expectedOutput = """<a href="url">some text</a>"""
    val htmlOutput: String = linkTag.convert(testInput)
    htmlOutput should be(expectedOutput)
  }

}
