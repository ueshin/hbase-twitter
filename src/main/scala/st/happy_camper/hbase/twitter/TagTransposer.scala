package st.happy_camper.hbase.twitter

import _root_.st.happy_camper.hbase.twitter.io.StatusWritable

import _root_.scala.collection.JavaConversions._

import _root_.org.apache.hadoop.hbase.HBaseConfiguration
import _root_.org.apache.hadoop.hbase.client.{ Scan, Result, Put }
import _root_.org.apache.hadoop.hbase.io.ImmutableBytesWritable
import _root_.org.apache.hadoop.hbase.mapreduce.{ TableMapper, IdentityTableReducer, TableMapReduceUtil }
import _root_.org.apache.hadoop.hbase.util.Bytes

import _root_.org.apache.hadoop.fs.Path
import _root_.org.apache.hadoop.mapreduce.{ Job, Mapper }
import _root_.org.apache.hadoop.mapreduce.lib.output.FileOutputFormat
import _root_.org.apache.hadoop.util.GenericOptionsParser

object TagTransposer {

  private implicit def stringToBytes(s: String) = Bytes.toBytes(s)

  class TagTransposeMapper extends TableMapper[ImmutableBytesWritable, Put] {

    type Context = Mapper[ImmutableBytesWritable, Result, ImmutableBytesWritable, Put]#Context

    private val HashTagRegexp = """#[0-9a-zA-Z_]+""".r

    override def map(key: ImmutableBytesWritable, value: Result, context: Context) {
      val userKey = Bytes.toString(key.get)
      value.raw foreach {
        keyvalue => {
          val statusKey = Bytes.toString(keyvalue.getQualifier)
          val timestamp = keyvalue.getTimestamp
          keyvalue.getValue match {
            case StatusWritable(status) => {
              HashTagRegexp findAllIn(status.text) foreach {
                tag => {
                  context.write(key, new Put(tag.toLowerCase).add("timeline", userKey, timestamp, statusKey))
                }
              }
            }
            case _ =>
          }
        }
      }
    }
  }

  def main(args: Array[String]) {
    val conf = new HBaseConfiguration
    val otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs

    val job = new Job(conf, "Tag Transposer")
    job.setJarByClass(getClass)

    val offset = (System.currentTimeMillis / (60*60*1000L))
    val scan = new Scan().addFamily("status").setTimeRange((offset - 1) * (60*60*1000L), offset * (60*60*1000L))
    TableMapReduceUtil.initTableMapperJob("twitter", scan, classOf[TagTransposeMapper], classOf[ImmutableBytesWritable], classOf[Put], job)
    TableMapReduceUtil.initTableReducerJob("tagtrend", classOf[IdentityTableReducer], job)

    System.exit(if(job.waitForCompletion(true)) 0 else 1)
  }
}
