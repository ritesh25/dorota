package models
import org.specs2.mutable._
import org.specs2.mock._
import play.api.libs.json.Json
import com.google.inject._
import com.tzavellas.sse.guice.ScalaModule
import play.api.test._
import play.api.test.Helpers._
import com.twitter.finagle.redis.Client
import com.twitter.finagle.redis.util.StringToChannelBuffer
import com.twitter.util.{Await, Future}
import common.{Dorota, RedisClientFactory, StorageFactory, RedisStorageFactory}
import java.util.concurrent.TimeUnit
import com.twitter.util.Duration
import com.twitter.storehaus.redis.RedisStringStore
import com.twitter.storehaus.algebra.MergeableStore
import org.jboss.netty.buffer.ChannelBuffer

class FeedProcessorSpec extends Specification with Mockito {
  sequential

  val fakeHeadlines = List(
      "At least 13 dead in Navy Yard shooting; possible suspect at large - Washington Post",
      "Costa Concordia salvage nears final phase - Reuters",
      "A Win for Assad? Backers Are Split - Wall Street Journal",
      "Man arrested at White House after setting off firecracker - Politico",
      "Police shooting of unarmed man spurs civil rights call - Chicago Tribune",
      "With Runoff Unlikely, Thompson Bows Out Of Mayoral Race To Unify Party - NY1",
      "Downed Syrian Helicopter Highlights Dangers of the Volatile Turkey-Syria Border - TIME",
      "JPMorgan may pay $750M fine for 'whale' losses - USA TODAY",
      "Yellen Is Top Fed Hopeful, Yet Again - Wall Street Journal",
      "Obama to Tout Economy While Marking Lehman Fall - ABC News"
  )

  val fakeRetriever = mock[Retriever]
  val fakeStoreFactory = mock[StorageFactory]
  val fakeStore = mock[MergeableStore[ChannelBuffer, String]]

  fakeStoreFactory.createStore returns fakeStore

  class TestModule extends ScalaModule {
    def configure = {
      bind[Retriever].toInstance(fakeRetriever)
      bind[StorageFactory].toInstance(fakeStoreFactory)
    }
  }
  Dorota.injector = Guice.createInjector(new TestModule)

  val expectedJson = Json.toJson(Json.obj("source" -> "Google News", "headlines" -> fakeHeadlines)).toString

  "A FeedProcessor" should {
    "be able to return finished JSON" in {

      val feedProcessor = new FeedProcessor
      val key = StringToChannelBuffer("Google News")

      fakeStore.get(key) returns Future.value { Some(expectedJson) }
      val result = feedProcessor.headlines("Google News", "http://example.com", fakeRetriever)

      result mustEqual expectedJson
    }

    "read from Redis cache" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        there was one(fakeStore).get(StringToChannelBuffer("Google News"))
      }
    }
  }
}
