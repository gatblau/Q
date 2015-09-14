package features.catalogue

import javax.inject.{Inject, Singleton}

import cucumber.api.{PendingException, CucumberOptions}
import cucumber.api.java.en.And
import cucumber.api.junit.Cucumber
import features.Common
import org.gatblau.q._
import org.junit.runner.RunWith

@RunWith(classOf[Cucumber])
@CucumberOptions(plugin = Array("org.gatblau.q.Logger"))
class CatalogueRunner

@Singleton
class CatalogueSteps {

  @Inject var common  : Common = _
  @Inject var drive   : Driver = _

  @And("^the information for a feature catalogue is known$")
  def the_information_for_a_feature_catalogue_is_known(): Unit = {
    // http://spray.io/blog/2012-12-13-the-magnet-pattern/
    throw new PendingException
  }

  @And("^the creation of the catalogue is requested$")
  def the_creation_of_the_catalogue_is_requested(): Unit = {
    throw new PendingException
  }

  @And("^the catalogue information is recorded$")
  def the_catalogue_information_is_recorded(): Unit = {
    throw new PendingException
  }
}
