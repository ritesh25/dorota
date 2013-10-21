package models 

import scala.io.Source

//TODO: needs exception handling
trait Retriever {
  def getFeed(feedUrl: String) : String
}

class FeedRetriever extends Retriever {
  def getFeed(feedUrl: String): String = {
    Source.fromURL(feedUrl).mkString
  }
}

class FakeRetriever extends Retriever {
  def getFeed(feedUrl: String): String = {
    Source.fromFile("test/feed.xml", "UTF-8").mkString
  }
}
