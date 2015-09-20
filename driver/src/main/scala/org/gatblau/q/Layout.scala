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

import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.core.{CoreConstants, LayoutBase}
import ch.qos.logback.core.pattern.color.ANSIConstants._
import org.gatblau.q.util.Logging

class Layout extends LayoutBase[ILoggingEvent] {
  private val FEATURE_TAG: String = "[feature]"
  private val SCENARIO_TAG: String = "[scenario]"
  private val STEP_TAG: String = "[step]"
  private val ACTION_TAG: String = "[action]"
  private val WARN_TAG: String = "[warn]"
  private val FAIL_TAG: String = "[fail]"
  private val PASS_TAG: String = "[pass]"
  private val WAIT_TAG: String = "[wait]"
  private val BLANK_SPACE: String = " "
  private var logOther: Boolean = false
  private var syntaxColor: Boolean = false
  private var tabChar: String = "+"
  private var formatHeaders: Boolean = false
  private var loggerNameFilter: String = classOf[Logging].getCanonicalName
  private var FEATURE_COLOR: String = "1;35"
  private var SCENARIO_COLOR: String = "1;36"
  private var STEP_COLOR: String = YELLOW_FG
  private var ACTION_COLOR: String = CYAN_FG
  private var WARN_COLOR: String = MAGENTA_FG
  private var FAIL_COLOR: String = RED_FG
  private var PASS_COLOR: String = GREEN_FG
  private var WAIT_COLOR: String = BLUE_FG
  private var DEFAULT_COLOR: String = "0;" + DEFAULT_FG

  /**
   * When true, logs any other events outside of the ones raised within the scope of the
   * {@link org.gatblau.xavier.TestManagerLayout#setLoggerNameFilter(String)} method.
   * @param logOther 'true' to log other events.
   *                 The default value is 'false'.
   */
  def setLogOther (logOther: Boolean) {
    this.logOther = logOther
  }

  /**
   * When 'true', adds ANSI color codes to the output stream producing syntax coloring
   * when using the shell console.
   * <b>Note:</b> for syntax coloring in Windows to work additional configuration is required, check
   * the links below.
   * @see ch.qos.logback.core.ConsoleAppender#setWithJansi(boolean)
   * @see <a href="http://logback.qos.ch/manual/appenders.html"></a>
   * @param syntaxColor 'true' to output ANSI color codes, otherwise false.
   *                    The default value is 'false'.
   */
  def setSyntaxColor (syntaxColor: Boolean) {
    this.syntaxColor = syntaxColor
  }

  /**
   * Sets the character used to fill tab spaces of the indented output.
   * @param tabChar the character or string used to fill indented spaces.
   *                The default value is '+'.
   */
  def setTabCharacter (tabChar: String) {
    this.tabChar = tabChar
  }

  /**
   * When 'true', the format of the feature, scenario and step names that is logged to the output stream
   * is enhanced for readability: underscores are replaced with spaces and the first character is capitalised.
   * @param formatHeaders true, if the method name is formatted.
   *                      The default value is 'false'.
   */
  def setFormatHeaders (formatHeaders: Boolean) {
    this.formatHeaders = formatHeaders
  }

  /**
   * Selects the name of the logger that will be included in the output stream.
   * @param loggerNameFilter the canonical name or partial name of the logger that logs test event.
   *                         The default value is 'TestManager.class.getCanonicalName()'.
   *                         Its value should not be modified unless a custom test manager is used and its
   *                         initial path differs from the default one.
   */
  def setLoggerNameFilter (loggerNameFilter: String) {
    this.loggerNameFilter = loggerNameFilter
  }

  /**
   * Sets the color used to render the name of the test method in the console output.
   * @param stepColor the ANSI code for the color without initial '[' and final 'm' escape characters.
   *                  The default color is yellow (i.e. '33').
   */
  def setStepColor (stepColor: String) {
    this.STEP_COLOR = stepColor
  }

  /**
   * Sets the color used to log actions of a test method to the console output.
   * @param actionColor the ANSI code for the color without initial '[' and final 'm' escape characters.
   *                    The default color is cyan (i.e. '36').
   */
  def setActionColor (actionColor: String) {
    this.ACTION_COLOR = actionColor
  }

  /**
   * Sets the color used to log warnings to the console output.
   * @param warnColor the ANSI code for the color without initial '[' and final 'm' escape characters.
   *                  The default color is magenta (i.e. '35').
   */
  def setWarnColor (warnColor: String) {
    this.WARN_COLOR = warnColor
  }

  /**
   * Sets the color used to log failed actions of the test method to the console output.
   * @param failColor the ANSI code for the color without initial '[' and final 'm' escape characters.
   *                  The default color is red (i.e. '31').
   */
  def setFailColor (failColor: String) {
    this.FAIL_COLOR = failColor
  }

  /**
   * Sets the color used to log successful actions of the test method to the console output.
   * @param passColor the ANSI code for the color without initial '[' and final 'm' escape characters.
   *                  The default color is green (i.e. '32').
   */
  def setPassColor (passColor: String) {
    this.PASS_COLOR = passColor
  }

  /**
   * Sets the color used to log wait messages of the test method to the console output.
   * @param waitColor the ANSI code for the color without initial '[' and final 'm' escape characters.
   *                  The default color is blue (i.e. '34).
   */
  def setWaitColor (waitColor: String) {
    this.WAIT_COLOR = waitColor
  }

  /**
   * Sets the default color of the console output.
   * @param defaultColor the ANSI code for the color without initial '[' and final 'm' escape characters.
   *                     The default color is white (i.e. '0;39').
   */
  def setDefaultColor (defaultColor: String) {
    this.DEFAULT_COLOR = defaultColor
  }

  def doLayout (event: ILoggingEvent): String = {
    var result: String = null
    if (event.getLoggerName.startsWith(loggerNameFilter) ) {
      result = logTestManager(event)
    }
    else if (logOther) {
      result = logOther(event)
    }
    else {
      result = ""
    }
    return result
  }

  private def logOther (event: ILoggingEvent): String = {
    val out: StringBuffer = new StringBuffer(128)
    out.append(event.getLevel)
    out.append(BLANK_SPACE)
    out.append(event.getLoggerName)
    out.append(" : ")
    out.append(event.getFormattedMessage)
    out.append(CoreConstants.LINE_SEPARATOR)
    return out.toString
  }

  private def logTestManager (event: ILoggingEvent): String = {
    val out: StringBuffer = new StringBuffer(128)
    var msg: String = event.getFormattedMessage

    if (msg.startsWith(FEATURE_TAG)) {
      out.append (CoreConstants.LINE_SEPARATOR)
      msg = msg.substring (FEATURE_TAG.length)
      if (formatHeaders) {
        msg = formatStep (msg)
      }
      out.append(formatStr(FEATURE_COLOR, msg, syntaxColor))
    }
    else if (msg.startsWith(SCENARIO_TAG)) {
      out.append (CoreConstants.LINE_SEPARATOR)
      msg = msg.substring (SCENARIO_TAG.length)
      if (formatHeaders) {
        msg = formatStep(msg)
      }
      out.append (formatStr(SCENARIO_COLOR, msg, syntaxColor))
    }
    else if (msg.startsWith (STEP_TAG)) {
      out.append(CoreConstants.LINE_SEPARATOR)
      msg = msg.substring(STEP_TAG.length)
      if (formatHeaders) {
        msg = formatStep(msg)
      }
      out.append(formatStr(STEP_COLOR, msg, syntaxColor))
    }
    else if (event.getFormattedMessage.startsWith(WAIT_TAG)) {
      msg = BLANK_SPACE + msg.substring (WAIT_TAG.length)
      out.append (formatStr (WAIT_COLOR, msg, syntaxColor) )
    }
    else if (event.getFormattedMessage.startsWith(ACTION_TAG)) {
      msg = msg.substring (ACTION_TAG.length)
      msg = tabChar + BLANK_SPACE + msg
      out.append(formatStr(ACTION_COLOR, msg, syntaxColor))
    }
    else if (event.getFormattedMessage.startsWith(WARN_TAG)) {
      msg = msg.substring(WARN_TAG.length)
      msg = tabChar + tabChar + BLANK_SPACE + msg
      out.append (formatStr(WARN_COLOR, msg, syntaxColor) )
    }
    else if (event.getFormattedMessage.startsWith(FAIL_TAG)) {
      msg = msg.substring (FAIL_TAG.length)
      msg = tabChar + tabChar + tabChar + BLANK_SPACE + msg
      out.append(formatStr(FAIL_COLOR, msg, syntaxColor))
    }
    else if (event.getFormattedMessage.startsWith(PASS_TAG)) {
      msg = msg.substring (PASS_TAG.length)
      msg = tabChar + tabChar + tabChar + BLANK_SPACE + msg
      out.append(formatStr(PASS_COLOR, msg, syntaxColor))
    }
    out.append(CoreConstants.LINE_SEPARATOR)
    out.toString
  }

  private def formatStr (colourCode: String, inputString: String, applyColour: Boolean): String = {
    if (applyColour) {
      val sb: StringBuilder = new StringBuilder
      sb.append (ESC_START).append (colourCode).append (ESC_END)
      sb.append (inputString)
      sb.append (ESC_START).append (DEFAULT_COLOR).append (ESC_END)
      sb.toString
    }
    else {
      inputString
    }
  }

  private def formatStep(step: String): String = {
    step.replace('_', ' ').substring(0, 1).toUpperCase.concat(step.substring(1))
  }
}
