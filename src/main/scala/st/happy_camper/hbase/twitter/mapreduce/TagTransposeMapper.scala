package st.happy_camper.hbase.twitter
package mapreduce

import io.{ StatusWritable, UserWritable }

import _root_.org.apache.hadoop.hbase.client.{ Result, Put }
import _root_.org.apache.hadoop.hbase.io.ImmutableBytesWritable
import _root_.org.apache.hadoop.hbase.mapreduce.TableMapper

class TagTransposeMapper extends TableMapper[ImmutableBytesWritable, Put] with Mappers[ImmutableBytesWritable, Result, ImmutableBytesWritable, Put] {

  private val HashTagRegexp = """#[0-9a-zA-Z_]+""".r

  override def map(key: ImmutableBytesWritable, value: Result, context: Context) {
    for(keyvalue <- value.raw) {
      keyvalue.getValue match {
        case StatusWritable(status) => {
          HashTagRegexp findAllIn(status.text) foreach {
            tag => {
              context.write(new ImmutableBytesWritable(tag.toLowerCase),
                            new Put(tag.toLowerCase).add("timeline", status.key, keyvalue.getTimestamp, UserWritable(status.user)))
            }
          }
        }
        case _ =>
      }
    }
  }
}
