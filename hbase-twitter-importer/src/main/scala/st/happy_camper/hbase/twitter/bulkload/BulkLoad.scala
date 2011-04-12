package st.happy_camper.hbase.twitter.bulkload

import mapreduce._

import _root_.org.apache.hadoop.conf.{ Configuration, Configured }
import _root_.org.apache.hadoop.fs.Path
import _root_.org.apache.hadoop.mapreduce.Job
import _root_.org.apache.hadoop.mapreduce.lib.input.{ FileInputFormat, TextInputFormat }
import _root_.org.apache.hadoop.mapreduce.lib.output.FileOutputFormat
import _root_.org.apache.hadoop.util.{ Tool, ToolRunner }

import _root_.org.apache.hadoop.hbase.HBaseConfiguration
import _root_.org.apache.hadoop.hbase.client.{ HTable, Put }
import _root_.org.apache.hadoop.hbase.io.ImmutableBytesWritable
import _root_.org.apache.hadoop.hbase.mapreduce.{ TableMapReduceUtil, HRegionPartitioner }

class BulkLoad(conf: Configuration = HBaseConfiguration.create) extends Configured(conf) with Tool {

  val TableName = "timeline"

  override def run(args: Array[String]) : Int = {
    val input = args(0)

    val job = new Job(getConf, "BulkLoader: " + input)
    job.setJarByClass(getClass)

    job.setInputFormatClass(classOf[TextInputFormat])
    FileInputFormat.setInputPaths(job, new Path(input))

    job.setMapperClass(classOf[BulkLoadMapper])
    job.setMapOutputKeyClass(classOf[ImmutableBytesWritable])
    job.setMapOutputValueClass(classOf[Put])

    TableMapReduceUtil.setNumReduceTasks(TableName, job)
    TableMapReduceUtil.initTableReducerJob(TableName, null, job, classOf[HRegionPartitioner[_, _]])

    if(job.waitForCompletion(true)) 0 else 1
  }
}

object BulkLoad {

  def main(args: Array[String]) {
    exit(ToolRunner.run(new BulkLoad, args))
  }
}
