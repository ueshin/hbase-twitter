package st.happy_camper.hbase.twitter
package io.template

import entity.User

import _root_.java.util.Date

import _root_.org.msgpack.{ Template, Packer, Unpacker, MessagePackObject }

object UserTemplate extends Template {

  def pack(packer: Packer, target: Object) {
    val user = target.asInstanceOf[User]
    packer.packLong(user.id)
    packer.packString(user.name)
    packer.packString(user.screenName)
    packer.packString(user.location)
    packer.packString(user.description)
    packer.packString(user.profileImageUrl)
    packer.packString(user.url)
    packer.packBoolean(user.isProtected)
    packer.packInt(user.followersCount)
    packer.packString(user.profileBackgroundColor)
    packer.packString(user.profileTextColor)
    packer.packString(user.profileLinkColor)
    packer.packString(user.profileSidebarFillColor)
    packer.packString(user.profileSidebarBorderColor)
    packer.packInt(user.friendsCount)
    packer.packLong(user.createdAt.getTime)
    packer.packInt(user.favouritesCount)
    packer.packInt(user.utcOffset)
    packer.packString(user.timeZone)
    packer.packString(user.profileBackgroundImageUrl)
    packer.packBoolean(user.profileBackgroundTile)
    packer.packBoolean(user.geoEnabled)
    packer.packBoolean(user.verified)
    packer.packInt(user.statusesCount)
    packer.packString(user.lang)
    packer.packBoolean(user.contributorsEnabled)
  }

  def unpack(unpacker: Unpacker) = {
    new User(
      unpacker.unpackLong,
      unpacker.unpackString,
      unpacker.unpackString,
      unpacker.unpackString,
      unpacker.unpackString,
      unpacker.unpackString,
      unpacker.unpackString,
      unpacker.unpackBoolean,
      unpacker.unpackInt,
      unpacker.unpackString,
      unpacker.unpackString,
      unpacker.unpackString,
      unpacker.unpackString,
      unpacker.unpackString,
      unpacker.unpackInt,
      new Date(unpacker.unpackLong),
      unpacker.unpackInt,
      unpacker.unpackInt,
      unpacker.unpackString,
      unpacker.unpackString,
      unpacker.unpackBoolean,
      unpacker.unpackBoolean,
      unpacker.unpackBoolean,
      unpacker.unpackInt,
      unpacker.unpackString,
      unpacker.unpackBoolean
    )
  }

  def convert(from: MessagePackObject) = from
}
