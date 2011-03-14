package st.happy_camper.hbase.twitter.bulkload.entity

import _root_.org.codehaus.jackson.JsonNode
import _root_.org.codehaus.jackson.map.ObjectMapper

class FlumeEvent(
  val body: String,
  val timestamp: Long,
  val pri: String,
  val nanos: Long,
  val host: String,
  val fields: Map[String, String]
)

object FlumeEvent {

  def apply(json: String) : FlumeEvent = {
    Option(new ObjectMapper().readTree(json)) match {
      case Some(root) => new FlumeEvent(
        root.path("body").getTextValue,
        root.path("timestamp").getLongValue,
        root.path("pri").getTextValue,
        root.path("nanos").getBigIntegerValue.longValue,
        root.path("host").getTextValue,
        Map.empty[String, String]
      )
      case _ => throw new RuntimeException("FlumeEvent expected.")
    }
  }

  def unapply(json: String) : Option[FlumeEvent] = {
    try {
      Option(FlumeEvent(json))
    }
    catch {
      case _ => None
    }
  }
}
