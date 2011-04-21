package st.happy_camper.hbase.twitter.entity

import _root_.java.util.Date

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
