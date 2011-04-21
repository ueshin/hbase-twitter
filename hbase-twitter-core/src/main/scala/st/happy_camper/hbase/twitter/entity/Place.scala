package st.happy_camper.hbase.twitter.entity

class Place(
  val id: String,
  val name: String,
  val fullName: String,
  val url: String,
  val placeType: String,
  val country: String,
  val countryCode: String,
  val boundingBox: Option[String],
  val boundingBoxType: Option[String],
  val attributes: String
)
