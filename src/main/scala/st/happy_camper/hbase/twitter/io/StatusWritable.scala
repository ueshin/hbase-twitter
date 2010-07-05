package st.happy_camper.hbase.twitter.io

import _root_.st.happy_camper.hbase.twitter.entity.Status

import _root_.java.io.{ DataInput, DataOutput }
import _root_.java.util.Date

import _root_.org.apache.hadoop.io.{ Text, Writable, WritableUtils }

class StatusWritable(var status: Status) extends Writable {

  def this() = this(null)

  def write(out: DataOutput) {
    WritableUtils.writeVLong(out, status.createdAt.getTime)
    WritableUtils.writeVLong(out, status.id)
    Text.writeString(out, status.text)
    Text.writeString(out, status.source)
    out.writeBoolean(status.truncated)
    WritableUtils.writeVLong(out, status.inReplyToStatusId)
    WritableUtils.writeVLong(out, status.inReplyToUserId)
    out.writeBoolean(status.favorited)
    Text.writeString(out, status.inReplyToScreenName)
  }

  def readFields(in: DataInput) {
    status = new Status(
      new Date(WritableUtils.readVLong(in)),
      WritableUtils.readVLong(in),
      Text.readString(in),
      Text.readString(in),
      in.readBoolean(),
      WritableUtils.readVLong(in),
      WritableUtils.readVLong(in),
      in.readBoolean(),
      Text.readString(in),
      None
    )
  }
}
