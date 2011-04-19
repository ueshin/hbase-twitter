package st.happy_camper.hbase.twitter.importer.entity

import _root_.org.specs._
import _root_.org.specs.runner.{ ConsoleRunner, JUnit4 }

import _root_.org.codehaus.jackson.map.ObjectMapper

class HashtagTest extends JUnit4(HashtagSpec)

object HashtagSpecRunner extends ConsoleRunner(HashtagSpec)

object HashtagSpec extends Specification {

  "Hashtag" should {

    "apply JSON" in {
      val hashtag = Hashtag(new ObjectMapper().readTree(json))

      hashtag.text    mustEqual "test"
      hashtag.indices mustEqual "[78,102]"
    }
  }

  val json = """{ "text": "test", "indices": [78, 102] }"""
}
