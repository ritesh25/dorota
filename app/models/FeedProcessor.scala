package models 

import play.api.libs.json.Json
import com.google.inject._
import java.util.concurrent.TimeUnit
import com.twitter.finagle.redis.util.StringToChannelBuffer
import com.twitter.util.{Duration, Await}
import common.{Dorota, StorageFactory}

class FeedProcessor @Inject() {
  @transient lazy val store =
    Dorota.injector.getInstance(classOf[StorageFactory]).createStore

  def headlines(source: String, feedUrl: String, retriever: Retriever) : String = {

    def feedTitles: Seq[String] = {
      val feedText = retriever.getFeed(feedUrl)

      feedText match {
        case s: String => {
          new FeedParser(feedText).titles
        }
        case _ => {
          play.Logger.info(s"$source returned nothing")
          List("Not available")
        }
      }
    }

    val processedFeed = {
      Await.result(store.get(StringToChannelBuffer(source))) match {
        case Some(v) => {
          play.Logger.info(s"Returning result from cache for source $source")
          new String(v)
        }
        case None => {
          play.Logger.info(s"source '$source' previously unseen. Generating and setting in cache")

          val headlines = feedTitles
          val processed = Json.toJson(Json.obj("source" -> source, "headlines" -> headlines)).toString

          store.merge(StringToChannelBuffer(source), processed)
          processed
        }
      }
    }
    processedFeed
  }
}
