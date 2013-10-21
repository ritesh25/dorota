package common

import play.api.Play
import play.api.Play.current
import com.google.inject._

import common.modules._

object Dorota {
  var injector = Guice.createInjector(new DevModule)
}
