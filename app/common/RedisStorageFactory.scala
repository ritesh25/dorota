package common

import com.twitter.storehaus.redis.RedisStringStore
import com.twitter.finagle.redis.Client
import com.twitter.util.Duration
import com.google.inject._

trait StorageFactory {
  def createStore(client: Client, ttl: Option[Duration]): RedisStringStore
}

class RedisStorageFactory extends StorageFactory {
  override def createStore(client: Client, ttl: Option[Duration]): RedisStringStore = {
    RedisStringStore(client, ttl)
  }
}
