package st.happy_camper.hbase.twitter.bulkload.entity

import _root_.org.specs._
import _root_.org.specs.runner.{ ConsoleRunner, JUnit4 }

import _root_.org.codehaus.jackson.map.ObjectMapper

class UserTest extends JUnit4(UserSpec)

object UserSpecRunner extends ConsoleRunner(UserSpec)

object UserSpec extends Specification {

  "User" should {
    "apply JSON" in {
      val user = User(new ObjectMapper().readTree(json))

      user.id                        mustEqual 3404
      user.name                      mustEqual "Kevin Lawver"
      user.screenName                mustEqual "kplawver"
      user.location.get              mustEqual "Savannah, GA, US"
      user.description.get           mustEqual "Nerd who loves web standards, Ruby on Rails and all things webbish. Chief Architect @ http://uplaya.com and co-founder of http://ficly.com. Happy dad & husband."
      user.profileImageUrl           mustEqual "http://a3.twimg.com/profile_images/980477983/oh_lawver_normal.jpg"
      user.url.get                   mustEqual "http://lawver.net"
      user.isProtected               mustEqual false
      user.followersCount            mustEqual 1049
      user.profileBackgroundColor    mustEqual "003030"
      user.profileTextColor          mustEqual "003030"
      user.profileLinkColor          mustEqual "DC4104"
      user.profileSidebarFillColor   mustEqual "E6E2B0"
      user.profileSidebarBorderColor mustEqual "A8A37E"
      user.friendsCount              mustEqual 268
      user.createdAt                 mustEqual User.createdAtDateFormat.parse("Sat Jul 29 18:23:37 +0000 2006")
      user.favouritesCount           mustEqual 148
      user.utcOffset.get             mustEqual -18000
      user.timeZone.get              mustEqual "Eastern Time (US & Canada)"
      user.profileBackgroundImageUrl mustEqual "http://a1.twimg.com/profile_background_images/1391712/twitter_back.png"
      user.profileBackgroundTile     mustEqual true
      user.profileUseBackgroundImage mustEqual true
      user.notifications.get         mustEqual false
      user.geoEnabled                mustEqual true
      user.verified                  mustEqual false
      user.statusesCount             mustEqual 10561
      user.lang                      mustEqual "en"
      user.contributorsEnabled       mustEqual false
      user.followRequestSent.get     mustEqual false
    }
  }

  val json = """{
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
    }"""
}
