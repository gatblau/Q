package features

import com.google.inject.{Provides, Guice, Injector, Stage}
import cucumber.api.guice.CucumberModules
import cucumber.runtime.java.guice.InjectorSource
import net.codingwell.scalaguice.ScalaModule
import org.gatblau.q.{Client, ClientImpl}

class ClientInjectorSource extends InjectorSource {
  override def getInjector : Injector = {
    Guice.createInjector(Stage.PRODUCTION, CucumberModules.SCENARIO, new MainModule())
  }

  class MainModule extends ScalaModule {
    val uri = "http://localhost:3000"

    override def configure(): Unit = {
    }

    @Provides def getClient : Client = new ClientImpl(uri)
  }
}
