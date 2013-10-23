package common

import com.twitter.storehaus.algebra.MergeableStore
import com.twitter.storehaus.redis.RedisStringStore
import org.jboss.netty.buffer.ChannelBuffer
import com.twitter.finagle.redis.Client
import com.twitter.util.Duration
import com.google.inject._
import java.util.concurrent.TimeUnit

abstract trait StorageFactory {
  def createStore: MergeableStore[ChannelBuffer, String]
}

class RedisStorageFactory extends StorageFactory {
  override def createStore: MergeableStore[ChannelBuffer, String] = {

    val ttl = Some(Duration(180000L, TimeUnit.MILLISECONDS))
    val clientFactory = new RedisClientFactory
    val client = clientFactory.createClient

    RedisStringStore(client, ttl)
  }
}
