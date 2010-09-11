package st.happy_camper.hbase.twitter
package io

import entity.Status

import _root_.java.io.{ DataInput, DataOutput }
import _root_.java.util.Date

import _root_.org.apache.hadoop.hbase.util.Writables

import _root_.org.apache.hadoop.io.{ Text, Writable, WritableUtils }

private class StatusWritable(var status: Status = null) extends Writable {

  def write(out: DataOutput) {
    StatusWritable.write(out, status)
  }

  def readFields(in: DataInput) {
    status = StatusWritable.read(in)
  }
}

object StatusWritable {

  def apply(status: Status) = {
    Writables.getBytes(new StatusWritable(status))
  }

  def unapply(b: Array[Byte]) = {
    try {
      val writable = new StatusWritable
      Writables.getWritable(b, writable)
      Option(writable.status)
    }
    catch {
      case _ => None
    }
  }

  def write(out: DataOutput, status: Status) {
    WritableUtils.writeVLong(out, status.createdAt.getTime)
    WritableUtils.writeVLong(out, status.id)
    Text.writeString(out, status.text)
    Text.writeString(out, status.source)
    out.writeBoolean(status.truncated)
    WritableUtils.writeVLong(out, status.inReplyToStatusId)
    WritableUtils.writeVLong(out, status.inReplyToUserId)
    out.writeBoolean(status.favorited)
    Text.writeString(out, status.inReplyToScreenName)
    UserWritable.write(out, status.user)
  }

  def read(in: DataInput) = {
    new Status(
      new Date(WritableUtils.readVLong(in)),
      WritableUtils.readVLong(in),
      Text.readString(in),
      Text.readString(in),
      in.readBoolean(),
      WritableUtils.readVLong(in),
      WritableUtils.readVLong(in),
      in.readBoolean(),
      Text.readString(in),
      UserWritable.read(in)
    )
  }
}
