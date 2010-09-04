package st.happy_camper.hbase.twitter
package entity

import _root_.scala.collection.immutable.SortedMap

class Score(val score: Double, val count: SortedMap[Long, Long]) {
}
