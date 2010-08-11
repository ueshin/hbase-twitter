package st.happy_camper.hbase.twitter.entity

import _root_.scala.xml.Node

class Delete(val statusId: Long, val userId: Long) {
  val statusKey = Status.createKey(statusId)
  val userKey = User.createKey(userId)
}

object Delete {
  def apply(node: Node) : Delete = {
    new Delete(
      (node \\ "id").text.toLong,
      (node \\ "user_id").text.toLong
    )
  }

  def unapply(node: Node) : Option[Delete] = {
    try {
      Some(Delete(node))
    }
    catch {
      case _ => None
    }
  }
}
