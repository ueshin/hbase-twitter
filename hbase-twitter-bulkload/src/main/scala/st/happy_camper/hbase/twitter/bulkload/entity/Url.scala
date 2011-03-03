package st.happy_camper.hbase.twitter.bulkload.entity

import _root_.dispatch.json._
import _root_.sjson.json._
import _root_.sjson.json.JsonSerialization._

class Url(
  val url: String,
  val expandedUrl: Option[String],
  val indices: String
)

object Url {

  private object UrlProtocol extends DefaultProtocol {

    implicit object UrlReads extends Reads[Url] {
      def reads(json: JsValue) = json match {
        case JsObject(m) => new Url(
          fromjson[String](m(JsString("url"))),
          try { Option(fromjson[String](m(JsString("expanded_url")))) } catch { case _ => None },
          m(JsString("indices")).toString
        )
        case _ => throw new RuntimeException("Url expected")
      }
    }
  }

  import UrlProtocol._

  def apply(json: JsValue) : Url = {
    fromjson[Url](json)
  }

  def unapply(json: JsValue) : Option[Url] = {
    try {
      Some(Url(json))
    }
    catch {
      case _ => None
    }
  }
}
