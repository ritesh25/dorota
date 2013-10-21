package models 

import play.api.libs.json.Json
import com.google.inject._
import java.util.concurrent.TimeUnit
import com.twitter.finagle.redis.util.StringToChannelBuffer
import com.twitter.util.{Duration, Await}
import common.{Dorota, RedisClientFactory, RedisStorageFactory}

class FeedProcessor @Inject() {

  val timeToLive = Option(Duration(1800000L, TimeUnit.MILLISECONDS))

  @transient lazy val redisClient =
    Dorota.injector.getInstance(classOf[RedisClientFactory]).createClient

  @transient lazy val store =
    Dorota.injector.getInstance(classOf[RedisStorageFactory]).createStore(redisClient, timeToLive)

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
      Await.result(redisClient.get(StringToChannelBuffer(source))) match {
        case Some(v) => {
          play.Logger.info(s"Returning result from cache for source $source")
          new String(v.array)
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
