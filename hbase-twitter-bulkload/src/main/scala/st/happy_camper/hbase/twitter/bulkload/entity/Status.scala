package st.happy_camper.hbase.twitter.bulkload.entity

import _root_.java.util.Date
import _root_.java.util.Locale
import _root_.java.text.SimpleDateFormat

import _root_.org.codehaus.jackson.JsonNode
import _root_.org.codehaus.jackson.map.ObjectMapper

class Status(
  val createdAt: Date,
  val id: Long,
  val text: String,
  val source: String,
  val truncated: Boolean,
  val inReplyToStatusId: Option[Long],
  val inReplyToUserId: Option[Long],
  val favorited: Boolean,
  val inReplyToScreenName: Option[String],
  val retweeted: Boolean,
  val retweetCount: Option[Long],
  val place: Option[Place],
  val userMentions: List[UserMention],
  val urls: List[Url],
  val hashtags: List[Hashtag],
  val user: User
)

object Status {
/*
  private object StatusProtocol extends DefaultProtocol {

    implicit object StatusReads extends Reads[Status] {
      def reads(json: JsValue) = json match {
        case JsObject(m) => new Status(
          createdAtDateFormat.parse(fromjson[String](m(JsString("created_at")))),
          fromjson[Long](m(JsString("id"))),
          fromjson[String](m(JsString("text"))),
          fromjson[String](m(JsString("source"))),
          fromjson[Boolean](m(JsString("truncated"))),
          try { Option(fromjson[Long](m(JsString("in_reply_to_status_id")))) } catch { case _ => None },
          try { Option(fromjson[Long](m(JsString("in_reply_to_user_id")))) } catch { case _ => None },
          fromjson[Boolean](m(JsString("favorited"))),
          try { Option(fromjson[String](m(JsString("in_reply_to_screen_name")))) } catch { case _ => None },
          fromjson[Boolean](m(JsString("retweeted"))),
          try { Option(fromjson[Long](m(JsString("retweet_count")))) } catch { case _ => None },
          try { Option(Place(m(JsString("place")))) } catch { case _ => None },
          m(JsString("entities")) match {
            case JsObject(m) => {
              m(JsString("user_mentions")) match {
                case JsArray(ts) => ts.map(t => UserMention(t))
                case _ => throw new RuntimeException("Status expected")
              }
            }
            case _ => throw new RuntimeException("Status expected")
          },
          m(JsString("entities")) match {
            case JsObject(m) => {
              m(JsString("urls")) match {
                case JsArray(ts) => ts.map(t => Url(t))
                case _ => throw new RuntimeException("Status expected")
              }
            }
            case _ => throw new RuntimeException("Status expected")
          },
          m(JsString("entities")) match {
            case JsObject(m) => {
              m(JsString("hashtags")) match {
                case JsArray(ts) => ts.map(t => Hashtag(t))
                case _ => throw new RuntimeException("Status expected")
              }
            }
            case _ => throw new RuntimeException("Status expected")
          },
          User(m(JsString("user")))
        )
        case _ => throw new RuntimeException("Status expected")
      }
    }
  }

  import StatusProtocol._
*/
  def apply(json: String) : Status = {
    null
  }

  def unapply(json: String) : Option[Status] = {
    try {
      Option(Status(json))
    }
    catch {
      case _ => None
    }
  }

  def createdAtDateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.US)
}
