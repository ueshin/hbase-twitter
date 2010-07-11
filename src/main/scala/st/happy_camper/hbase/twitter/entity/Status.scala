package st.happy_camper.hbase.twitter.entity

import _root_.java.util.Date
import _root_.java.util.Locale
import _root_.java.text.SimpleDateFormat

import _root_.scala.xml.Node

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
  val user: Option[User]
) {
  val key = "%016x".format(id)
}

object Status {
  def apply(node: Node) : Status = {
    new Status(
      createdAtDateFormat.parse((node \ "created_at").text),
      (node \ "id").text.toLong,
      (node \ "text").text,
      (node \ "source").text,
      (node \ "truncated").text.toBoolean,
      try { (node \ "in_reply_to_status_id").text.toLong } catch { case e : NumberFormatException => 0L },
      try { (node \ "in_reply_to_user_id").text.toLong } catch { case e : NumberFormatException => 0L },
      (node \ "favorited").text.toBoolean,
      (node \ "in_reply_to_screen_name").text,
      try { Some(User((node \ "user")(0))) } catch { case e : IndexOutOfBoundsException => None }
    )
  }

  def unapply(node: Node) : Option[Status] = {
    try {
      Some(Status(node))
    }
    catch {
      case _ => None
    }
  }

  def createdAtDateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.US)
}
