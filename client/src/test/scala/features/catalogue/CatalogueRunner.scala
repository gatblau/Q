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
@CucumberOptions(
  plugin = Array("org.gatblau.q.Logger")
//  ,features = Array("../../client/src/main/resources/features/catalogue")
//  , glue = Array("features.catalogue")
)
class CatalogueRunner

