package st.happy_camper.hbase.twitter.bulkload.entity

import _root_.org.codehaus.jackson.JsonNode
import _root_.org.codehaus.jackson.map.ObjectMapper

class Hashtag(
  val text: String,
  val indices: String
)

object Hashtag {

  def apply(json: String) : Hashtag = {
    Option(new ObjectMapper().readTree(json)) match {
      case Some(root) => new Hashtag(
        root.path("text").getTextValue,
        root.path("indices").toString
      )
      case _ => throw new RuntimeException("Hashtag expected.")
    }
  }

  def unapply(json: String) : Option[Hashtag] = {
    try {
      Option(Hashtag(json))
    }
    catch {
      case _ => None
    }
  }
}
