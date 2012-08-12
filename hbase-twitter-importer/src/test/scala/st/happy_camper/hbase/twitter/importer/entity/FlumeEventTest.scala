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
object FlumeEventTest extends FlumeEventSpec

class FlumeEventSpec extends Specification {

  "FlumeEvent" should {

    "apply JSON" in {
      val flumeEvent = FlumeEvent(new ObjectMapper().readTree(json))

      flumeEvent.body mustEqual "event body"
      flumeEvent.timestamp mustEqual 1299160307737L
      flumeEvent.pri mustEqual "INFO"
      //    flumeEvent.nanos     mustEqual 1299160347119674000L
      flumeEvent.host mustEqual "127.0.0.1"
      flumeEvent.fields mustEqual Map(
        "AckTag" -> "log....seq",
        "AckType" -> "msg",
        "AckChecksum" -> "\\u0000",
        "service" -> "Twitter",
        "rolltag" -> "log....seq")
    }
  }

  val json = """{ "body":"event body", "timestamp":1299160307737, "pri":"INFO", "nanos":1299160347119674000, "host":"127.0.0.1",
                "fields":{
                  "AckTag":"log....seq",
                  "AckType":"msg",
                  "AckChecksum":"\\u0000",
                  "service":"Twitter",
                  "rolltag":"log....seq"
                  }}"""
}
