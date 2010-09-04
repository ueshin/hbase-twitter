package st.happy_camper.hbase.twitter

import io.ScoreWritable

import _root_.java.util.concurrent.TimeUnit.HOURS

import _root_.scala.collection.JavaConversions._
import _root_.scala.math.log

import _root_.org.apache.hadoop.hbase.HBaseConfiguration
import _root_.org.apache.hadoop.hbase.client.{ HTable, Get, Scan, ResultScanner }
import _root_.org.apache.hadoop.hbase.filter.{ SingleColumnValueFilter, CompareFilter }
import _root_.org.apache.hadoop.hbase.util.Bytes

import _root_.org.apache.hadoop.util.GenericOptionsParser

object TweetHotTag {

  def main(args: Array[String]) {
    val conf = new HBaseConfiguration
    val otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs

    val lang = otherArgs(0)

    val target = try {
      TagScoring.dateFormat.parse(otherArgs(1)).getTime
    }
    catch {
      case e: ArrayIndexOutOfBoundsException => {
        val now = System.currentTimeMillis
        now - ( now % HOURS.toMillis(1) )
      }
    }

    new HTable(conf, "tagtrend").open {
      case tagtrend: HTable => {
        tagtrend.getScanner(new Scan().addColumn("score_" + lang, TagScoring.dateFormat.format(target))).open {
          case scanner: ResultScanner => {
            val score = scanner.map {
              result => {
                val tag = Bytes.toString(result.getRow)
                val ScoreWritable(score) = result.value
                (tag -> score)
              }
            }.toMap
            score.keys.toList.sortBy(score(_).score).reverse.take(100).foreach(tag => println(tag + "\t" + score(tag).score + " : count = " + score(tag).count))
          }
        }
      }
    }
  }
}
