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

import st.happy_camper.hbase.twitter.entity.Place

/**
 * @author ueshin
 *
 */
object Place {

  def apply(json: JsonNode): Place = {
    new Place(
      json.path("id").getTextValue,
      json.path("name").getTextValue,
      json.path("full_name").getTextValue,
      json.path("url").getTextValue,
      json.path("place_type").getTextValue,
      json.path("country").getTextValue,
      json.path("country_code").getTextValue,
      if (json.path("bounding_box").isNull) None else Option(json.path("bounding_box").path("coordinates").toString),
      if (json.path("bounding_box").isNull) None else Option(json.path("bounding_box").path("type").getTextValue),
      json.path("attributes").toString)
  }

  def unapply(json: JsonNode): Option[Place] = {
    try {
      Option(Place(json))
    } catch {
      case e => None
    }
  }
}
