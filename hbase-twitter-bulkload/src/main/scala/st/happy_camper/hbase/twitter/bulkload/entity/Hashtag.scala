package st.happy_camper.hbase.twitter.bulkload.entity

import _root_.dispatch.json._
import _root_.sjson.json._
import _root_.sjson.json.JsonSerialization._

class Hashtag(
  val text: String,
  val indices: String
)

object Hashtag {

  private object HashtagProtocol extends DefaultProtocol {

    implicit object HashtagReads extends Reads[Hashtag] {
      def reads(json: JsValue) = json match {
        case JsObject(m) => new Hashtag(
          fromjson[String](m(JsString("text"))),
          m(JsString("indices")).toString
        )
        case _ => throw new RuntimeException("Hashtag expected")
      }
    }
  }

  import HashtagProtocol._

  def apply(json: JsValue) : Hashtag = {
    fromjson[Hashtag](json)
  }

  def unapply(json: JsValue) : Option[Hashtag] = {
    try {
      Some(fromjson[Hashtag](json))
    }
    catch {
      case _ => None
    }
  }
}
