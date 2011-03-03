package st.happy_camper.hbase.twitter.bulkload.entity

import _root_.dispatch.json._
import _root_.sjson.json._
import _root_.sjson.json.JsonSerialization._

class UserMention(
  val id: Long,
  val screenName: String,
  val name: String,
  val indices: String
)

object UserMention {

  private object UserMentionProtocol extends DefaultProtocol {

    implicit object UserMentionReads extends Reads[UserMention] {
      def reads(json: JsValue) = json match {
        case JsObject(m) => new UserMention(
          fromjson[Long](m(JsString("id"))),
          fromjson[String](m(JsString("screen_name"))),
          fromjson[String](m(JsString("name"))),
          m(JsString("indices")).toString
        )
        case _ => throw new RuntimeException("UserMention expected")
      }
    }
  }

  import UserMentionProtocol._

  def apply(json: JsValue) : UserMention = {
    fromjson[UserMention](json)
  }

  def unapply(json: JsValue) : Option[UserMention] = {
    try {
      Some(UserMention(json))
    }
    catch {
      case _ => None
    }
  }
}
