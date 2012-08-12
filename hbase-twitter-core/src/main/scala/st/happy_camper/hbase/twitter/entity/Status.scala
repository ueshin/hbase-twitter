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
package st.happy_camper.hbase.twitter.entity

import java.util.Date

/**
 * @author ueshin
 *
 */
class Status(
  val id: Long,
  val createdAt: Date,

  val source: String,
  val text: String,
  val truncated: Boolean,

  val inReplyToStatusId: Option[Long],
  val inReplyToUserId: Option[Long],
  val inReplyToScreenName: Option[String],

  val favorited: Boolean,
  val retweeted: Boolean,
  val retweetCount: Int,

  val place: Option[Place],
  val userMentions: List[UserMention],
  val urls: List[Url],
  val hashtags: List[Hashtag],

  val user: User)
