package st.happy_camper.hbase.twitter
package mapreduce

import entity.Score
import io.ScoreWritable

import _root_.java.text.SimpleDateFormat
import _root_.java.util.Date
import _root_.java.util.concurrent.TimeUnit.{ DAYS, HOURS }

import _root_.scala.math.{ pow, log }
import _root_.scala.collection.JavaConversions._
import _root_.scala.collection.immutable.SortedMap

import _root_.org.apache.hadoop.hbase.HBaseConfiguration
import _root_.org.apache.hadoop.hbase.client.{ HTable, Result, Put }
import _root_.org.apache.hadoop.hbase.io.ImmutableBytesWritable
import _root_.org.apache.hadoop.hbase.mapreduce.TableMapper

class TagScoringMapper extends TableMapper[ImmutableBytesWritable, Put] with Mappers[ImmutableBytesWritable, Result, ImmutableBytesWritable, Put] {

  var lang = ""
  var target = 0L
  var rangeDays = 0
  var languages: HTable = null

  override def setup(context: Context) {
    val conf = new HBaseConfiguration(context.getConfiguration)
    lang = conf.get("lang", "")
    target = conf.getLong("target", 0L)
    rangeDays = conf.getInt("rangeDays", 0)
    languages = new HTable(conf, "languages")
  }

  override def map(key: ImmutableBytesWritable, value: Result, context: Context) {
    val count = value.raw.foldLeft(SortedMap.empty[Long, Long]) {
      case(count, keyvalue) => {
        val hour = (target - keyvalue.getTimestamp - 1L) / HOURS.toMillis(1)
        count + (hour -> (count.getOrElse(hour, 0L) + 1L))
      }
    }
    val score = count.foldLeft(0.0) {
      case (score, (hour, count)) => {
        score + ( count * pow(2, - hour / 3.0) )
      }
    } * (1 - ( log(count.size) / log(DAYS.toHours(rangeDays)) ) )
    val yyyymmddhh = TagScoring.dateFormat.format(target)
    context.write(key, new Put(key.get).add("score_" + lang, yyyymmddhh, ScoreWritable(new Score(score, count))))
    languages.incrementColumnValue("TagScoring", lang, yyyymmddhh, count.getOrElse(0, 0L))
  }

  override def cleanup(context: Context) {
    languages.close
  }
}
