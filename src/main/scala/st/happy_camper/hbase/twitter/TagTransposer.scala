package st.happy_camper.hbase.twitter

import mapreduce.{ TagTransposeMapper, TagTransposeReducer }

import _root_.org.apache.hadoop.hbase.HBaseConfiguration
import _root_.org.apache.hadoop.hbase.client.{ Scan, Put }
import _root_.org.apache.hadoop.hbase.io.ImmutableBytesWritable
import _root_.org.apache.hadoop.hbase.mapreduce.{ TableMapReduceUtil, HRegionPartitioner }

import _root_.org.apache.hadoop.fs.Path
import _root_.org.apache.hadoop.mapreduce.Job
import _root_.org.apache.hadoop.mapreduce.lib.output.FileOutputFormat
import _root_.org.apache.hadoop.util.GenericOptionsParser

object TagTransposer {

  def main(args: Array[String]) {
    val conf = new HBaseConfiguration
    val otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs

    val job = new Job(conf, "Tag Transposer")
    job.setJarByClass(getClass)

    TableMapReduceUtil.setNumReduceTasks("tagtrend", job)

    val offset = (System.currentTimeMillis / (60*60*1000L))
    val scan = new Scan().addFamily("status").setTimeRange((offset - 1) * (60*60*1000L), offset * (60*60*1000L))
    TableMapReduceUtil.initTableMapperJob("twitter", scan,
                                          classOf[TagTransposeMapper], classOf[ImmutableBytesWritable], classOf[Put], job)
    TableMapReduceUtil.initTableReducerJob("tagtrend",
                                           classOf[TagTransposeReducer], job,
                                           classOf[HRegionPartitioner[ImmutableBytesWritable, Put]])

    System.exit(if(job.waitForCompletion(true)) 0 else 1)
  }
}
