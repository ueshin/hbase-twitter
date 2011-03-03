package st.happy_camper.hbase.twitter.bulkload.entity

import _root_.java.util.Date
import _root_.java.util.Locale
import _root_.java.text.SimpleDateFormat

import _root_.dispatch.json._
import _root_.sjson.json._
import _root_.sjson.json.JsonSerialization._

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
  val profileUseBackgroundImage: Boolean,
  val notifications: Boolean,
  val geoEnabled: Boolean,
  val verified: Boolean,
  val statusesCount: Int,
  val lang: String,
  val contributorsEnabled: Boolean,
  val followRequestSent: Boolean
) {
  val key = User.createKey(id)
}

object User {

  private object UserProtocol extends DefaultProtocol {

    implicit object UserReads extends Reads[User] {

      def reads(json: JsValue) = json match {
        case JsObject(m) => new User(
          fromjson[Long](m(JsString("id"))),
          fromjson[String](m(JsString("name"))),
          fromjson[String](m(JsString("screen_name"))),
          fromjson[String](m(JsString("location"))),
          fromjson[String](m(JsString("description"))),
          fromjson[String](m(JsString("profile_image_url"))),
          fromjson[String](m(JsString("url"))),
          fromjson[Boolean](m(JsString("protected"))),
          fromjson[Int](m(JsString("followers_count"))),
          fromjson[String](m(JsString("profile_background_color"))),
          fromjson[String](m(JsString("profile_text_color"))),
          fromjson[String](m(JsString("profile_link_color"))),
          fromjson[String](m(JsString("profile_sidebar_fill_color"))),
          fromjson[String](m(JsString("profile_sidebar_border_color"))),
          fromjson[Int](m(JsString("friends_count"))),
          createdAtDateFormat.parse(fromjson[String](m(JsString("created_at")))),
          fromjson[Int](m(JsString("favourites_count"))),
          fromjson[Int](m(JsString("utc_offset"))),
          fromjson[String](m(JsString("time_zone"))),
          fromjson[String](m(JsString("profile_background_image_url"))),
          fromjson[Boolean](m(JsString("profile_background_tile"))),
          fromjson[Boolean](m(JsString("profile_use_background_image"))),
          fromjson[Boolean](m(JsString("notifications"))),
          fromjson[Boolean](m(JsString("geo_enabled"))),
          fromjson[Boolean](m(JsString("verified"))),
          fromjson[Int](m(JsString("statuses_count"))),
          fromjson[String](m(JsString("lang"))),
          fromjson[Boolean](m(JsString("contributors_enabled"))),
          fromjson[Boolean](m(JsString("follow_request_sent")))
        )
        case _ => throw new RuntimeException("User expected")
      }
    }
  }

  import UserProtocol._

  def apply(json: JsValue) : User = {
    fromjson[User](json)
  }

  def createKey(id: Long) = "%016x".format(id)
  def createdAtDateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.US)
}
