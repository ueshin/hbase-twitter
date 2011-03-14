package st.happy_camper.hbase.twitter.bulkload.entity

import _root_.org.codehaus.jackson.JsonNode

class Place(
  val id: String,
  val name: String,
  val fullName: String,
  val placeType: String,
  val url: String,
  val boundingBox: String,
  val boundingBoxType: String,
  val country: String,
  val countryCode: String
)

object Place {

  def apply(json: JsonNode) : Place = {
    new Place(
      json.path("id").getTextValue,
      json.path("name").getTextValue,
      json.path("full_name").getTextValue,
      json.path("place_type").getTextValue,
      json.path("url").getTextValue,
      json.path("bounding_box").path("coordinates").toString,
      json.path("bounding_box").path("type").getTextValue,
      json.path("country").getTextValue,
      json.path("country_code").getTextValue
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
