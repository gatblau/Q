package features

import com.google.inject.{Guice, Stage, Injector}
import cucumber.api.guice.CucumberModules
import cucumber.runtime.java.guice.InjectorSource
import net.codingwell.scalaguice.ScalaModule
import org.gatblau.q.{Client, ClientImpl}
import org.gatblau.q.aspect.Registry

class ClientInjectorSource extends InjectorSource {
  override def getInjector : Injector = {
    Guice.createInjector(Stage.PRODUCTION, CucumberModules.SCENARIO, new MainModule());
  }

  class MainModule extends ScalaModule {
    override def configure(): Unit = {
      bind[Client].to[ClientImpl]
    }
  }
}
