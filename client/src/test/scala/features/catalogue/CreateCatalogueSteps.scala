package features.catalogue

import javax.inject.{Inject, Singleton}

import cucumber.api.java.en.And
import features.Vars
import org.gatblau.q.aspect.Tracking
import org.gatblau.q.model.Catalogue
import org.gatblau.q.util.M
import org.gatblau.q.{Client, Q}

@Singleton
class CreateCatalogueSteps extends Q with Tracking {
  import Vars._

  private var api: Client = _

  def catalogue = implicitly[M[Catalogue]] from map.of[Catalogue](CatalogueReference)

  @Inject
  def init(client: Client): Unit = {
    this.api = track[Client](client)
  }

  @And("^the information for a feature catalogue is known$")
  def the_information_for_a_feature_catalogue_is_known(): Unit = {
    load -> CatalogueReference
  }

  @And("^the creation of the catalogue is requested$")
  def the_creation_of_the_catalogue_is_requested(): Unit = {
    api createCatalogue catalogue
  }

  @And("^the catalogue information is recorded$")
  def the_catalogue_information_is_recorded(): Unit = {
  }
}
