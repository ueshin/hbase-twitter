package st.happy_camper.hbase.twitter.handler

import _root_.st.happy_camper.hbase.twitter.entity.{ Status, User, DeleteStatus }
import _root_.st.happy_camper.hbase.twitter.io.StatusWritable

import _root_.scala.xml.XML

import _root_.org.apache.hadoop.io.Writable

import _root_.org.apache.hadoop.hbase.client.{ HTable, Get, Put, Delete }
import _root_.org.apache.hadoop.hbase.util.{ Bytes, Writables }

class HTableHandler extends (String => Unit) {

  private implicit def intToBytes(i: Int) = Bytes.toBytes(i)
  private implicit def longToBytes(l: Long) = Bytes.toBytes(l)
  private implicit def booeanToBytes(b: Boolean) = Bytes.toBytes(b)
  private implicit def stringToBytes(s: String) = Bytes.toBytes(s)
  private implicit def writableToBytes(w: Writable) = Writables.getBytes(w)

  val table = new HTable("twitter")

  def apply(xml: String) {
    XML.loadString(xml) match {
      case Status(status) =>
        status.user map {
          user => {
            val put = new Put(user.key)

            val result = table.get(new Get(user.key).addFamily("user"))

            def putIfNotEqualsStringValue(family: String, qualifier: String, value: String) {
              if(!result.containsColumn(family, qualifier) ||
                 value != Bytes.toString(result.getValue(family, qualifier))) {
                put.add(family, qualifier, value)
              }
            }

            def putIfNotEqualsIntValue(family: String, qualifier: String, value: Int) {
              if(!result.containsColumn(family, qualifier) ||
                 value != Bytes.toInt(result.getValue(family, qualifier))) {
                put.add(family, qualifier, value)
              }
            }

            def putIfNotEqualsLongValue(family: String, qualifier: String, value: Long) {
              if(!result.containsColumn(family, qualifier) ||
                 value != Bytes.toLong(result.getValue(family, qualifier))) {
                put.add(family, qualifier, value)
              }
            }

            def putIfNotEqualsBooleanValue(family: String, qualifier: String, value: Boolean) {
              if(!result.containsColumn(family, qualifier) ||
                 value != Bytes.toBoolean(result.getValue(family, qualifier))) {
                put.add(family, qualifier, value)
              }
            }

            putIfNotEqualsStringValue("user", "name", user.name)
            putIfNotEqualsStringValue("user", "screenName", user.screenName)
            putIfNotEqualsStringValue("user", "location", user.location)
            putIfNotEqualsStringValue("user", "description", user.description)
            putIfNotEqualsStringValue("user", "profileImageUrl", user.profileImageUrl)
            putIfNotEqualsStringValue("user", "url", user.url)
            putIfNotEqualsBooleanValue("user", "isProtected", user.isProtected)
            putIfNotEqualsIntValue("user", "followersCount", user.followersCount)
            putIfNotEqualsStringValue("user", "profileBackgroundColor", user.profileBackgroundColor)
            putIfNotEqualsStringValue("user", "profileTextColor", user.profileTextColor)
            putIfNotEqualsStringValue("user", "profileLinkColor", user.profileLinkColor)
            putIfNotEqualsStringValue("user", "profileSidebarFillColor", user.profileSidebarFillColor)
            putIfNotEqualsStringValue("user", "profileSidebarBorderColor", user.profileSidebarBorderColor)
            putIfNotEqualsIntValue("user", "friendsCount", user.friendsCount)
            putIfNotEqualsLongValue("user", "createdAt", user.createdAt.getTime)
            putIfNotEqualsIntValue("user", "favouritesCount", user.favouritesCount)
            putIfNotEqualsIntValue("user", "utcOffset", user.utcOffset)
            putIfNotEqualsStringValue("user", "timeZone", user.timeZone)
            putIfNotEqualsStringValue("user", "profileBackgroundImageUrl", user.profileBackgroundImageUrl)
            putIfNotEqualsBooleanValue("user", "profileBackgroundTile", user.profileBackgroundTile)
            putIfNotEqualsBooleanValue("user", "geoEnabled", user.geoEnabled)
            putIfNotEqualsBooleanValue("user", "verified", user.verified)
            putIfNotEqualsIntValue("user", "statusesCount", user.statusesCount)
            putIfNotEqualsStringValue("user", "lang", user.lang)
            putIfNotEqualsBooleanValue("user", "contributorsEnabled", user.contributorsEnabled)

            put.add("status", status.key, new StatusWritable(status))
            table.put(put)
          }
        }
      case DeleteStatus(delete) => {
        table.delete(new Delete(delete.userKey).deleteColumns("status", delete.statusKey))
      }
      case _ => println(xml)
    }
  }

  def close {
    table.close
  }
}
