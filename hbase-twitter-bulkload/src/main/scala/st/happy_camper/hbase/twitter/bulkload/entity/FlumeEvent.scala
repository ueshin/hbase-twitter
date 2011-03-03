package st.happy_camper.hbase.twitter.bulkload.entity

import _root_.dispatch.json._
import _root_.sjson.json._
import _root_.sjson.json.JsonSerialization._

class FlumeEvent(
  val body: String,
  val timestamp: Long,
  val pri: String,
  val nanos: Long,
  val host: String,
  val fields: Map[String, String]
)

object FlumeEvent {

  private object FlumeEventProtocol extends DefaultProtocol {

    implicit object FlumeEventReads extends Reads[FlumeEvent] {
      def reads(json: JsValue) = json match {
        case JsObject(m) => new FlumeEvent(
          fromjson[String](m(JsString("body"))),
          fromjson[Long](m(JsString("timestamp"))),
          fromjson[String](m(JsString("pri"))),
          fromjson[Long](m(JsString("nanos"))),
          fromjson[String](m(JsString("host"))),
          m(JsString("fields")) match {
            case JsObject(m) => {
              m.foldLeft(Map.empty[String, String]) { case (m, (k, v)) => m + (fromjson[String](k) -> fromjson[String](v)) }
            }
            case _ => throw new RuntimeException("FlumeEvent expected")
          }
        )
        case _ => throw new RuntimeException("FlumeEvent expected")
      }
    }
  }

  import FlumeEventProtocol._

  def apply(json: JsValue) : FlumeEvent = {
    fromjson[FlumeEvent](json)
  }

  def unapply(json: JsValue) : Option[FlumeEvent] = {
    try {
      Some(FlumeEvent(json))
    }
    catch {
      case _ => None
    }
  }
}
