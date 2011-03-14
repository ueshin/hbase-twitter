package st.happy_camper.hbase.twitter.bulkload.entity

import _root_.org.codehaus.jackson.JsonNode
import _root_.org.codehaus.jackson.map.ObjectMapper

class UserMention(
  val id: Long,
  val screenName: String,
  val name: String,
  val indices: String
)

object UserMention {

  def apply(json: String) : UserMention = {
    Option(new ObjectMapper().readTree(json)) match {
      case Some(root) => new UserMention(
        root.path("id").getLongValue,
        root.path("screen_name").getTextValue,
        root.path("name").getTextValue,
        root.path("indices").toString
      )
      case _ => throw new RuntimeException("UserMention expected.")
    }
  }

  def unapply(json: String) : Option[UserMention] = {
    try {
      Option(UserMention(json))
    }
    catch {
      case _ => None
    }
  }
}
