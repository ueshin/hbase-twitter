package st.happy_camper.hbase.twitter

import mapreduce.TagScoringMapper

import _root_.java.text.SimpleDateFormat

import _root_.org.apache.hadoop.hbase.HBaseConfiguration
import _root_.org.apache.hadoop.hbase.client.{ HTable, Get, Scan, Put }
import _root_.org.apache.hadoop.hbase.io.ImmutableBytesWritable
import _root_.org.apache.hadoop.hbase.mapreduce.{ HRegionPartitioner, IdentityTableReducer, TableMapReduceUtil }
import _root_.org.apache.hadoop.hbase.util.Bytes

import _root_.org.apache.hadoop.mapreduce.Job
import _root_.org.apache.hadoop.util.GenericOptionsParser

object TagScoring {

  private val HourToMillis = 60*60*1000L
  private val DayToMillis = 24*HourToMillis

  def main(args: Array[String]) {
    val conf = new HBaseConfiguration
    val otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs

    val lang = otherArgs(0)

    val target = try {
      dateFormat.parse(otherArgs(1)).getTime
    }
    catch {
      case e: ArrayIndexOutOfBoundsException => {
        val now = System.currentTimeMillis
        now - ( now % HourToMillis )
      }
    }

    if(yet(conf, lang, dateFormat.format(target)) && limit(conf) >= target) {
      conf.set("lang", lang)
      conf.setLong("target", target)

      val job = new Job(conf, "Tag Scoring " + lang)
      job.setJarByClass(getClass)

      TableMapReduceUtil.setNumReduceTasks("tagtrend", job)
      TableMapReduceUtil.initTableMapperJob("tagtrend",
                                            new Scan().addFamily("timeline_" + lang).setTimeRange(target - DayToMillis, target).setMaxVersions(),
                                            classOf[TagScoringMapper], classOf[ImmutableBytesWritable], classOf[Put], job)
      TableMapReduceUtil.initTableReducerJob("tagtrend",
                                             classOf[IdentityTableReducer], job,
                                             classOf[HRegionPartitioner[ImmutableBytesWritable, Put]])

      System.exit(if(job.waitForCompletion(true)) 0 else 1)
    }
  }

  def dateFormat = new SimpleDateFormat("yyyyMMddHH")

  private def yet(conf: HBaseConfiguration, lang: String, target: String) = new HTable(conf, "languages").open {
    languages => {
      languages.get(new Get("TagScoring").addColumn(lang, target)).isEmpty
    }
  }

  private def limit(conf: HBaseConfiguration) = new HTable(conf, "configuration").open {
    configuration => {
      try {
        Bytes.toLong(configuration.get(new Get("TagTransposer").addColumn("property", "limit")).value)
      }
      catch {
        case _ => 0L
      }
    }
  }

}
