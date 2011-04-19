package st.happy_camper.hbase.twitter.entity

import _root_.org.specs._
import _root_.org.specs.runner.{ ConsoleRunner, JUnit4 }

import _root_.org.codehaus.jackson.map.ObjectMapper

class UrlTest extends JUnit4(UrlSpec)

object UrlSpecRunner extends ConsoleRunner(UrlSpec)

object UrlSpec extends Specification {

  "Url" should {

    "apply JSON" in {
      val url = Url(new ObjectMapper().readTree(json))

      url.url         mustEqual "http://bit.ly/libraryman"
      url.expandedUrl mustEqual None
      url.indices     mustEqual "[78,102]"
    }
  }

  val json = """{ "expanded_url": null, "url": "http://bit.ly/libraryman", "indices": [78, 102] }"""
}
