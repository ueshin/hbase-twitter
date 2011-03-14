package st.happy_camper.hbase.twitter.bulkload.entity

import _root_.java.util.Date
import _root_.java.util.Locale
import _root_.java.text.SimpleDateFormat

import _root_.org.codehaus.jackson.JsonNode

class User(
  val id: Long,
  val name: String,
  val screenName: String,
  val location: Option[String],
  val description: Option[String],
  val profileImageUrl: String,
  val url: Option[String],
  val isProtected: Boolean,
  val followersCount: Int,
  val profileBackgroundColor: String,
  val profileTextColor: String,
  val profileLinkColor: String,
  val profileSidebarFillColor: String,
  val profileSidebarBorderColor: String,
  val friendsCount: Int,
  val createdAt: Date,
  val favouritesCount: Int,
  val utcOffset: Option[Int],
  val timeZone: Option[String],
  val profileBackgroundImageUrl: String,
  val profileBackgroundTile: Boolean,
  val profileUseBackgroundImage: Boolean,
  val notifications: Option[Boolean],
  val geoEnabled: Boolean,
  val verified: Boolean,
  val following: Option[Boolean],
  val statusesCount: Int,
  val lang: String,
  val contributorsEnabled: Boolean,
  val followRequestSent: Option[Boolean]
) {
  val key = User.createKey(id)
}

object User {

  def apply(json: JsonNode) : User = {
    new User(
      json.path("id").getLongValue,
      json.path("name").getTextValue,
      json.path("screen_name").getTextValue,
      Option(json.path("location").getTextValue),
      Option(json.path("description").getTextValue),
      json.path("profile_image_url").getTextValue,
      Option(json.path("url").getTextValue),
      json.path("protected").getBooleanValue,
      json.path("followers_count").getIntValue,
      json.path("profile_background_color").getTextValue,
      json.path("profile_text_color").getTextValue,
      json.path("profile_link_color").getTextValue,
      json.path("profile_sidebar_fill_color").getTextValue,
      json.path("profile_sidebar_border_color").getTextValue,
      json.path("friends_count").getIntValue,
      createdAtDateFormat.parse(json.path("created_at").getTextValue),
      json.path("favourites_count").getIntValue,
      if(json.path("utc_offset").isNumber) Option(json.path("utc_offset").getIntValue) else None,
      Option(json.path("time_zone").getTextValue),
      json.path("profile_background_image_url").getTextValue,
      json.path("profile_background_tile").getBooleanValue,
      json.path("profile_use_background_image").getBooleanValue,
      if(json.path("notifications").isBoolean) Option(json.path("notifications").getBooleanValue) else None,
      json.path("geo_enabled").getBooleanValue,
      json.path("verified").getBooleanValue,
      if(json.path("following").isBoolean) Option(json.path("following").getBooleanValue) else None,
      json.path("statuses_count").getIntValue,
      json.path("lang").getTextValue,
      json.path("contributors_enabled").getBooleanValue,
      if(json.path("follow_request_sent").isBoolean) Option(json.path("follow_request_sent").getBooleanValue) else None
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
