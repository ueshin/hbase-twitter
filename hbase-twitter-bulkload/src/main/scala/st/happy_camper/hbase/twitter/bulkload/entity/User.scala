package st.happy_camper.hbase.twitter.bulkload.entity

import _root_.java.util.Date
import _root_.java.util.Locale
import _root_.java.text.SimpleDateFormat

import _root_.org.codehaus.jackson.JsonNode
import _root_.org.codehaus.jackson.map.ObjectMapper

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
/*
  private object UserProtocol extends DefaultProtocol {

    implicit object UserReads extends Reads[User] {

      def reads(json: JsValue) = json match {
        case JsObject(m) => new User(
          fromjson[Long](m(JsString("id"))),
          fromjson[String](m(JsString("name"))),
          fromjson[String](m(JsString("screen_name"))),
          try { Option(fromjson[String](m(JsString("location")))) } catch { case _ => None },
          try { Option(fromjson[String](m(JsString("description")))) } catch { case _ => None },
          fromjson[String](m(JsString("profile_image_url"))),
          try { Option(fromjson[String](m(JsString("url")))) } catch { case _ => None },
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
          try { Option(fromjson[Int](m(JsString("utc_offset")))) } catch { case _ => None },
          try { Option(fromjson[String](m(JsString("time_zone")))) } catch { case _ => None },
          fromjson[String](m(JsString("profile_background_image_url"))),
          fromjson[Boolean](m(JsString("profile_background_tile"))),
          fromjson[Boolean](m(JsString("profile_use_background_image"))),
          try { Option(fromjson[Boolean](m(JsString("notifications")))) } catch { case _ => None },
          fromjson[Boolean](m(JsString("geo_enabled"))),
          fromjson[Boolean](m(JsString("verified"))),
          try { Option(fromjson[Boolean](m(JsString("following")))) } catch { case _ => None },
          fromjson[Int](m(JsString("statuses_count"))),
          fromjson[String](m(JsString("lang"))),
          fromjson[Boolean](m(JsString("contributors_enabled"))),
          try { Option(fromjson[Boolean](m(JsString("follow_request_sent")))) } catch { case _ => None }
        )
        case _ => throw new RuntimeException("User expected")
      }
    }
  }

  import UserProtocol._
*/
  def apply(json: String) : User = {
    null
  }

  def unapplly(json: String) : Option[User] = {
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
