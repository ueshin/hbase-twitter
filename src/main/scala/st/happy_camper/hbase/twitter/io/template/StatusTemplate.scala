package st.happy_camper.hbase.twitter
package io.template

import entity.Status

import _root_.java.util.Date

import _root_.org.msgpack.{ Template, Packer, Unpacker, MessagePackObject }

object StatusTemplate extends Template {

  def pack(packer: Packer, target: Object) {
    val status = target.asInstanceOf[Status]
    packer.packLong(status.createdAt.getTime)
    packer.packLong(status.id)
    packer.packString(status.text)
    packer.packString(status.source)
    packer.packBoolean(status.truncated)
    packer.packLong(status.inReplyToStatusId)
    packer.packLong(status.inReplyToUserId)
    packer.packBoolean(status.favorited)
    packer.packString(status.inReplyToScreenName)
    UserTemplate.pack(packer, status.user)
  }

  def unpack(unpacker: Unpacker) = {
    new Status(
      new Date(unpacker.unpackLong),
      unpacker.unpackLong,
      unpacker.unpackString,
      unpacker.unpackString,
      unpacker.unpackBoolean,
      unpacker.unpackLong,
      unpacker.unpackLong,
      unpacker.unpackBoolean,
      unpacker.unpackString,
      UserTemplate.unpack(unpacker)
    )
  }

  def convert(from: MessagePackObject) = from
}
