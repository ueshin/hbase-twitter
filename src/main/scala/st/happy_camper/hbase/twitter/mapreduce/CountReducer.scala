package st.happy_camper.hbase.twitter.mapreduce

import _root_.scala.collection.JavaConversions._

import _root_.org.apache.hadoop.io.{ Text, LongWritable }
import _root_.org.apache.hadoop.mapreduce.Reducer

class CountReducer extends Reducer[Text, LongWritable, Text, LongWritable] {

  type Context = Reducer[Text, LongWritable, Text, LongWritable]#Context

  override def reduce(key: Text, values: java.lang.Iterable[LongWritable], context: Context) {
    context.write(key, new LongWritable(values.foldLeft(0L) { _ + _.get }))
  }
}
