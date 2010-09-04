package st.happy_camper.hbase.twitter
package io

import entity.Score

import _root_.java.io.{ DataInput, DataOutput }

import _root_.scala.collection.immutable.SortedMap

import _root_.org.apache.hadoop.hbase.util.Writables

import _root_.org.apache.hadoop.io.{ Writable, WritableUtils }


private class ScoreWritable(var score: Score) extends Writable {

  def this() = this(null)

  def write(out: DataOutput) {
    ScoreWritable.write(out, score)
  }

  def readFields(in: DataInput) {
    score = ScoreWritable.read(in)
  }
}

object ScoreWritable {

  def apply(score: Score) = {
    Writables.getBytes(new ScoreWritable(score))
  }

  def unapply(b: Array[Byte]) = {
    try {
      val writable = new ScoreWritable
      Writables.getWritable(b, writable)
      Option(writable.score)
    }
    catch {
      case _ => None
    }
  }

  def write(out: DataOutput, score: Score) {
    out.writeDouble(score.score)
    WritableUtils.writeVInt(out, score.count.size)
    score.count.foreach {
      case (hour, count) => {
        WritableUtils.writeVLong(out, hour)
        WritableUtils.writeVLong(out, count)
      }
    }
  }

  def read(in: DataInput) = {
    new Score(
      in.readDouble,
      (1 to WritableUtils.readVInt(in)).foldLeft(SortedMap.empty[Long, Long]) {
        (m, i) => m + (WritableUtils.readVLong(in) -> WritableUtils.readVLong(in))
      }
    )
  }
}
