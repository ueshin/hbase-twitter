package st.happy_camper.hbase.twitter.entity

import _root_.java.util.Date

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
)
