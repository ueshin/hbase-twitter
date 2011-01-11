package st.happy_camper.hbase.twitter.entity

import _root_.dispatch.json._
import _root_.sjson.json._
import _root_.sjson.json.JsonSerialization._

class Delete(val statusId: Long, val userId: Long) {
  val statusKey = Status.createKey(statusId)
  val userKey = User.createKey(userId)
}

object Delete {

  private object DeleteProtocol extends DefaultProtocol {

    implicit object DeleteReads extends Reads[Delete] {
      def reads(json: JsValue) = json match {
        case JsObject(m) => m(JsString("delete")) match {
          case JsObject(m) => m(JsString("status")) match {
            case JsObject(m) => new Delete(
              fromjson[Long](m(JsString("id"))),
              fromjson[Long](m(JsString("user_id")))
            )
            case _ => throw new RuntimeException("Delete expected")
          }
          case _ => throw new RuntimeException("Delete expected")
        }
        case _ => throw new RuntimeException("Delete expected")
      }
    }
  }

  import DeleteProtocol._

  def apply(json: JsValue) : Delete = {
    fromjson[Delete](json)
  }

  def unapply(json: JsValue) : Option[Delete] = {
    try {
      Some(Delete(json))
    }
    catch {
      case _ => None
    }
  }
}
