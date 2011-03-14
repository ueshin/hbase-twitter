package st.happy_camper.hbase.twitter.bulkload.entity

import _root_.org.specs._
import _root_.org.specs.runner.{ ConsoleRunner, JUnit4 }

class StatusTest extends JUnit4(StatusSpec)

object StatusSpecRunner extends ConsoleRunner(StatusSpec)

object StatusSpec extends Specification {

  "Status" should {

    "apply JSON" in {
      val status = Status(json)

      status.createdAt           mustEqual Status.createdAtDateFormat.parse("Fri Jul 16 16:55:52 +0000 2010")
      status.id                  mustEqual 18700688341L
      status.text                mustEqual "Anything is possible when you're in the library... with a celestial sandwich: http://bit.ly/libraryman (via @iamcal)"
      status.source              mustEqual "web"
      status.truncated           mustEqual false
      status.inReplyToStatusId   mustEqual None
      status.inReplyToUserId     mustEqual None
      status.favorited           mustEqual false
      status.inReplyToScreenName mustEqual None
      status.retweeted           mustEqual false
      status.retweetCount.get    mustEqual 98L

      status.place match {
        case Some(place) => {
          place.id              mustEqual "5a110d312052166f"
          place.name            mustEqual "San Francisco"
          place.fullName        mustEqual "San Francisco, CA"
          place.placeType       mustEqual "city"
          place.url             mustEqual "http://api.twitter.com/1/geo/id/5a110d312052166f.json"
          place.boundingBox     mustEqual "[[[-122.51368188, 37.70813196], [-122.35845384, 37.70813196], [-122.35845384, 37.83245301], [-122.51368188, 37.83245301]]]"
          place.boundingBoxType mustEqual "Polygon"
          place.country         mustEqual "The United States of America"
          place.countryCode     mustEqual "US"
        }
        case _ => fail
      }

      status.userMentions match {
        case List(userMention) => {
          userMention.id         mustEqual 6104L
          userMention.screenName mustEqual "iamcal"
          userMention.name       mustEqual "Cal Henderson"
          userMention.indices    mustEqual "[108, 115]"
        }
        case _ => fail
      }

      status.urls match {
        case List(url) => {
          url.url         mustEqual "http://bit.ly/libraryman"
          url.expandedUrl mustEqual None
          url.indices     mustEqual "[78, 102]"
        }
        case _ => fail
      }

      status.hashtags mustEqual Nil

      val user = status.user
      user.id                        mustEqual 635543
      user.name                      mustEqual "Daniel Burka"
      user.screenName                mustEqual "dburka"
      user.location.get              mustEqual "San Francisco"
      user.description.get           mustEqual "Director of design at Tiny Speck. Ex-Creative director at Digg. CSS. Design. UX. Climbing. Cycling. Chilaquiles mmm."
      user.profileImageUrl           mustEqual "http://a3.twimg.com/profile_images/74260755/2009-square-small_normal.jpg"
      user.url.get                   mustEqual "http://deltatangobravo.com"
      user.isProtected               mustEqual false
      user.followersCount            mustEqual 9950
      user.profileBackgroundColor    mustEqual "BADFCD"
      user.profileTextColor          mustEqual "0C3E53"
      user.profileLinkColor          mustEqual "5a0d91"
      user.profileSidebarFillColor   mustEqual "f1ccff"
      user.profileSidebarBorderColor mustEqual "a655ec"
      user.friendsCount              mustEqual 219
      user.createdAt                 mustEqual User.createdAtDateFormat.parse("Mon Jan 15 15:22:14 +0000 2007")
      user.favouritesCount           mustEqual 92
      user.utcOffset.get             mustEqual -28800
      user.timeZone.get              mustEqual "Pacific Time (US & Canada)"
      user.profileBackgroundImageUrl mustEqual "http://a3.twimg.com/profile_background_images/4444585/back.png"
      user.profileBackgroundTile     mustEqual true
      user.profileUseBackgroundImage mustEqual true
      user.notifications.get         mustEqual false
      user.geoEnabled                mustEqual true
      user.verified                  mustEqual false
      user.statusesCount             mustEqual 806
      user.lang                      mustEqual "en"
      user.contributorsEnabled       mustEqual false
      user.followRequestSent.get     mustEqual false
    }
  }

  val json = """{
    "coordinates": null,
    "favorited": false,
    "created_at": "Fri Jul 16 16:55:52 +0000 2010",
    "truncated": false,
    "entities": {
      "urls": [
        {
          "expanded_url": null,
          "url": "http://bit.ly/libraryman",
          "indices": [
            78,
            102
          ]
        }
      ],
      "hashtags": [

      ],
      "user_mentions": [
        {
          "name": "Cal Henderson",
          "id": 6104,
          "indices": [
            108,
            115
          ],
          "screen_name": "iamcal"
        }
      ]
    },
    "text": "Anything is possible when you're in the library... with a celestial sandwich: http://bit.ly/libraryman (via @iamcal)",
    "annotations": null,
    "contributors": null,
    "id": 18700688341,
    "geo": null,
    "in_reply_to_user_id": null,
    "place": {
      "name": "San Francisco",
      "country_code": "US",
      "country": "The United States of America",
      "attributes": {

      },
      "url": "http://api.twitter.com/1/geo/id/5a110d312052166f.json",
      "id": "5a110d312052166f",
      "bounding_box": {
        "coordinates": [
          [
            [
              -122.51368188,
              37.70813196
            ],
            [
              -122.35845384,
              37.70813196
            ],
            [
              -122.35845384,
              37.83245301
            ],
            [
              -122.51368188,
              37.83245301
            ]
          ]
        ],
        "type": "Polygon"
      },
      "full_name": "San Francisco, CA",
      "place_type": "city"
    },
    "in_reply_to_screen_name": null,
    "user": {
      "name": "Daniel Burka",
      "profile_sidebar_border_color": "a655ec",
      "profile_background_tile": true,
      "profile_sidebar_fill_color": "f1ccff",
      "created_at": "Mon Jan 15 15:22:14 +0000 2007",
      "profile_image_url": "http://a3.twimg.com/profile_images/74260755/2009-square-small_normal.jpg",
      "location": "San Francisco",
      "profile_link_color": "5a0d91",
      "follow_request_sent": false,
      "url": "http://deltatangobravo.com",
      "favourites_count": 92,
      "contributors_enabled": false,
      "utc_offset": -28800,
      "id": 635543,
      "profile_use_background_image": true,
      "profile_text_color": "0C3E53",
      "protected": false,
      "followers_count": 9950,
      "lang": "en",
      "notifications": false,
      "time_zone": "Pacific Time (US & Canada)",
      "verified": false,
      "profile_background_color": "BADFCD",
      "geo_enabled": true,
      "description": "Director of design at Tiny Speck. Ex-Creative director at Digg. CSS. Design. UX. Climbing. Cycling. Chilaquiles mmm.",
      "friends_count": 219,
      "statuses_count": 806,
      "profile_background_image_url": "http://a3.twimg.com/profile_background_images/4444585/back.png",
      "following": true,
      "screen_name": "dburka"
    },
    "source": "web",
    "in_reply_to_status_id": null,
    "retweeted": false,
    "retweet_count": 98
  }"""
}
