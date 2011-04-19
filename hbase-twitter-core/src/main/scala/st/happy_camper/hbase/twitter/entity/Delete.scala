package st.happy_camper.hbase.twitter.entity

import _root_.scala.collection.JavaConversions._

import _root_.java.util.Date
import _root_.java.util.Locale
import _root_.java.text.SimpleDateFormat

import _root_.org.codehaus.jackson.JsonNode
import _root_.org.codehaus.jackson.map.ObjectMapper

class Delete(
  val id: Long,
  val userId: Long
)

object Delete {

  def apply(json: JsonNode) : Delete = {
    new Delete(
      json.path("delete").path("status").path("id").getLongValue,
      json.path("delete").path("status").path("user_id").getLongValue
    )
  }

  def unapply(json: JsonNode) : Option[Delete] = {
    try {
      Option(Delete(json))
    }
    catch {
      case _ => None
    }
  }

}
