package st.happy_camper.hbase.twitter.entity

import _root_.scala.xml.Node

class DeleteStatus(val statusId: Long, val userId: Long) {
  val statusKey = Status.createKey(statusId)
  val userKey = User.createKey(userId)
}

object DeleteStatus {
  def apply(node: Node) : DeleteStatus = {
    new DeleteStatus(
      (node \\ "id").text.toLong,
      (node \\ "user_id").text.toLong
    )
  }

  def unapply(node: Node) : Option[DeleteStatus] = {
    try {
      Some(DeleteStatus(node))
    }
    catch {
      case _ => None
    }
  }
}
