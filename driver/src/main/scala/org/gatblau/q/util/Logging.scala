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

package org.gatblau.q.util

import org.gatblau.q.FeatureInfo
import org.slf4j.{LoggerFactory, Logger}

trait Logging {
  private var currentStep: String = ""
  private var currentScenario: String = ""
  private var currentFeature: String = ""

  import Strings._

  private var logger: Logger = LoggerFactory.getLogger(classOf[Logging])

  private[q] def logStep {
    logFeature
    logScenario
    val step: String = getTestMethodName
    val stepLabel: String = getString(STEP_LABEL)
    if (!step.equals(currentStep)) {
      val len = step.length + stepLabel.length + 1
      logger.info("[step]{} {}\n{}", stepLabel, step, "-" * len)
      currentStep = step
    }
  }

  private[q] def logScenario {
    val scenario: String = FeatureInfo.getCurrentScenarioName
    val scenarioLabel: String = getString(SCENARIO_LABEL)
    if (scenario != null) {
      if (!(scenario == currentScenario)) {
        logger.info(s"[scenario]$scenarioLabel $scenario")
        currentScenario = scenario
      }
    }
  }

  private[q] def logFeature {
    val feature: String = FeatureInfo.getCurrentFeatureName
    val featureLabel: String = getString(FEATURE_LABEL)
    if (feature != null) {
      if (!(feature == currentFeature)) {
        logger.info("[feature]{} {}\n{}", featureLabel, feature, FeatureInfo.getCurrentFeatureDesc)
        currentFeature = feature
      }
    }
  }

  private[q] def logAction(action: String) {
    logger.info("[action] {}", action)
  }

  private[q] def logFail(ex: Exception, message: String) {
    logger.info(String.format("[fail]%s", message))
    Thread.sleep(100)
    throw new RuntimeException(message, ex)
  }

  private[q] def logPass(message: String) {
    logger.info("[pass]{}\n", message)
  }

  private def getTestMethodName : String = {
    var methodName = ""
    val stack: Array[StackTraceElement] = Thread.currentThread.getStackTrace.reverse
    val el = stack.find(i => i.getClassName.endsWith("Steps"))
    match {
      case Some(el) => methodName = el.getMethodName.replace('_', ' ').capitalize
      case None => throw new RuntimeException("Test steps must be placed in a class which name ends with Steps.")
    }
    methodName
  }
}
