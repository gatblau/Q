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

import java.util.{Locale, ResourceBundle}

object Strings {
  private val bundle: ResourceBundle = ResourceBundle.getBundle("Strings", Locale.getDefault)

  private[q] def getString(key: String): String = {
    return bundle.getString(key)
  }

  private[q] val WAITING: String = "waiting"
  private[q] val CHECK_ENTITY_EXISTS_PASS: String = "checkEntityExists_pass"
  private[q] val CHECK_ENTITY_EXISTS_FAIL: String = "checkEntityExists_fail"
  private[q] val CHECK_ENTITY_EXISTS_FIELD_MISMATCH: String = "checkEntityExists_mismatched_fields"
  private[q] val ACTION_QUERY: String = "query"
  private[q] val INVOKE_PARAMS: String = "invoke_params"
  private[q] val INVOKE_NO_PARAMS: String = "invoke_no_params"
  private[q] val INVOKE_PASS_RETURN: String = "invoke_pass_return"
  private[q] val INVOKE_PASS_VOID: String = "invoke_pass_void"
  private[q] val INVOKE_FAIL: String = "invoke_fail"
  private[q] val INVOKE_FAIL_NO_METHOD: String = "invoke_fail_no_method"
  private[q] val INVOKE_FAIL_INVOCATION_TARGET: String = "invoke_fail_invocation_target"
  private[q] val INVOKE_FAIL_ILLEGAL_ACCESS: String = "invoke_fail_illegal_access"
  private[q] val DB_INIT: String = "db_init"
  private[q] val DB_INIT_COMPLETE: String = "db_init_complete"
  private[q] val FEATURE_LABEL: String = "feature_label"
  private[q] val SCENARIO_LABEL: String = "scenario_label"
  private[q] val STEP_LABEL: String = "step_label"
  private[q] val SET_DATA: String = "set_data"
  private[q] val SET_DATA_FROM_RECORD: String = "set_data_from_record"
  private[q] val SET_DATA_FROM_RECORD_PASS: String = "set_data_from_record_pass"
  private[q] val SET_DATA_FROM_RECORD_FAIL: String = "set_data_from_record_fail"
  private[q] val SET_DATA_PASS: String = "set_data_pass"
  private[q] val SET_DATA_FAIL: String = "set_data_fail"
  private[q] val LOAD_RECORD: String = "load_record"
  private[q] val LOAD_RECORD_PASS: String = "load_record_pass"
  private[q] val LOAD_RECORD_FAIL: String = "load_record_fail"
  private[q] val LOAD_RECORD_ONE_TABLE: String = "load_record_one_table"
  private[q] val NOT_FOUND_IN_CACHE: String = "not_found_in_cache"
  private[q] val ACTION_SET_CACHE : String = "action_set_cache"
  private[q] val ACTION_CACHE_SET : String = "action_cache_set"
  private[q] val ACTION_GET_CACHE : String = "action_get_cache"
  private[q] val ACTION_GET_CACHE_FAIL : String = "action_get_cache_fail"
  private[q] val ACTION_CACHE_GET : String = "action_cache_get"
}
