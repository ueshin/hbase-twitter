package st.happy_camper.hbase.twitter.importer.entity

import _root_.scala.collection.JavaConversions._

import _root_.org.codehaus.jackson.JsonNode

class FlumeEvent(
  val body: String,
  val timestamp: Long,
  val pri: String,
  val nanos: Long,
  val host: String,
  val fields: Map[String, String]
)

object FlumeEvent {

  def apply(json: JsonNode) : FlumeEvent = {
    new FlumeEvent(
      json.path("body").getTextValue,
      json.path("timestamp").getLongValue,
      json.path("pri").getTextValue,
      json.path("nanos").getLongValue,
      json.path("host").getTextValue,
      json.path("fields").getFieldNames.map {
        fieldName => {
          (fieldName -> json.path("fields").path(fieldName).getTextValue)
        }
      }.toMap
    )
  }

  def unapply(json: JsonNode) : Option[FlumeEvent] = {
    try {
      Option(FlumeEvent(json))
    }
    catch {
      case _ => None
    }
  }
}
