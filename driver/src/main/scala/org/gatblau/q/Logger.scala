/*
 * Copyright 2015 gatblau.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.gatblau.q

import java.util

import gherkin.formatter.Formatter
import gherkin.formatter.model._
import FeatureInfo._

/**
 * A gherkin formatter that captures the executing feature name, description and scenario name
 * and make them available to the {@link Qdriver}.
 * <p><b>Note:</b> if not used, the Feature and Scenario names will not be logged by the {@link Qdriver}.</p>
 * <p><b>Cucumber-JVM example:</b></p>
 * <pre>
        import cucumber.api.CucumberOptions
        import cucumber.api.junit.Cucumber
        import org.junit.runner.RunWith

       {@literal @}RunWith(Cucumber.class)
       {@literal @}CucumberOptions(plugin = Array("org.gatblau.q.Logger"))
       class MyFeatures {
       }
 * </pre>
 */
class Logger extends Formatter {
  override def syntaxError(state: String, event: String, legalEvents: util.List[String], uri: String, line: Integer): Unit = {
  }

  override def endOfScenarioLifeCycle(scenario: Scenario): Unit = {
  }

  override def scenario(scenario: Scenario): Unit = {
  }

  override def startOfScenarioLifeCycle(scenario: Scenario): Unit = {
    setCurrentScenarioName(scenario.getName)
  }

  override def uri(uri: String): Unit = {
  }

  override def done(): Unit = {
  }

  override def scenarioOutline(scenarioOutline: ScenarioOutline): Unit = {
  }

  override def background(background: Background): Unit = {
  }

  override def feature(feature: Feature): Unit = {
    setCurrentFeatureName(feature.getName)
    setCurrentFeatureDesc(feature.getDescription)
  }

  override def close(): Unit = {
  }

  override def step(step: Step): Unit = {
  }

  override def examples(examples: Examples): Unit = {
  }

  override def eof(): Unit = {
  }
}
