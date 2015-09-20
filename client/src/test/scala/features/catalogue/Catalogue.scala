package features.catalogue

import javax.inject.{Inject, Singleton}

import cucumber.api.java.en.And
import cucumber.api.junit.Cucumber
import cucumber.api.{CucumberOptions, PendingException}
import features.Vars
import org.gatblau.q.{Client, _}
import org.gatblau.q.aspect.Tracking
import org.gatblau.q.model.Catalogue
import org.junit.runner.RunWith

@RunWith(classOf[Cucumber])
@CucumberOptions(plugin = Array("org.gatblau.q.Logger"))
class CatalogueRunner

@Singleton
class CatalogueSteps extends Q with Tracking {
  import Vars._

  private var client: Client = _

  @Inject
  def init(client: Client): Unit = {
    this.client = track[Client](client)
  }

  @And("^the information for a feature catalogue is known$")
  def the_information_for_a_feature_catalogue_is_known(): Unit = {
    cache.loadFromFile(DATA_FILE_CATALOGUE_REF)
  }

  @And("^the creation of the catalogue is requested$")
  def the_creation_of_the_catalogue_is_requested(): Unit = {
    val c = Catalogue(0, Option("gatblau"), Option(""), "", Option(""), null, 0)
    client.createCatalogue(c)
  }

  @And("^the catalogue information is recorded$")
  def the_catalogue_information_is_recorded(): Unit = {
    throw new PendingException
  }
}
