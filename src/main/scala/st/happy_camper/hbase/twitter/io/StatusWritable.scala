package st.happy_camper.hbase.twitter.io

import _root_.st.happy_camper.hbase.twitter.entity.{ Status, User }

import _root_.java.io.{ DataInput, DataOutput }
import _root_.java.util.Date

import _root_.org.apache.hadoop.hbase.util.Writables

import _root_.org.apache.hadoop.io.{ Text, Writable, WritableUtils }

private class StatusWritable(var status: Status) extends Writable {

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

    WritableUtils.writeVLong(out, status.user.id)
    Text.writeString(out, status.user.name)
    Text.writeString(out, status.user.screenName)
    Text.writeString(out, status.user.location)
    Text.writeString(out, status.user.description)
    Text.writeString(out, status.user.profileImageUrl)
    Text.writeString(out, status.user.url)
    out.writeBoolean(status.user.isProtected)
    WritableUtils.writeVInt(out, status.user.followersCount)
    Text.writeString(out, status.user.profileBackgroundColor)
    Text.writeString(out, status.user.profileTextColor)
    Text.writeString(out, status.user.profileLinkColor)
    Text.writeString(out, status.user.profileSidebarFillColor)
    Text.writeString(out, status.user.profileSidebarBorderColor)
    WritableUtils.writeVInt(out, status.user.friendsCount)
    WritableUtils.writeVLong(out, status.user.createdAt.getTime)
    WritableUtils.writeVInt(out, status.user.favouritesCount)
    WritableUtils.writeVInt(out, status.user.utcOffset)
    Text.writeString(out, status.user.timeZone)
    Text.writeString(out, status.user.profileBackgroundImageUrl)
    out.writeBoolean(status.user.profileBackgroundTile)
    out.writeBoolean(status.user.geoEnabled)
    out.writeBoolean(status.user.verified)
    WritableUtils.writeVInt(out, status.user.statusesCount)
    Text.writeString(out, status.user.lang)
    out.writeBoolean(status.user.contributorsEnabled)
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
      new User(
        WritableUtils.readVLong(in),
        Text.readString(in),
        Text.readString(in),
        Text.readString(in),
        Text.readString(in),
        Text.readString(in),
        Text.readString(in),
        in.readBoolean(),
        WritableUtils.readVInt(in),
        Text.readString(in),
        Text.readString(in),
        Text.readString(in),
        Text.readString(in),
        Text.readString(in),
        WritableUtils.readVInt(in),
        new Date(WritableUtils.readVLong(in)),
        WritableUtils.readVInt(in),
        WritableUtils.readVInt(in),
        Text.readString(in),
        Text.readString(in),
        in.readBoolean(),
        in.readBoolean(),
        in.readBoolean(),
        WritableUtils.readVInt(in),
        Text.readString(in),
        in.readBoolean()
      )
    )
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
}
