package features.catalogue

import javax.inject.{Inject, Singleton}

import cucumber.api.java.en.And
import org.gatblau.q.{Client, Q}

@Singleton
class FindAllCataloguesSteps extends Q {

  private var client: Client = _

  @Inject
  def init(client: Client): Unit = {
    this.client = track[Client](client)
  }

  @And("^there are a few feature catalogues in the system$")
  def there_are_a_few_feature_catalogues_in_the_system() : Unit = {

  }

  @And("^the list of available catalogues is requested$")
  def the_list_of_available_catalogues_is_requested() : Unit = {
    val calatogues = client.getCatalogues
  }

  @And("^the list of available catalogues is produced$")
  def the_list_of_available_catalogues_is_produced() : Unit = {

  }

  @And("^the list contains all recorded catalogues in the system$")
  def the_list_contains_all_recorded_catalogues_in_the_system() : Unit = {

  }
}
