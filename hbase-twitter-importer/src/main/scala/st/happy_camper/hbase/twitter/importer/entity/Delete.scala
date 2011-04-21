package st.happy_camper.hbase.twitter
package importer
package entity

import _root_.st.happy_camper.hbase.twitter.entity._

import _root_.org.codehaus.jackson.JsonNode

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
