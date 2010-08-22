package st.happy_camper.hbase.twitter

import _root_.java.text.SimpleDateFormat
import _root_.java.util.Date

import _root_.scala.collection.JavaConversions._
import _root_.scala.math.log

import _root_.org.apache.hadoop.hbase.client.{ HTable, Scan }
import _root_.org.apache.hadoop.hbase.filter.{ SingleColumnValueFilter, CompareFilter }
import _root_.org.apache.hadoop.hbase.util.Bytes

object TweetHotTag {

  def main(args: Array[String]) {

    val table = new HTable("tagtrend")
    try {
      val target = new SimpleDateFormat("yyyyMMddHH").format(new Date(System.currentTimeMillis - 60*60*1000))

      val filter = new SingleColumnValueFilter("hourly", target, CompareFilter.CompareOp.GREATER, 0L)
      filter.setFilterIfMissing(true)

      val scan = new Scan().addFamily("hourly")
      scan.setFilter(filter)

      val scanner = table.getScanner(scan)
      try {
        val(count, (num, hours)) = scanner.foldLeft((Map.empty[String, Long], (Map.empty[String, Long], Set[String]()))) {
          case ((count, (num, hours)), result) => {
            val tag = Bytes.toString(result.getRow)
            (
              count + (tag -> Bytes.toLong(result.getValue("hourly", target))),
              result.raw.foldLeft((num, hours)) {
                case ((num, hours), keyvalue) => {
                  val hour = Bytes.toString(keyvalue.getQualifier)
                  (
                    num + (tag -> (num.getOrElse(tag, 0L) + 1L)),
                    hours + hour
                  )
                }
              }
            )
          }
        }
        val score = count.foldLeft(Map.empty[String, Double]) {
          case (score, (tag, count)) => score + (tag -> count * (log(hours.size) - log(num(tag))))
        }
        score.keys.toList.sortBy(score(_)).reverse.take(10).foreach(tag => println(tag + "\t" + score(tag)))
      }
      finally {
        scanner.close
      }
    }
    finally {
      table.close
    }
  }
}
