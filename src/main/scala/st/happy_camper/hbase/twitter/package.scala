package st.happy_camper.hbase

import _root_.org.apache.hadoop.io.Text

import _root_.org.apache.hadoop.hbase.util.Bytes

package object twitter {

  implicit def stringToBytes(s: String)   = Bytes.toBytes(s)
  implicit def booleanToBytes(b: Boolean) = Bytes.toBytes(b)
  implicit def intToBytes(i: Int)         = Bytes.toBytes(i)
  implicit def longToBytes(l: Long)       = Bytes.toBytes(l)
  implicit def floatToBytes(f: Float)     = Bytes.toBytes(f)
  implicit def doubleToBytes(d: Double)   = Bytes.toBytes(d)

  class EnsureClose(val closable: { def close() }) {
    def open[A](f: { def close() } => A ) = {
      try {
        f(closable)
      }
      finally {
        closable.close
      }
    }
  }
  implicit def ensureClose(closable: { def close() }) = new EnsureClose(closable)
}
