package st.happy_camper.hbase.twitter
package mapreduce

import _root_.java.text.SimpleDateFormat
import _root_.java.util.Date

import _root_.scala.collection.JavaConversions._

import _root_.org.apache.hadoop.hbase.client.{ Result, Put }
import _root_.org.apache.hadoop.hbase.io.ImmutableBytesWritable
import _root_.org.apache.hadoop.hbase.mapreduce.TableReducer
import _root_.org.apache.hadoop.hbase.util.Bytes

import _root_.org.apache.hadoop.io.Writable

class TagTransposeReducer extends TableReducer[ImmutableBytesWritable, Put, ImmutableBytesWritable] with Reducers[ImmutableBytesWritable, Put, ImmutableBytesWritable, Writable] {

  override def reduce(key: ImmutableBytesWritable, values: Iterable[Put], context: Context) {
    val hourly = values.foldLeft(Map.empty[String, Long]) {
      (hourly, value) => {
        context.write(key, value)

        value.getFamilyMap()("timeline").foldLeft(hourly) {
          (hourly, keyvalue) => {
            val hour = new SimpleDateFormat("yyyyMMddHH").format(new Date(keyvalue.getTimestamp))
            hourly + (hour -> (hourly.getOrElse(hour, 0L) + 1L))
          }
        }
      }
    }
    for((hour, count) <- hourly) {
      context.write(key, new Put(key.get).add("hourly", hour, count))
    }
  }
}
