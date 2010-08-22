package st.happy_camper.hbase.twitter
package handler

import entity.{ Status, User, Delete }
import io.StatusWritable

import _root_.java.util.Arrays

import _root_.scala.xml.XML

import _root_.org.apache.hadoop.hbase.HConstants
import _root_.org.apache.hadoop.hbase.client.{ HTable, Get, Put }

class HTableHandler extends (String => Unit) {

  val table = new HTable("twitter")

  def apply(xml: String) {
    XML.loadString(xml) match {
      case Status(status) => {
        val user = status.user

        val put = new Put(user.key)

        val result = table.get(new Get(user.key).addFamily("user"))

        def addIfNotEquals(family: String, qualifier: String, value: Array[Byte]) {
          if(!Arrays.equals(result.getValue(family, qualifier), value)) {
            put.add(family, qualifier, value)
          }
        }

        addIfNotEquals("user", "name", user.name)
        addIfNotEquals("user", "screenName", user.screenName)
        addIfNotEquals("user", "location", user.location)
        addIfNotEquals("user", "description", user.description)
        addIfNotEquals("user", "profileImageUrl", user.profileImageUrl)
        addIfNotEquals("user", "url", user.url)
        addIfNotEquals("user", "isProtected", user.isProtected)
        addIfNotEquals("user", "followersCount", user.followersCount)
        addIfNotEquals("user", "profileBackgroundColor", user.profileBackgroundColor)
        addIfNotEquals("user", "profileTextColor", user.profileTextColor)
        addIfNotEquals("user", "profileLinkColor", user.profileLinkColor)
        addIfNotEquals("user", "profileSidebarFillColor", user.profileSidebarFillColor)
        addIfNotEquals("user", "profileSidebarBorderColor", user.profileSidebarBorderColor)
        addIfNotEquals("user", "friendsCount", user.friendsCount)
        addIfNotEquals("user", "createdAt", user.createdAt.getTime)
        addIfNotEquals("user", "favouritesCount", user.favouritesCount)
        addIfNotEquals("user", "utcOffset", user.utcOffset)
        addIfNotEquals("user", "timeZone", user.timeZone)
        addIfNotEquals("user", "profileBackgroundImageUrl", user.profileBackgroundImageUrl)
        addIfNotEquals("user", "profileBackgroundTile", user.profileBackgroundTile)
        addIfNotEquals("user", "geoEnabled", user.geoEnabled)
        addIfNotEquals("user", "verified", user.verified)
        addIfNotEquals("user", "statusesCount", user.statusesCount)
        addIfNotEquals("user", "lang", user.lang)
        addIfNotEquals("user", "contributorsEnabled", user.contributorsEnabled)

        put.add("status", status.key, StatusWritable(status))
        table.put(put)

      }
      case Delete(delete) => {
        table.put(new Put(delete.userKey).add("status", delete.statusKey, HConstants.EMPTY_BYTE_ARRAY))
      }
      case _ => println(xml)
    }
  }

  def close {
    table.close
  }
}

