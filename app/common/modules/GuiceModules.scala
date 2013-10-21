package common.modules

import com.tzavellas.sse.guice.ScalaModule
import models._
import common.{RedisClientFactory, RedisStorageFactory}
import com.twitter.finagle.redis.Client

class ProdModule extends ScalaModule {
  def configure() {
    bind[Retriever].to[FeedRetriever]

    val redisClient = new RedisClientFactory
    bind[RedisClientFactory].toInstance(redisClient)

    val redisStorageFactory = new RedisStorageFactory
    bind[RedisStorageFactory].toInstance(redisStorageFactory)

    val processor = new FeedProcessor
    bind[FeedProcessor].toInstance(processor)
  }
}

class DevModule extends ScalaModule {
  def configure() {
    bind[Retriever].to[FakeRetriever]

    val redisClient = new RedisClientFactory
    bind[RedisClientFactory].toInstance(redisClient)

    val redisStorageFactory = new RedisStorageFactory
    bind[RedisStorageFactory].toInstance(redisStorageFactory)
  }
}
