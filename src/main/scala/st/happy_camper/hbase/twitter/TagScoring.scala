package st.happy_camper.hbase.twitter

import mapreduce.TagScoringMapper

import _root_.java.text.SimpleDateFormat
import _root_.java.util.concurrent.TimeUnit.{ DAYS, HOURS }

import _root_.org.apache.hadoop.hbase.HBaseConfiguration
import _root_.org.apache.hadoop.hbase.client.{ HTable, Get, Scan, Put }
import _root_.org.apache.hadoop.hbase.io.ImmutableBytesWritable
import _root_.org.apache.hadoop.hbase.mapreduce.{ HRegionPartitioner, IdentityTableReducer, TableMapReduceUtil }
import _root_.org.apache.hadoop.hbase.util.Bytes

import _root_.org.apache.hadoop.mapreduce.Job
import _root_.org.apache.hadoop.util.GenericOptionsParser

object TagScoring {

  val RangeDays = 7

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
        now - ( now % HOURS.toMillis(1) )
      }
    }

    if(yet(conf, lang, dateFormat.format(target)) && limit(conf) >= target) {
      conf.set("lang", lang)
      conf.setLong("target", target)
      conf.setInt("rangeDays", RangeDays)

      val job = new Job(conf, "Tag Scoring")
      job.setJarByClass(getClass)

      TableMapReduceUtil.setNumReduceTasks("tagtrend", job)
      TableMapReduceUtil.initTableMapperJob("tagtrend",
                                            new Scan().addFamily("timeline_" + lang).setTimeRange(target - DAYS.toMillis(RangeDays), target).setMaxVersions(),
                                            classOf[TagScoringMapper], classOf[ImmutableBytesWritable], classOf[Put], job)
      TableMapReduceUtil.initTableReducerJob("tagtrend",
                                             classOf[IdentityTableReducer], job,
                                             classOf[HRegionPartitioner[ImmutableBytesWritable, Put]])

      System.exit(if(job.waitForCompletion(true)) 0 else 1)
    }
  }

  def dateFormat = new SimpleDateFormat("yyyyMMddHH")

  private def yet(conf: HBaseConfiguration, lang: String, target: String) = new HTable(conf, "languages").open {
    case languages: HTable => {
      languages.get(new Get("TagScoring").addColumn(lang, target)).isEmpty
    }
  }

  private def limit(conf: HBaseConfiguration) = new HTable(conf, "configuration").open {
    case configuration: HTable => {
      try {
        Bytes.toLong(configuration.get(new Get("TagTransposer").addColumn("property", "limit")).value)
      }
      catch {
        case _ => 0L
      }
    }
  }

}
