package st.happy_camper.hbase.twitter.bulkload.entity

import _root_.java.util.Date
import _root_.java.util.Locale
import _root_.java.text.SimpleDateFormat

import _root_.org.codehaus.jackson.JsonNode

class User(
  val id: Long,
  val name: String,
  val screenName: String,
  val createdAt: Date,
  val description: Option[String],
  val url: Option[String],

  val lang: String,
  val location: Option[String],
  val timeZone: Option[String],
  val utcOffset: Option[Int],

  val statusesCount: Int,
  val favouritesCount: Int,
  val followersCount: Int,
  val friendsCount: Int,
  val listedCount: Int,

  val profileImageUrl: String,
  val profileBackgroundImageUrl: String,
  val profileTextColor: Option[String],
  val profileLinkColor: Option[String],
  val profileSidebarFillColor: Option[String],
  val profileSidebarBorderColor: Option[String],
  val profileBackgroundColor: Option[String],
  val profileBackgroundTile: Boolean,
  val profileUseBackgroundImage: Boolean,

  val isProtected: Boolean,
  val following: Boolean,
  val followRequestSent: Boolean,

  val notifications: Boolean,
  val verified: Boolean,
  val geoEnabled: Boolean,
  val contributorsEnabled: Boolean,
  val showAllInlineMedia: Boolean,
  val isTranslator: Boolean
) {
  val key = User.createKey(id)
}

object User {

  def apply(json: JsonNode) : User = {
    new User(
      json.path("id").getLongValue,
      json.path("name").getTextValue,
      json.path("screen_name").getTextValue,
      createdAtDateFormat.parse(json.path("created_at").getTextValue),
      Option(json.path("description").getTextValue),
      Option(json.path("url").getTextValue),

      json.path("lang").getTextValue,
      Option(json.path("location").getTextValue),
      Option(json.path("time_zone").getTextValue),
      if(json.path("utc_offset").isNumber) Option(json.path("utc_offset").getIntValue) else None,

      json.path("statuses_count").getIntValue,
      json.path("favourites_count").getIntValue,
      json.path("followers_count").getIntValue,
      json.path("friends_count").getIntValue,
      json.path("listed_count").getIntValue,

      json.path("profile_image_url").getTextValue,
      json.path("profile_background_image_url").getTextValue,
      Option(json.path("profile_text_color").getTextValue),
      Option(json.path("profile_link_color").getTextValue),
      Option(json.path("profile_sidebar_fill_color").getTextValue),
      Option(json.path("profile_sidebar_border_color").getTextValue),
      Option(json.path("profile_background_color").getTextValue),
      json.path("profile_background_tile").getBooleanValue,
      json.path("profile_use_background_image").getBooleanValue,

      json.path("protected").getBooleanValue,
      json.path("following").getBooleanValue,
      json.path("follow_request_sent").getBooleanValue,

      json.path("notifications").getBooleanValue,
      json.path("verified").getBooleanValue,
      json.path("geo_enabled").getBooleanValue,
      json.path("contributors_enabled").getBooleanValue,
      json.path("show_all_inline_media").getBooleanValue,
      json.path("is_translator").getBooleanValue
    )
  }

  def unapplly(json: JsonNode) : Option[User] = {
    try {
      Option(User(json))
    }
    catch {
      case _ => None
    }
  }

  def createKey(id: Long) = "%016x".format(id)
  def createdAtDateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.US)
}
