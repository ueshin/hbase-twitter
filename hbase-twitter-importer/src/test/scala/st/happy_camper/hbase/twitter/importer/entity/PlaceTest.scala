/*
 * Copyright 2010-2012 Happy-Camper Street.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package st.happy_camper.hbase.twitter.importer.entity

import org.codehaus.jackson.map.ObjectMapper
import org.junit.runner.RunWith
import org.specs2.mutable.Specification
import org.specs2.runner.JUnitRunner

@RunWith(classOf[JUnitRunner])
object PlaceTest extends PlaceSpec

class PlaceSpec extends Specification {

  "Place" should {

    "apply JSON" in {
      val place = Place(new ObjectMapper().readTree(json))

      place.id mustEqual "5a110d312052166f"
      place.name mustEqual "San Francisco"
      place.fullName mustEqual "San Francisco, CA"
      place.url mustEqual "http://api.twitter.com/1/geo/id/5a110d312052166f.json"
      place.placeType mustEqual "city"
      place.country mustEqual "The United States of America"
      place.countryCode mustEqual "US"
      place.boundingBox.get mustEqual "[[[-122.51368188,37.70813196],[-122.35845384,37.70813196],[-122.35845384,37.83245301],[-122.51368188,37.83245301]]]"
      place.boundingBoxType.get mustEqual "Polygon"
      place.attributes mustEqual "{}"
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
