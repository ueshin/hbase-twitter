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
class User(
  val id: Long,
  val name: String,
  val screenName: String,
  val createdAt: Date,
  val description: Option[String],
  val url: Option[String],

  val lang: String,
  val location: Option[String],
  val timeZone: Option[String],
  val utcOffset: Option[Int],

  val statusesCount: Int,
  val favouritesCount: Int,
  val followersCount: Int,
  val friendsCount: Int,
  val listedCount: Int,

  val profileImageUrl: String,
  val profileBackgroundImageUrl: String,
  val profileTextColor: Option[String],
  val profileLinkColor: Option[String],
  val profileSidebarFillColor: Option[String],
  val profileSidebarBorderColor: Option[String],
  val profileBackgroundColor: Option[String],
  val profileBackgroundTile: Boolean,
  val profileUseBackgroundImage: Boolean,

  val isProtected: Boolean,
  val following: Boolean,
  val followRequestSent: Boolean,

  val notifications: Boolean,
  val verified: Boolean,
  val geoEnabled: Boolean,
  val contributorsEnabled: Boolean,
  val showAllInlineMedia: Boolean,
  val isTranslator: Boolean)
