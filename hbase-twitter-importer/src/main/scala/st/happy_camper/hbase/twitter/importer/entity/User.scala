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

import org.codehaus.jackson.JsonNode

import st.happy_camper.hbase.twitter.entity.User

/**
 * @author ueshin
 *
 */
object User {

  def apply(json: JsonNode): User = {
    new User(
      json.path("id").getLongValue,
      json.path("name").getTextValue,
      json.path("screen_name").getTextValue,
      createdAtDateFormat.parse(json.path("created_at").getTextValue),
      Option(json.path("description").getTextValue),
      Option(json.path("url").getTextValue),

      json.path("lang").getTextValue,
      Option(json.path("location").getTextValue),
      Option(json.path("time_zone").getTextValue),
      if (json.path("utc_offset").isNumber) Option(json.path("utc_offset").getIntValue) else None,

      json.path("statuses_count").getIntValue,
      json.path("favourites_count").getIntValue,
      json.path("followers_count").getIntValue,
      json.path("friends_count").getIntValue,
      json.path("listed_count").getIntValue,

      json.path("profile_image_url").getTextValue,
      json.path("profile_background_image_url").getTextValue,
      Option(json.path("profile_text_color").getTextValue),
      Option(json.path("profile_link_color").getTextValue),
      Option(json.path("profile_sidebar_fill_color").getTextValue),
      Option(json.path("profile_sidebar_border_color").getTextValue),
      Option(json.path("profile_background_color").getTextValue),
      json.path("profile_background_tile").getBooleanValue,
      json.path("profile_use_background_image").getBooleanValue,

      json.path("protected").getBooleanValue,
      json.path("following").getBooleanValue,
      json.path("follow_request_sent").getBooleanValue,

      json.path("notifications").getBooleanValue,
      json.path("verified").getBooleanValue,
      json.path("geo_enabled").getBooleanValue,
      json.path("contributors_enabled").getBooleanValue,
      json.path("show_all_inline_media").getBooleanValue,
      json.path("is_translator").getBooleanValue)
  }

  def unapplly(json: JsonNode): Option[User] = {
    try {
      Option(User(json))
    } catch {
      case _ => None
    }
  }

  def createdAtDateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.US)
}
