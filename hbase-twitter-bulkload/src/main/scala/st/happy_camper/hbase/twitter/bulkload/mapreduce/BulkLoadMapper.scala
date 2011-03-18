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
        put.add("status", "id", status.id)
        put.add("status", "created_at", status.createdAt.getTime)

        put.add("status", "source", status.source)
        put.add("status", "text", status.text)
        put.add("status", "truncated", status.truncated)

        status.inReplyToStatusId.foreach(put.add("status", "in_reply_to_status_id", _))
        status.inReplyToUserId.foreach(put.add("status", "in_reply_to_user_id", _))
        status.inReplyToScreenName.foreach(put.add("status", "in_reply_to_screen_name", _))

        put.add("status", "favorited", status.favorited)
        put.add("status", "retweeted", status.retweeted)
        put.add("status", "retweet_count", status.retweetCount)

        status.place.foreach {
          place => {
            put.add("place", "id", place.id)
            put.add("place", "name", place.name)
            put.add("place", "full_name", place.fullName)
            put.add("place", "url", place.url)
            put.add("place", "place_type", place.placeType)
            put.add("place", "country", place.country)
            put.add("place", "country_code", place.countryCode)
            place.boundingBox.foreach(put.add("place", "bounding_box", _))
            place.boundingBoxType.foreach(put.add("place", "bounding_box_type", _))
            put.add("place", "attributes", place.attributes)
          }
        }

        status.userMentions.foreach {
          userMention => {
            put.add("user_mentions", Bytes.add(userMention.id, "id"), userMention.id)
            put.add("user_mentions", Bytes.add(userMention.id, "name"), userMention.name)
            put.add("user_mentions", Bytes.add(userMention.id, "screen_name"), userMention.screenName)
            put.add("user_mentions", Bytes.add(userMention.id, "indices"), userMention.indices)
          }
        }

        status.urls.foreach {
          url => {
            put.add("urls", Bytes.add(url.url, "url"), url.url)
            url.expandedUrl.foreach(put.add("urls", Bytes.add(url.url, "expanded_url"), _))
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
        put.add("user", "created_at", user.createdAt.getTime)
        user.description.foreach(put.add("user", "description", _))
        user.url.foreach(put.add("user", "url", _))

        put.add("user", "lang", user.lang)
        user.location.foreach(put.add("user", "location", _))
        user.timeZone.foreach(put.add("user", "time_zone", _))
        user.utcOffset.foreach(put.add("user", "utc_offset", _))

        put.add("user", "statuses_count", user.statusesCount)
        put.add("user", "favourites_count", user.favouritesCount)
        put.add("user", "followers_count", user.followersCount)
        put.add("user", "friends_count", user.friendsCount)
        put.add("user", "listed_count", user.listedCount)

        put.add("user", "profile_image_url", user.profileImageUrl)
        put.add("user", "profile_background_image_url", user.profileBackgroundImageUrl)
        user.profileTextColor.foreach(put.add("user", "profile_text_color", _))
        user.profileLinkColor.foreach(put.add("user", "profile_link_color", _))
        user.profileSidebarFillColor.foreach(put.add("user", "profile_sidebar_fill_color", _))
        user.profileSidebarBorderColor.foreach(put.add("user", "profile_sidebar_border_color", _))
        user.profileBackgroundColor.foreach(put.add("user", "profile_background_color", _))
        put.add("user", "profile_background_tile", user.profileBackgroundTile)
        put.add("user", "profile_use_background_image", user.profileUseBackgroundImage)

        put.add("user", "protected", user.isProtected)
        put.add("user", "following", user.following)
        put.add("user", "follow_request_sent", user.followRequestSent)

        put.add("user", "notifications", user.notifications)
        put.add("user", "verified", user.verified)
        put.add("user", "geo_enabled", user.geoEnabled)
        put.add("user", "contributors_enabled", user.contributorsEnabled)
        put.add("user", "show_all_inline_media", user.showAllInlineMedia)
        put.add("user", "is_translator", user.isTranslator)

        context.write(keyout, put)

      }
      case o => println(o.toString)
    }
  }
}
