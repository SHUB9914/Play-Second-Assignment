package modules

import com.google.inject.AbstractModule
import play.api.{Configuration, Environment}
import services.{ConfService, ImpConfService}

class GuiceModule(environment: Environment, configuration: Configuration) extends AbstractModule {
  override def configure() = {
    bind(classOf[ConfService]).to(classOf[ImpConfService])
  }
}