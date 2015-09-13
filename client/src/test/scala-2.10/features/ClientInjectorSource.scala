package features

import com.google.inject.{Guice, Stage, Injector}
import cucumber.api.guice.CucumberModules
import cucumber.runtime.java.guice.InjectorSource
import net.codingwell.scalaguice.ScalaModule

class ClientInjectorSource extends InjectorSource {
  override def getInjector : Injector = {
    Guice.createInjector(Stage.PRODUCTION, CucumberModules.SCENARIO, new AskModule());
  }

  class AskModule extends ScalaModule {
    override def configure(): Unit = {
//      bind[Driver].to[QDriver]
    }
  }
}
