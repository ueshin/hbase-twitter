package st.happy_camper.hbase.twitter.bulkload.entity

import _root_.org.specs._
import _root_.org.specs.runner.{ ConsoleRunner, JUnit4 }

import _root_.org.codehaus.jackson.map.ObjectMapper

class UserMentionTest extends JUnit4(UserMentionSpec)

object UserMentionSpecRunner extends ConsoleRunner(UserMentionSpec)

object UserMentionSpec extends Specification {

  "UserMention" should {

    "apply JSON" in {
      val userMention = UserMention(new ObjectMapper().readTree(json))

      userMention.id         mustEqual 6104L
      userMention.name       mustEqual "Cal Henderson"
      userMention.screenName mustEqual "iamcal"
      userMention.indices    mustEqual "[108,115]"
    }
  }

  val json = """{ "name": "Cal Henderson", "id": 6104, "indices": [108, 115], "screen_name": "iamcal" }"""
}
