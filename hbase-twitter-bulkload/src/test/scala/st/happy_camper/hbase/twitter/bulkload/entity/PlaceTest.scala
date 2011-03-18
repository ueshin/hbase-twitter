package st.happy_camper.hbase.twitter.bulkload.entity

import _root_.org.specs._
import _root_.org.specs.runner.{ ConsoleRunner, JUnit4 }

import _root_.org.codehaus.jackson.map.ObjectMapper

class PlaceTest extends JUnit4(PlaceSpec)

object PlaceSpecRunner extends ConsoleRunner(PlaceSpec)

object PlaceSpec extends Specification {

  "Place" should {

    "apply JSON" in {
      val place = Place(new ObjectMapper().readTree(json))

      place.id                  mustEqual "5a110d312052166f"
      place.name                mustEqual "San Francisco"
      place.fullName            mustEqual "San Francisco, CA"
      place.url                 mustEqual "http://api.twitter.com/1/geo/id/5a110d312052166f.json"
      place.placeType           mustEqual "city"
      place.country             mustEqual "The United States of America"
      place.countryCode         mustEqual "US"
      place.boundingBox.get     mustEqual "[[[-122.51368188,37.70813196],[-122.35845384,37.70813196],[-122.35845384,37.83245301],[-122.51368188,37.83245301]]]"
      place.boundingBoxType.get mustEqual "Polygon"
      place.attributes          mustEqual "{}"
    }
  }

  val json = """{
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
    }"""
}
