package st.happy_camper.hbase.twitter.bulkload.entity

import _root_.dispatch.json._
import _root_.sjson.json._
import _root_.sjson.json.JsonSerialization._

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

  private object PlaceProtocol extends DefaultProtocol {

    implicit object PlaceReads extends Reads[Place] {
      def reads(json: JsValue) = json match {
        case JsObject(m) => new Place(
          fromjson[String](m(JsString("id"))),
          fromjson[String](m(JsString("name"))),
          fromjson[String](m(JsString("full_name"))),
          fromjson[String](m(JsString("place_type"))),
          fromjson[String](m(JsString("url"))),
          m(JsString("bounding_box")) match {
            case JsObject(m) => m(JsString("coordinates")).toString
            case _ => throw new RuntimeException("Place expected")
          },
          m(JsString("bounding_box")) match {
            case JsObject(m) => fromjson[String](m(JsString("type")))
            case _ => throw new RuntimeException("Place expected")
          },
          fromjson[String](m(JsString("country"))),
          fromjson[String](m(JsString("country_code")))
        )
        case _ => throw new RuntimeException("Place expected")
      }
    }
  }

  import PlaceProtocol._

  def apply(json: JsValue) : Place = {
    fromjson[Place](json)
  }

  def unapply(json: JsValue) : Option[Place] = {
    try {
      Some(Place(json))
    }
    catch {
      case e => None
    }
  }
}
