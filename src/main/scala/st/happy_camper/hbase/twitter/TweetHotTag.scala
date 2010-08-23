package st.happy_camper.hbase.twitter

import io.UserWritable

import _root_.java.text.SimpleDateFormat
import _root_.java.util.Date

import _root_.scala.collection.JavaConversions._
import _root_.scala.math.log

import _root_.org.apache.hadoop.hbase.HBaseConfiguration
import _root_.org.apache.hadoop.hbase.client.{ HTable, Get, Scan }
import _root_.org.apache.hadoop.hbase.filter.{ SingleColumnValueFilter, CompareFilter }
import _root_.org.apache.hadoop.hbase.util.Bytes

import _root_.org.apache.hadoop.util.GenericOptionsParser

object TweetHotTag {

  def main(args: Array[String]) {
    val conf = new HBaseConfiguration
    val otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs

    val configuration = new HTable(conf, "configuration")
    val limit = try {
      Bytes.toLong(configuration.get(new Get("TagTransposer").addColumn("property", "limit")).value)
    }
    catch {
      case _ => 0L
    }
    finally {
      configuration.close
    }

    val tagtrend = new HTable("tagtrend")
    try {
      val scanner = tagtrend.getScanner(new Scan().addFamily("timeline"))
      try {
        val(count, num, hours) = scanner.foldLeft((Map.empty[String, Long], Map.empty[String, Set[Long]], Set[Long]())) {
          case ((count, num, hours), result) => {
            val tag = Bytes.toString(result.getRow)
            result.raw.foldLeft((count, num, hours)) {
              case ((count, num, hours), keyvalue) => {
                keyvalue.getValue match {
                  case UserWritable(user) if user.lang == "ja" => {
                    val timestamp = keyvalue.getTimestamp
                    val hour = (limit - timestamp) / (60*60*1000L);
                    (
                      if(hour==0L) { count + (tag -> (count.getOrElse(tag, 0L) + 1L)) } else { count },
                      num + (tag -> (num.getOrElse(tag, Set.empty[Long]) + hour)),
                      hours + hour
                    )
                  }
                  case _ => (count, num, hours)
                }
              }
            }
          }
        }
        val score = count.foldLeft(Map.empty[String, Double]) {
          case (score, (tag, count)) => score + (tag -> count * (log(hours.size) - log(num(tag).size)))
        }
        score.keys.toList.sortBy(score(_)).reverse.take(10).foreach(tag => println(tag + "\t" + score(tag) + " = " + count(tag) + " * ( log(" + hours.size + ") - log(" + num(tag).size + ") ) "))
      }
      finally {
        scanner.close
      }
    }
    finally {
      tagtrend.close
    }
  }
}
