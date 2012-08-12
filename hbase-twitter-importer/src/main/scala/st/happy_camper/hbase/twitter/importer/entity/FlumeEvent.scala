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
package st.happy_camper.hbase.twitter.importer.entity

import scala.collection.JavaConversions.asScalaIterator

import org.codehaus.jackson.JsonNode

/**
 * @author ueshin
 *
 */
class FlumeEvent(
  val body: String,
  val timestamp: Long,
  val pri: String,
  val nanos: Long,
  val host: String,
  val fields: Map[String, String])

/**
 * @author ueshin
 *
 */
object FlumeEvent {

  def apply(json: JsonNode): FlumeEvent = {
    new FlumeEvent(
      json.path("body").getTextValue,
      json.path("timestamp").getLongValue,
      json.path("pri").getTextValue,
      json.path("nanos").getLongValue,
      json.path("host").getTextValue,
      json.path("fields").getFieldNames.map {
        fieldName =>
          {
            (fieldName -> json.path("fields").path(fieldName).getTextValue)
          }
      }.toMap)
  }

  def unapply(json: JsonNode): Option[FlumeEvent] = {
    try {
      Option(FlumeEvent(json))
    } catch {
      case _ => None
    }
  }
}
