package models

import org.specs2.mutable._

import scala.io.Source

class FeedParserSpec extends Specification {
  val source = Source.fromFile("test/feed.xml", "UTF-8")
  val feedText = source.mkString
  val parser = new FeedParser(feedText)

  "FeedParser" should {
    "Return a sequence of titles in a feed" in {
      parser.titles must contain("Obama to Tout Economy While Marking Lehman Fall - ABC News")
    }

    "Not return the channel title" in {
      parser.titles must not contain("Top Stories - Google News")
    }
  }
}
