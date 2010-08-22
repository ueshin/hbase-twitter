package st.happy_camper.hbase.twitter
package mapreduce

import io.StatusWritable

import _root_.scala.collection.JavaConversions._

import _root_.org.apache.hadoop.hbase.client.Result
import _root_.org.apache.hadoop.hbase.io.ImmutableBytesWritable
import _root_.org.apache.hadoop.hbase.mapreduce.TableMapper

import _root_.org.apache.hadoop.io.{ Text, LongWritable }

class SourceCountMapper extends TableMapper[Text, LongWritable] with Mappers[ImmutableBytesWritable, Result, Text, LongWritable] {

  private val one = new LongWritable(1L)

  override def map(key: ImmutableBytesWritable, value: Result, context: Context) {
    for((statusKey, StatusWritable(status)) <- value.getFamilyMap("status")) {
      context.write(new Text(status.source), one)
    }
  }
}
