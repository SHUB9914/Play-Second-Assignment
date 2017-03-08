
package services
import javax.inject.Inject

import play.api.{Configuration, Environment}

import scala.collection.mutable.ListBuffer

trait ConfService {
  def getType():String
  val userdata: ListBuffer[String] = ListBuffer[String]()
}

class ImpConfService @Inject()(environment: Environment, configuration: Configuration) extends ConfService {

  def getType: String = {
   configuration.getString("user.type").fold ("not found")(identity)

  }

}
