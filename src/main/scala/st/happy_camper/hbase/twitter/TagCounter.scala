package st.happy_camper.hbase.twitter

import _root_.st.happy_camper.hbase.twitter.io.StatusWritable
import _root_.st.happy_camper.hbase.twitter.mapreduce.CountReducer

import _root_.scala.collection.JavaConversions._

import _root_.org.apache.hadoop.hbase.HBaseConfiguration
import _root_.org.apache.hadoop.hbase.client.{ Scan, Result }
import _root_.org.apache.hadoop.hbase.io.ImmutableBytesWritable
import _root_.org.apache.hadoop.hbase.mapreduce.{ TableMapper, TableMapReduceUtil }
import _root_.org.apache.hadoop.hbase.util.Bytes

import _root_.org.apache.hadoop.fs.Path
import _root_.org.apache.hadoop.io.{ Text, LongWritable }
import _root_.org.apache.hadoop.mapreduce.{ Job, Mapper }
import _root_.org.apache.hadoop.mapreduce.lib.output.FileOutputFormat
import _root_.org.apache.hadoop.util.GenericOptionsParser

object TagCounter {

  private implicit def stringToBytes(s: String) = Bytes.toBytes(s)

  class TagCountMapper extends TableMapper[Text, LongWritable] {

    type Context = Mapper[ImmutableBytesWritable, Result, Text, LongWritable]#Context

    private val hashTagRegexp = """#[0-9a-zA-Z_]+""".r

    private val one = new LongWritable(1L)

    override def map(key: ImmutableBytesWritable, value: Result, context: Context) {
      for((statusKey, StatusWritable(status)) <- value.getFamilyMap("status") if status.user.lang == "ja") {
        hashTagRegexp findAllIn(status.text) foreach {
          tag => context.write(new Text(tag.toLowerCase), one)
        }
      }
    }
  }

  def main(args: Array[String]) {
    val conf = new HBaseConfiguration
    val otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs

    val job = new Job(conf, "Tag Counter")
    job.setJarByClass(getClass)

    TableMapReduceUtil.initTableMapperJob("twitter", new Scan().addFamily("status"), classOf[TagCountMapper], classOf[Text], classOf[LongWritable], job)

    job.setCombinerClass(classOf[CountReducer])
    job.setReducerClass(classOf[CountReducer])

    FileOutputFormat.setOutputPath(job, new Path(otherArgs(0)))

    System.exit(if(job.waitForCompletion(true)) 0 else 1)
  }

}
