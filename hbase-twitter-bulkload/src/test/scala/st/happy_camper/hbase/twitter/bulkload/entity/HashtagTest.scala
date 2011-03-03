package st.happy_camper.hbase.twitter.bulkload.entity

import _root_.org.specs._
import _root_.org.specs.runner.{ ConsoleRunner, JUnit4 }

import _root_.dispatch.json._

class HashtagTest extends JUnit4(HashtagSpec)

object HashtagSpecRunner extends ConsoleRunner(HashtagSpec)

object HashtagSpec extends Specification {

  "Hashtag" should {

    "apply JSON" in {
      val hashtag = Hashtag(json)

      hashtag.text    mustEqual "test"
      hashtag.indices mustEqual "[78, 102]"
    }
  }

  val json = Js("""{ "text": "test", "indices": [78, 102] }""")
}
