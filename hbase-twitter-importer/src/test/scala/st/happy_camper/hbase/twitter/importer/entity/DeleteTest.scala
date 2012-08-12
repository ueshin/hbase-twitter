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
object DeleteSepecTest extends DeleteSpec

class DeleteSpec extends Specification {

  "Delete" should {

    "apply JSON" in {
      val delete = Delete(new ObjectMapper().readTree(json))

      delete.id mustEqual 36227315155345408L
      delete.userId mustEqual 229678665L
    }
  }

  val json = """{"delete":{"status":{"user_id_str":"229678665","id_str":"36227315155345408","id":36227315155345408,"user_id":229678665}}}"""
}
