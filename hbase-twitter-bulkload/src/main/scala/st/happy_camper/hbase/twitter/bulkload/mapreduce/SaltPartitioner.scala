package st.happy_camper.hbase.twitter.bulkload
package mapreduce

import _root_.org.apache.hadoop.mapreduce.Partitioner
import _root_.org.apache.hadoop.io.Writable
import _root_.org.apache.hadoop.hbase.io.ImmutableBytesWritable

class SaltPartitioner extends Partitioner[ImmutableBytesWritable, Writable] {

  override def getPartition(key: ImmutableBytesWritable, value: Writable, numPartitions: Int) = {
    val salt = key.get()(0)
    if(salt >= numPartitions) {
      (salt.toString.hashCode & Int.MaxValue) % numPartitions
    }
    else {
      salt
    }
  }
}
