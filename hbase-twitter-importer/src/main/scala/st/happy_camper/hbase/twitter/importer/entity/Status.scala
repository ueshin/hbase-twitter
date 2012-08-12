/*
 * Copyright 2010-2012 Happy-Camper Street.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package st.happy_camper.hbase.twitter
package importer
package entity

import java.text.SimpleDateFormat
import java.util.Locale

import scala.collection.JavaConversions.iterableAsScalaIterable

import org.codehaus.jackson.JsonNode

import st.happy_camper.hbase.twitter.entity.Status

/**
 * @author ueshin
 *
 */
object Status {

  def apply(json: JsonNode): Status = {
    new Status(
      json.path("id").getLongValue,
      createdAtDateFormat.parse(json.path("created_at").getTextValue),

      json.path("source").getTextValue,
      json.path("text").getTextValue,
      json.path("truncated").getBooleanValue,

      Option(json.path("in_reply_to_status_id").getLongValue).filter(_ != 0L),
      Option(json.path("in_reply_to_user_id").getLongValue).filter(_ != 0L),
      Option(json.path("in_reply_to_screen_name").getTextValue),

      json.path("favorited").getBooleanValue,
      json.path("retweeted").getBooleanValue,
      json.path("retweet_count").getIntValue,

      if (json.path("place").isNull) None else Option(Place(json.path("place"))),
      json.path("entities").path("user_mentions").map(UserMention(_)).toList,
      json.path("entities").path("urls").map(Url(_)).toList,
      json.path("entities").path("hashtags").map(Hashtag(_)).toList,

      User(json.path("user")))
  }

  def unapply(json: JsonNode): Option[Status] = {
    try {
      Option(Status(json))
    } catch {
      case _ => None
    }
  }

  def createdAtDateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.US)
}
