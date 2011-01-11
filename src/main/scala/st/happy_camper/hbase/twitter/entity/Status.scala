package st.happy_camper.hbase.twitter.entity

import _root_.java.util.Date
import _root_.java.util.Locale
import _root_.java.text.SimpleDateFormat

import _root_.dispatch.json._
import _root_.sjson.json._
import _root_.sjson.json.JsonSerialization._

class Status(
  val createdAt: Date,
  val id: Long,
  val text: String,
  val source: String,
  val truncated: Boolean,
  val inReplyToStatusId: Long,
  val inReplyToUserId: Long,
  val favorited: Boolean,
  val inReplyToScreenName: String,
  val user: User
) {
  val key = Status.createKey(id)
}

object Status {

  private object StatusProtocol extends DefaultProtocol {

    implicit object StatusReads extends Reads[Status] {
      def reads(json: JsValue) = json match {
        case JsObject(m) => new Status(
          createdAtDateFormat.parse(fromjson[String](m(JsString("created_at")))),
          fromjson[Long](m(JsString("id"))),
          fromjson[String](m(JsString("text"))),
          fromjson[String](m(JsString("source"))),
          fromjson[Boolean](m(JsString("truncated"))),
          try { fromjson[Long](m(JsString("in_reply_to_status_id"))) } catch { case _ => 0L },
          try { fromjson[Long](m(JsString("in_reply_to_user_id"))) } catch { case _ => 0L },
          fromjson[Boolean](m(JsString("favorited"))),
          try { fromjson[String](m(JsString("in_reply_to_screen_name"))) } catch { case _ => "" },
          User(m(JsString("user")))
        )
        case _ => throw new RuntimeException("Status expected")
      }
    }
  }

  import StatusProtocol._

  def apply(json: JsValue) : Status = {
    fromjson[Status](json)
  }

  def unapply(json: JsValue) : Option[Status] = {
    try {
      Some(Status(json))
    }
    catch {
      case _ => None
    }
  }

  def createKey(id: Long) = "%016x".format(id)
  def createdAtDateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.US)
}
