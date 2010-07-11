package st.happy_camper.hbase.twitter.entity

import _root_.java.util.Date
import _root_.java.util.Locale
import _root_.java.text.SimpleDateFormat

import _root_.scala.xml.Node

class User(
  val id: Long,
  val name: String,
  val screenName: String,
  val location: String,
  val description: String,
  val profileImageUrl: String,
  val url: String,
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
  val utcOffset: Int,
  val timeZone: String,
  val profileBackgroundImageUrl: String,
  val profileBackgroundTile: Boolean,
  val geoEnabled: Boolean,
  val verified: Boolean,
  val statusesCount: Int,
  val lang: String,
  val contributorsEnabled: Boolean
) {
  val key = "%016x".format(id)
}

object User {
  def apply(node: Node) : User = {
    new User(
      (node \ "id").text.toLong,
      (node \ "name").text,
      (node \ "screen_name").text,
      (node \ "location").text,
      (node \ "description").text,
      (node \ "profile_image_url").text,
      (node \ "url").text,
      (node \ "protected").text.toBoolean,
      (node \ "followers_count").text.toInt,
      (node \ "profile_background_color").text,
      (node \ "profile_text_color").text,
      (node \ "profile_link_color").text,
      (node \ "profile_sidebar_fill_color").text,
      (node \ "profile_sidebar_border_color").text,
      (node \ "friends_count").text.toInt,
      createdAtDateFormat.parse((node \ "created_at").text),
      (node \ "favourites_count").text.toInt,
      try { (node \ "utc_offset").text.toInt } catch { case e : NumberFormatException => 0 },
      (node \ "time_zone").text,
      (node \ "profile_background_image_url").text,
      (node \ "profile_background_tile").text.toBoolean,
      (node \ "geo_enabled").text.toBoolean,
      (node \ "verified").text.toBoolean,
      (node \ "statuses_count").text.toInt,
      (node \ "lang").text,
      (node \ "contributors_enabled").text.toBoolean
    )
  }

  def createdAtDateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.US)
}
