package st.happy_camper.hbase.twitter
package mapreduce

import _root_.org.apache.hadoop.mapreduce.Mapper

trait Mappers[KEYIN, VALUEIN, KEYOUT, VALUEOUT] extends Mapper[KEYIN, VALUEIN, KEYOUT, VALUEOUT] {

  type Context = Mapper[KEYIN, VALUEIN, KEYOUT, VALUEOUT]#Context
}
