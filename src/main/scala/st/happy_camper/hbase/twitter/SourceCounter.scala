package st.happy_camper.hbase.twitter

import mapreduce.SourceCountMapper

import _root_.org.apache.hadoop.hbase.HBaseConfiguration
import _root_.org.apache.hadoop.hbase.client.Scan
import _root_.org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil

import _root_.org.apache.hadoop.fs.Path
import _root_.org.apache.hadoop.io.{ Text, LongWritable }
import _root_.org.apache.hadoop.mapreduce.Job
import _root_.org.apache.hadoop.mapreduce.lib.output.FileOutputFormat
import _root_.org.apache.hadoop.mapreduce.lib.reduce.LongSumReducer
import _root_.org.apache.hadoop.util.GenericOptionsParser

object SourceCounter {

  def main(args: Array[String]) {
    val conf = new HBaseConfiguration
    val otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs

    val job = new Job(conf, "Source Counter")
    job.setJarByClass(getClass)

    TableMapReduceUtil.initTableMapperJob("twitter", new Scan().addFamily("status"),
                                          classOf[SourceCountMapper], classOf[Text], classOf[LongWritable], job)

    job.setCombinerClass(classOf[LongSumReducer[Text]])
    job.setReducerClass(classOf[LongSumReducer[Text]])

    FileOutputFormat.setOutputPath(job, new Path(otherArgs(0)))

    System.exit(if(job.waitForCompletion(true)) 0 else 1)
  }

}
