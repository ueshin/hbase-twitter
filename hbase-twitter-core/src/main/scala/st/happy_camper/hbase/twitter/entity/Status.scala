package st.happy_camper.hbase.twitter.entity

import _root_.scala.collection.JavaConversions._

import _root_.java.util.Date
import _root_.java.util.Locale
import _root_.java.text.SimpleDateFormat

import _root_.org.codehaus.jackson.JsonNode
import _root_.org.codehaus.jackson.map.ObjectMapper

class Status(
  val id: Long,
  val createdAt: Date,

  val source: String,
  val text: String,
  val truncated: Boolean,

  val inReplyToStatusId: Option[Long],
  val inReplyToUserId: Option[Long],
  val inReplyToScreenName: Option[String],

  val favorited: Boolean,
  val retweeted: Boolean,
  val retweetCount: Int,

  val place: Option[Place],
  val userMentions: List[UserMention],
  val urls: List[Url],
  val hashtags: List[Hashtag],

  val user: User
)

object Status {

  def apply(json: JsonNode) : Status = {
    new Status(
      json.path("id").getLongValue,
      createdAtDateFormat.parse(json.path("created_at").getTextValue),

      json.path("source").getTextValue,
      json.path("text").getTextValue,
      json.path("truncated").getBooleanValue,

      Option(json.path("in_reply_to_status_id").getLongValue).filter(_!=0L),
      Option(json.path("in_reply_to_user_id").getLongValue).filter(_!=0L),
      Option(json.path("in_reply_to_screen_name").getTextValue),

      json.path("favorited").getBooleanValue,
      json.path("retweeted").getBooleanValue,
      json.path("retweet_count").getIntValue,

      if(json.path("place").isNull) None else Option(Place(json.path("place"))),
      json.path("entities").path("user_mentions").map(UserMention(_)).toList,
      json.path("entities").path("urls").map(Url(_)).toList,
      json.path("entities").path("hashtags").map(Hashtag(_)).toList,

      User(json.path("user"))
    )
  }

  def unapply(json: JsonNode) : Option[Status] = {
    try {
      Option(Status(json))
    }
    catch {
      case _ => None
    }
  }

  def createdAtDateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.US)
}
