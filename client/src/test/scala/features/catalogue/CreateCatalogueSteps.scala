package features.catalogue

import javax.inject.{Inject, Singleton}

import cucumber.api.java.en.And
import features.Vars
import org.gatblau.q.model.Catalogue
import org.gatblau.q.util.M
import org.gatblau.q.{StringUtils, Client, Q}

import StringUtils._

@Singleton
class CreateCatalogueSteps extends Q {
  import Vars._

  private var api: Client = _

  def catalogue = implicitly[M[Catalogue]] from map.of[Catalogue](REF_CATALOGUE)

  @Inject
  def init(client: Client): Unit = {
    this.api = manage[Client](client)
    db -> Q_DATA_SOURCE jdbc
  }

  @And("^the information for a feature catalogue is known$")
  def the_information_for_a_feature_catalogue_is_known(): Unit = {
    load -> REF_CATALOGUE
  }

  @And("^the creation of the catalogue is requested$")
  def the_creation_of_the_catalogue_is_requested(): Unit = {
    keep -> REF_CATALOGUE_ID as ( api createCatalogue catalogue )
  }

  @And("^the catalogue information is recorded$")
  def the_catalogue_information_is_recorded(): Unit = {
    compare -> REF_CATALOGUE to (QUERY_GET_CATALOGUE_BY_ID % REF_CATALOGUE_ID) in Q_DATA_SOURCE
  }
}
