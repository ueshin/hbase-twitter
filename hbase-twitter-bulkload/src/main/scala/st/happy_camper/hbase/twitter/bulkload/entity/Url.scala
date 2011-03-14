package st.happy_camper.hbase.twitter.bulkload.entity

import _root_.org.codehaus.jackson.JsonNode
import _root_.org.codehaus.jackson.map.ObjectMapper

class Url(
  val url: String,
  val expandedUrl: Option[String],
  val indices: String
)

object Url {

  def apply(json: String) : Url = {
    Option(new ObjectMapper().readTree(json)) match {
      case Some(root) => new Url(
        root.path("url").getTextValue,
        Option(root.path("expanded_url").getTextValue),
        root.path("indices").toString
      )
      case _ => throw new RuntimeException("Url expected.")
    }
  }

  def unapply(json: String) : Option[Url] = {
    try {
      Some(Url(json))
    }
    catch {
      case _ => None
    }
  }
}
