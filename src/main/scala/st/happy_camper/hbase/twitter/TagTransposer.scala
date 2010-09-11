package st.happy_camper.hbase.twitter

import mapreduce.TagTransposeMapper

import _root_.java.util.Date

import _root_.org.apache.hadoop.hbase.HBaseConfiguration
import _root_.org.apache.hadoop.hbase.client.{ HTable, Get, Scan, Put }
import _root_.org.apache.hadoop.hbase.io.ImmutableBytesWritable
import _root_.org.apache.hadoop.hbase.mapreduce.{ HRegionPartitioner, IdentityTableReducer, TableMapReduceUtil }
import _root_.org.apache.hadoop.hbase.util.Bytes

import _root_.org.apache.hadoop.fs.Path
import _root_.org.apache.hadoop.mapreduce.Job
import _root_.org.apache.hadoop.mapreduce.lib.output.FileOutputFormat
import _root_.org.apache.hadoop.util.GenericOptionsParser

object TagTransposer {

  def main(args: Array[String]) {
    val conf = new HBaseConfiguration
    val otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs

    new HTable(conf, "configuration").open {
      configuration => {
        val limit = try {
          Bytes.toLong(configuration.get(new Get("TagTransposer").addColumn("property", "limit")).value)
        }
        catch {
          case _ => 0L
        }

        val job = new Job(conf, "Tag Transposer")
        job.setJarByClass(getClass)

        TableMapReduceUtil.setNumReduceTasks("tagtrend", job)

        val now = new Date().getTime
        val scan = new Scan().addFamily("status").setTimeRange(limit, now).setMaxVersions()
        TableMapReduceUtil.initTableMapperJob("twitter", scan,
                                              classOf[TagTransposeMapper], classOf[ImmutableBytesWritable], classOf[Put], job)
        TableMapReduceUtil.initTableReducerJob("tagtrend",
                                               classOf[IdentityTableReducer], job,
                                               classOf[HRegionPartitioner[ImmutableBytesWritable, Put]])

        if(job.waitForCompletion(true)) {
          configuration.put(new Put("TagTransposer").add("property", "limit", now))
          System.exit(0)
        }
        else {
          System.exit(1)
        }
      }
    }
  }
}
