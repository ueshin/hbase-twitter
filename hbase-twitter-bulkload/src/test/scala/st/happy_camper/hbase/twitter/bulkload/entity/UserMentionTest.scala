package st.happy_camper.hbase.twitter.bulkload.entity

import _root_.org.specs._
import _root_.org.specs.runner.{ ConsoleRunner, JUnit4 }

import _root_.dispatch.json._

class UserMentionTest extends JUnit4(UserMentionSpec)

object UserMentionSpecRunner extends ConsoleRunner(UserMentionSpec)

object UserMentionSpec extends Specification {

  "UserMention" should {

    "apply JSON" in {
      val userMention = UserMention(json)

      userMention.id         mustEqual 6104L
      userMention.screenName mustEqual "iamcal"
      userMention.name       mustEqual "Cal Henderson"
      userMention.indices    mustEqual "[108, 115]"
    }
  }

  val json = Js("""{ "name": "Cal Henderson", "id": 6104, "indices": [108, 115], "screen_name": "iamcal" }""")
}
