package controllers

import play.api._
import play.api.mvc._
import models._
import com.google.inject._
import java.util.Random
import common.Dorota

@Singleton
class Feed @Inject() (retriever: FeedRetriever, processor: FeedProcessor) extends Controller {
  def randomFeedKey: String = {
    val rand = new Random(System.currentTimeMillis());
    val feedKeys = Feeds.current.keys.toList
    val randomIndex = rand.nextInt(feedKeys.length);
    feedKeys(randomIndex);
  }

  def json = Action {
    val source = randomFeedKey
    val randomFeed = Feeds.current(source)
    val feed = processor.headlines(source, randomFeed, retriever)
    Ok(feed)
  }
}
