package st.happy_camper.hbase.twitter.bulkload.entity

import _root_.org.codehaus.jackson.JsonNode
import _root_.org.codehaus.jackson.map.ObjectMapper

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

  def apply(json: String) : Place = {
    null
  }

  def unapply(json: String) : Option[Place] = {
    try {
      Option(Place(json))
    }
    catch {
      case e => None
    }
  }
}
