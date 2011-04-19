package st.happy_camper.hbase.twitter.entity

import _root_.org.specs._
import _root_.org.specs.runner.{ ConsoleRunner, JUnit4 }

import _root_.org.codehaus.jackson.map.ObjectMapper

class DeleteTest extends JUnit4(DeleteSpec)

object DeleteSpecRunner extends ConsoleRunner(DeleteSpec)

object DeleteSpec extends Specification {

  "Delete" should {

    "apply JSON" in {
      val delete = Delete(new ObjectMapper().readTree(json))

      delete.id     mustEqual 36227315155345408L
      delete.userId mustEqual 229678665L
    }
  }

  val json = """{"delete":{"status":{"user_id_str":"229678665","id_str":"36227315155345408","id":36227315155345408,"user_id":229678665}}}"""
}
