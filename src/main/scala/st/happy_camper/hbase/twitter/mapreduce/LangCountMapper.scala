package st.happy_camper.hbase.twitter
package mapreduce

import _root_.org.apache.hadoop.hbase.client.Result
import _root_.org.apache.hadoop.hbase.io.ImmutableBytesWritable
import _root_.org.apache.hadoop.hbase.mapreduce.{ TableMapper, TableMapReduceUtil }
import _root_.org.apache.hadoop.hbase.util.Bytes

import _root_.org.apache.hadoop.io.{ Text, LongWritable }

class LangCountMapper extends TableMapper[Text, LongWritable] with Mappers[ImmutableBytesWritable, Result, Text, LongWritable] {

  private val one = new LongWritable(1L)

  override def map(key: ImmutableBytesWritable, value: Result, context: Context) {
    context.write(new Text(Bytes.toString(value.getValue("user", "lang"))), one)
  }
}
