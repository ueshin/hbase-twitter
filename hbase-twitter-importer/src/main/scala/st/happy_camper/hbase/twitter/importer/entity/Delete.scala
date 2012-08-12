/*
 * Copyright 2010-2012 Happy-Camper Street.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package st.happy_camper.hbase.twitter
package importer
package entity

import org.codehaus.jackson.JsonNode

import st.happy_camper.hbase.twitter.entity.Delete

/**
 * @author ueshin
 *
 */
object Delete {

  def apply(json: JsonNode): Delete = {
    new Delete(
      json.path("delete").path("status").path("id").getLongValue,
      json.path("delete").path("status").path("user_id").getLongValue)
  }

  def unapply(json: JsonNode): Option[Delete] = {
    try {
      Option(Delete(json))
    } catch {
      case _ => None
    }
  }

}
