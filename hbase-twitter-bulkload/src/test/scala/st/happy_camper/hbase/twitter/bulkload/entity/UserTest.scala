package st.happy_camper.hbase.twitter.bulkload.entity

import _root_.org.specs._
import _root_.org.specs.runner.{ ConsoleRunner, JUnit4 }

import _root_.dispatch.json._

class UserTest extends JUnit4(UserSpec)

object UserSpecRunner extends ConsoleRunner(UserSpec)

object UserSpec extends Specification {

  "User" should {
    "apply JSON" in {
      val user3404 = User(json3404)

      user3404.id                        mustEqual 3404
      user3404.name                      mustEqual "Kevin Lawver"
      user3404.screenName                mustEqual "kplawver"
      user3404.location                  mustEqual "Savannah, GA, US"
      user3404.description               mustEqual "Nerd who loves web standards, Ruby on Rails and all things webbish. Chief Architect @ http://uplaya.com and co-founder of http://ficly.com. Happy dad & husband."
      user3404.profileImageUrl           mustEqual "http://a3.twimg.com/profile_images/980477983/oh_lawver_normal.jpg"
      user3404.url                       mustEqual "http://lawver.net"
      user3404.isProtected               mustEqual false
      user3404.followersCount            mustEqual 1049
      user3404.profileBackgroundColor    mustEqual "003030"
      user3404.profileTextColor          mustEqual "003030"
      user3404.profileLinkColor          mustEqual "DC4104"
      user3404.profileSidebarFillColor   mustEqual "E6E2B0"
      user3404.profileSidebarBorderColor mustEqual "A8A37E"
      user3404.friendsCount              mustEqual 268
      user3404.createdAt                 mustEqual User.createdAtDateFormat.parse("Sat Jul 29 18:23:37 +0000 2006")
      user3404.favouritesCount           mustEqual 148
      user3404.utcOffset                 mustEqual -18000
      user3404.timeZone                  mustEqual "Eastern Time (US & Canada)"
      user3404.profileBackgroundImageUrl mustEqual "http://a1.twimg.com/profile_background_images/1391712/twitter_back.png"
      user3404.profileBackgroundTile     mustEqual true
      user3404.profileUseBackgroundImage mustEqual true
      user3404.notifications             mustEqual false
      user3404.geoEnabled                mustEqual true
      user3404.verified                  mustEqual false
      user3404.statusesCount             mustEqual 10561
      user3404.lang                      mustEqual "en"
      user3404.contributorsEnabled       mustEqual false
      user3404.followRequestSent         mustEqual false
    }
  }

  val json3404 = Js("""{
      "name": "Kevin Lawver",
      "profile_sidebar_border_color": "A8A37E",
      "profile_background_tile": true,
      "profile_sidebar_fill_color": "E6E2B0",
      "created_at": "Sat Jul 29 18:23:37 +0000 2006",
      "profile_image_url": "http://a3.twimg.com/profile_images/980477983/oh_lawver_normal.jpg",
      "location": "Savannah, GA, US",
      "profile_link_color": "DC4104",
      "follow_request_sent": false,
      "url": "http://lawver.net",
      "favourites_count": 148,
      "contributors_enabled": false,
      "utc_offset": -18000,
      "id": 3404,
      "profile_use_background_image": true,
      "profile_text_color": "003030",
      "protected": false,
      "followers_count": 1049,
      "lang": "en",
      "notifications": false,
      "time_zone": "Eastern Time (US & Canada)",
      "verified": false,
      "profile_background_color": "003030",
      "geo_enabled": true,
      "description": "Nerd who loves web standards, Ruby on Rails and all things webbish. Chief Architect @ http://uplaya.com and co-founder of http://ficly.com. Happy dad & husband.",
      "friends_count": 268,
      "statuses_count": 10561,
      "profile_background_image_url": "http://a1.twimg.com/profile_background_images/1391712/twitter_back.png",
      "following": true,
      "screen_name": "kplawver"
    }""")
}
