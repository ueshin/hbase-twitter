package st.happy_camper.hbase.twitter.bulkload.entity

import _root_.org.specs._
import _root_.org.specs.runner.{ ConsoleRunner, JUnit4 }

class UrlTest extends JUnit4(UrlSpec)

object UrlSpecRunner extends ConsoleRunner(UrlSpec)

object UrlSpec extends Specification {

  "Url" should {

    "apply JSON" in {
      val url = Url(json)

      url.url         mustEqual "http://bit.ly/libraryman"
      url.expandedUrl mustEqual None
      url.indices     mustEqual "[78,102]"
    }
  }

  val json = """{ "expanded_url": null, "url": "http://bit.ly/libraryman", "indices": [78, 102] }"""
}
