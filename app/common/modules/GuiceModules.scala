package common.modules

import com.tzavellas.sse.guice.ScalaModule
import models._
import common.{RedisClientFactory, StorageFactory, RedisStorageFactory}
import com.twitter.finagle.redis.Client

class ProdModule extends ScalaModule {
  def configure() {
    bind[Retriever].to[FeedRetriever]
    bind[RedisClientFactory].toInstance(new RedisClientFactory)
    bind[StorageFactory].toInstance(new RedisStorageFactory)
    bind[FeedProcessor].toInstance(new FeedProcessor)
  }
}

class DevModule extends ScalaModule {
  def configure() {
    bind[Retriever].to[FakeRetriever]
    bind[RedisClientFactory].toInstance(new RedisClientFactory)
    bind[StorageFactory].toInstance(new RedisStorageFactory)
  }
}
