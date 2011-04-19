package st.happy_camper.hbase.twitter.entity

import _root_.org.codehaus.jackson.JsonNode

class Url(
  val url: String,
  val expandedUrl: Option[String],
  val indices: String
)

object Url {

  def apply(json: JsonNode) : Url = {
    new Url(
      json.path("url").getTextValue,
      Option(json.path("expanded_url").getTextValue),
      json.path("indices").toString
    )
  }

  def unapply(json: JsonNode) : Option[Url] = {
    try {
      Option(Url(json))
    }
    catch {
      case _ => None
    }
  }
}
