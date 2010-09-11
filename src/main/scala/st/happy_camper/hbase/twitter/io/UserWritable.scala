package st.happy_camper.hbase.twitter
package io

import entity.User

import _root_.java.io.{ DataInput, DataOutput }
import _root_.java.util.Date

import _root_.org.apache.hadoop.hbase.util.Writables

import _root_.org.apache.hadoop.io.{ Text, Writable, WritableUtils }

private class UserWritable(var user: User = null) extends Writable {

  def write(out: DataOutput) {
    UserWritable.write(out, user)
  }

  def readFields(in: DataInput) {
    user = UserWritable.read(in)
  }
}

object UserWritable {

  def apply(user: User) = {
    Writables.getBytes(new UserWritable(user))
  }

  def unapply(b: Array[Byte]) = {
    try {
      val writable = new UserWritable
      Writables.getWritable(b, writable)
      Option(writable.user)
    }
    catch {
      case _ => None
    }
  }

  def write(out: DataOutput, user: User) {
    WritableUtils.writeVLong(out, user.id)
    Text.writeString(out, user.name)
    Text.writeString(out, user.screenName)
    Text.writeString(out, user.location)
    Text.writeString(out, user.description)
    Text.writeString(out, user.profileImageUrl)
    Text.writeString(out, user.url)
    out.writeBoolean(user.isProtected)
    WritableUtils.writeVInt(out, user.followersCount)
    Text.writeString(out, user.profileBackgroundColor)
    Text.writeString(out, user.profileTextColor)
    Text.writeString(out, user.profileLinkColor)
    Text.writeString(out, user.profileSidebarFillColor)
    Text.writeString(out, user.profileSidebarBorderColor)
    WritableUtils.writeVInt(out, user.friendsCount)
    WritableUtils.writeVLong(out, user.createdAt.getTime)
    WritableUtils.writeVInt(out, user.favouritesCount)
    WritableUtils.writeVInt(out, user.utcOffset)
    Text.writeString(out, user.timeZone)
    Text.writeString(out, user.profileBackgroundImageUrl)
    out.writeBoolean(user.profileBackgroundTile)
    out.writeBoolean(user.geoEnabled)
    out.writeBoolean(user.verified)
    WritableUtils.writeVInt(out, user.statusesCount)
    Text.writeString(out, user.lang)
    out.writeBoolean(user.contributorsEnabled)
  }

  def read(in: DataInput) = {
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
  }
}
