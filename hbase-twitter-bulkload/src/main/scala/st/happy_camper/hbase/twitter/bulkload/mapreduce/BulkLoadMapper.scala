package st.happy_camper.hbase.twitter.bulkload
package mapreduce

import entity._

import _root_.org.apache.hadoop.io.{ LongWritable, Text }
import _root_.org.apache.hadoop.mapreduce.Mapper

import _root_.org.apache.hadoop.hbase.client.Put
import _root_.org.apache.hadoop.hbase.io.ImmutableBytesWritable
import _root_.org.apache.hadoop.hbase.util.Bytes

import _root_.org.codehaus.jackson.map.ObjectMapper

class BulkLoadMapper extends Mapper[LongWritable, Text, ImmutableBytesWritable, Put] {

  type Context = Mapper[LongWritable, Text, ImmutableBytesWritable, Put]#Context

  private val keyout = new ImmutableBytesWritable

  override def map(key: LongWritable, value: Text, context: Context) {
    val flumeEvent = FlumeEvent(new ObjectMapper().readTree(value.toString))
    new ObjectMapper().readTree(flumeEvent.body) match {

      case Status(status) => {

        keyout.set(Bytes.add(Array((flumeEvent.timestamp & 0xf).asInstanceOf[Byte]), Long.MaxValue - flumeEvent.timestamp, status.id))

        val put = new Put(keyout.get, flumeEvent.timestamp)
        put.add("status", "created_at", status.createdAt.getTime)
        put.add("status", "id", status.id)
        put.add("status", "text", status.text)
        put.add("status", "source", status.source)
        put.add("status", "truncated", status.truncated)
        status.inReplyToStatusId.foreach(put.add("status", "in_reply_to_status_id", _))
        status.inReplyToUserId.foreach(put.add("status", "in_reply_to_user_id", _))
        put.add("status", "favorited", status.favorited)
        status.inReplyToScreenName.foreach(put.add("status", "in_reply_to_screen_name", _))
        status.place.foreach {
          place => {
            put.add("place", "id", place.id)
            put.add("place", "name", place.name)
            put.add("place", "full_name", place.fullName)
            put.add("place", "place_type", place.placeType)
            put.add("place", "url", place.url)
            put.add("place", "bounding_box", place.boundingBox)
            put.add("place", "bounding_box_type", place.boundingBoxType)
            put.add("place", "country", place.country)
            put.add("place", "country_code", place.countryCode)
          }
        }
        status.userMentions.foreach {
          userMention => {
            put.add("user_mentions", Bytes.add(userMention.id, "id"), userMention.id)
            put.add("user_mentions", Bytes.add(userMention.id, "screen_name"), userMention.screenName)
            put.add("user_mentions", Bytes.add(userMention.id, "name"), userMention.name)
            put.add("user_mentions", Bytes.add(userMention.id, "indices"), userMention.indices)
          }
        }
        status.urls.foreach {
          url => {
            put.add("urls", Bytes.add(url.url, "url"), url.url)
            url.expandedUrl.map(put.add("urls", Bytes.add(url.url, "expanded_url"), _))
            put.add("urls", Bytes.add(url.url, "indices"), url.indices)
          }
        }
        status.hashtags.foreach {
          hashtag => {
            put.add("hashtags", hashtag.text, hashtag.indices)
          }
        }

        val user = status.user
        put.add("user", "id", user.id)
        put.add("user", "name", user.name)
        put.add("user", "screen_name", user.screenName)
        user.location.foreach(put.add("user", "location", _))
        user.description.foreach(put.add("user", "description", _))
        put.add("user", "profile_image_url", user.profileImageUrl)
        user.url.foreach(put.add("user", "url", _))
        put.add("user", "protected", user.isProtected)
        put.add("user", "followers_count", user.followersCount)
        put.add("user", "profile_background_color", user.profileBackgroundColor)
        put.add("user", "profile_text_color", user.profileTextColor)
        put.add("user", "profile_link_color", user.profileLinkColor)
        put.add("user", "profile_sidebar_fill_color", user.profileSidebarFillColor)
        put.add("user", "profile_sidebar_border_color", user.profileSidebarBorderColor)
        put.add("user", "friends_count", user.friendsCount)
        put.add("user", "created_at", user.createdAt.getTime)
        put.add("user", "favourites_count", user.favouritesCount)
        user.utcOffset.foreach(put.add("user", "utc_offset", _))
        user.timeZone.foreach(put.add("user", "time_zone", _))
        put.add("user", "profile_background_image_url", user.profileBackgroundImageUrl)
        put.add("user", "profile_background_tile", user.profileBackgroundTile)
        put.add("user", "profile_use_background_image", user.profileUseBackgroundImage)
        user.notifications.foreach(put.add("user", "notifications", _))
        put.add("user", "geo_enabled", user.geoEnabled)
        put.add("user", "verified", user.verified)
        put.add("user", "statuses_count", user.statusesCount)
        put.add("user", "lang", user.lang)
        put.add("user", "contributors_enabled", user.contributorsEnabled)
        user.followRequestSent.foreach(put.add("user", "follow_request_sent", _))

        context.write(keyout, put)

      }
      case o => println(o.toString)
    }
  }
}
