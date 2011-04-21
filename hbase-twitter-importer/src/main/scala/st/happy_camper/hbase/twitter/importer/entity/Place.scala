package st.happy_camper.hbase.twitter
package importer
package entity

import _root_.st.happy_camper.hbase.twitter.entity._

import _root_.org.codehaus.jackson.JsonNode

object Place {

  def apply(json: JsonNode) : Place = {
    new Place(
      json.path("id").getTextValue,
      json.path("name").getTextValue,
      json.path("full_name").getTextValue,
      json.path("url").getTextValue,
      json.path("place_type").getTextValue,
      json.path("country").getTextValue,
      json.path("country_code").getTextValue,
      if(json.path("bounding_box").isNull) None else Option(json.path("bounding_box").path("coordinates").toString),
      if(json.path("bounding_box").isNull) None else Option(json.path("bounding_box").path("type").getTextValue),
      json.path("attributes").toString
    )
  }

  def unapply(json: JsonNode) : Option[Place] = {
    try {
      Option(Place(json))
    }
    catch {
      case e => None
    }
  }
}
