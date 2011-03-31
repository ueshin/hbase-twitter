package st.happy_camper.hbase.twitter.bulkload.entity

import _root_.org.specs._
import _root_.org.specs.runner.{ ConsoleRunner, JUnit4 }

import _root_.org.codehaus.jackson.map.ObjectMapper

class FlumeEventTest extends JUnit4(FlumeEventSpec)

object FlumeEventSpecRunner extends ConsoleRunner(FlumeEventSpec)

object FlumeEventSpec extends Specification {

  "FlumeEvent" should {

    "apply JSON" in {
      val flumeEvent = FlumeEvent(new ObjectMapper().readTree(json))

      flumeEvent.body      mustEqual "event body"
      flumeEvent.timestamp mustEqual 1299160307737L
      flumeEvent.pri       mustEqual "INFO"
//    flumeEvent.nanos     mustEqual 1299160347119674000L
      flumeEvent.host      mustEqual "127.0.0.1"
      flumeEvent.fields    mustEqual Map(
        "AckTag"      -> "log....seq",
        "AckType"     -> "msg",
        "AckChecksum" -> "\\u0000",
        "service"     -> "Twitter",
        "rolltag"     -> "log....seq"
      )
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
