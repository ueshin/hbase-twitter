package st.happy_camper.hbase.twitter.handler

import _root_.st.happy_camper.hbase.twitter.entity.{ Status, User }
import _root_.st.happy_camper.hbase.twitter.io.StatusWritable

import _root_.scala.xml.XML

import _root_.org.apache.hadoop.hbase.client.{ HTable, Put }
import _root_.org.apache.hadoop.hbase.util.{ Bytes, Writables }

class HTableHandler extends (String => Unit) {

  val table = new HTable("twitter")
  table.setAutoFlush(false)

  def apply(xml: String) {
    XML.loadString(xml) match {
      case s @ <status>{_ @ _*}</status> => {
        val status = Status(s)
        val put = new Put(Bytes.toBytes(status.user.get.id))
        put.add(Bytes.toBytes("status"), Bytes.toBytes(status.id), Writables.getBytes(new StatusWritable(status)))
        table.put(put)
      }
      case _ => println(xml)
    }
  }

  def close {
    table.close
  }
}
