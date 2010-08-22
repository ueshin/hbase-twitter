package st.happy_camper.hbase.twitter
package mapreduce

import _root_.scala.collection.JavaConversions._

import _root_.org.apache.hadoop.mapreduce.Reducer

trait Reducers[KEYIN, VALUEIN, KEYOUT, VALUEOUT] extends Reducer[KEYIN, VALUEIN, KEYOUT, VALUEOUT] {

  type Context = Reducer[KEYIN, VALUEIN, KEYOUT, VALUEOUT]#Context

  override def reduce(key: KEYIN, values: java.lang.Iterable[VALUEIN], context: Context) {
    reduce(key, asIterable(values), context)
  }

  def reduce(key: KEYIN, values: Iterable[VALUEIN], context: Context)
}
