package common

import com.twitter.finagle.redis.Client

trait ClientFactory {
  def createClient: Client
}

class RedisClientFactory extends ClientFactory {
  override def createClient: Client  = {
    val host = sys.env.getOrElse("DOROTA_REDIS_HOST", "localhost:6379")
    Client(host)
  }
}
