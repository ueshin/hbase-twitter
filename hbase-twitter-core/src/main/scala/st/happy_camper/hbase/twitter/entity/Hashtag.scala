package st.happy_camper.hbase.twitter.entity

import _root_.org.codehaus.jackson.JsonNode

class Hashtag(
  val text: String,
  val indices: String
)

object Hashtag {

  def apply(json: JsonNode) : Hashtag = {
    new Hashtag(
      json.path("text").getTextValue,
      json.path("indices").toString
    )
  }

  def unapply(json: JsonNode) : Option[Hashtag] = {
    try {
      Option(Hashtag(json))
    }
    catch {
      case _ => None
    }
  }
}
