package st.happy_camper.hbase.twitter

import _root_.scala.collection.JavaConversions._

import _root_.org.apache.hadoop.hbase.HBaseConfiguration
import _root_.org.apache.hadoop.hbase.client.{ Scan, Result }
import _root_.org.apache.hadoop.hbase.io.ImmutableBytesWritable
import _root_.org.apache.hadoop.hbase.mapreduce.{ TableMapper, TableMapReduceUtil }
import _root_.org.apache.hadoop.hbase.util.{ Bytes, Writables }

import _root_.org.apache.hadoop.fs.Path
import _root_.org.apache.hadoop.io.{ Text, LongWritable }
import _root_.org.apache.hadoop.mapreduce.{ Job, Mapper, Reducer }
import _root_.org.apache.hadoop.mapreduce.lib.output.FileOutputFormat
import _root_.org.apache.hadoop.util.GenericOptionsParser

object LangCounter {

  private implicit def stringToBytes(s: String) = Bytes.toBytes(s)

  class LangCountMapper extends TableMapper[Text, LongWritable] {

    private implicit def bytesToText(b: Array[Byte]) = new Text(Bytes.toString(b))

    type Context = Mapper[ImmutableBytesWritable, Result, Text, LongWritable]#Context

    private val one = new LongWritable(1L)

    override def map(key: ImmutableBytesWritable, value: Result, context: Context) {
      context.write(value.getValue("user", "lang"), one)
    }
  }

  class LangCountReducer extends Reducer[Text, LongWritable, Text, LongWritable] {

    type Context = Reducer[Text, LongWritable, Text, LongWritable]#Context

    override def reduce(key: Text, values: java.lang.Iterable[LongWritable], context: Context) {
      context.write(key, new LongWritable(values.foldLeft(0L) { _ + _.get }))
    }
  }

  def main(args: Array[String]) {
    val conf = new HBaseConfiguration
    val otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs

    val job = new Job(conf, "Lang Counter")
    job.setJarByClass(getClass)

    val scan = new Scan
    scan.addColumn("user", "lang")
    TableMapReduceUtil.initTableMapperJob("twitter", scan, classOf[LangCountMapper], classOf[Text], classOf[LongWritable], job)

    job.setCombinerClass(classOf[LangCountReducer])
    job.setReducerClass(classOf[LangCountReducer])

    FileOutputFormat.setOutputPath(job, new Path(args(0)))

    System.exit(if(job.waitForCompletion(true)) 0 else 1)
  }
}
