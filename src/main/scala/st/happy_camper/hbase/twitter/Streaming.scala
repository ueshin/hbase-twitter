package st.happy_camper.hbase.twitter

import _root_.st.happy_camper.hbase.twitter.handler.HTableHandler

import _root_.org.apache.commons.logging.LogFactory

class Streaming(id: String, pw: String) {

  import _root_.java.io.InputStream
  import _root_.java.net.{ Authenticator, PasswordAuthentication, URL }

  import _root_.scala.collection.mutable.ListBuffer
  import _root_.scala.io.Source

  import _root_.scala.actors.{ Actor, TIMEOUT }

  val Log = LogFactory.getLog(getClass)

  val url = new URL("http://stream.twitter.com/1/statuses/sample.xml?delimited=length")

  Authenticator.setDefault(new Authenticator {
    override def getPasswordAuthentication = {
      new PasswordAuthentication(id, pw.toCharArray)
    }
  })

  val handlers = ListBuffer[String => Unit]()

  private var in: InputStream = null

  def start {
    streaming.getState match {
      case Actor.State.New => {
        val conn = url.openConnection
        conn.setReadTimeout(10000)
        in = conn.getInputStream
        streaming.start
      }
      case Actor.State.Terminated => throw new IllegalStateException("Streaming was closed.")
      case _ => throw new IllegalStateException("Streaming has already been started.")
    }
  }

  def close {
    streaming.getState match {
      case Actor.State.New => throw new IllegalStateException("Streaming has not been started yet.")
      case Actor.State.Terminated => throw new IllegalStateException("Streaming was closed.")
      case _ => {
        streaming !? CLOSE
        if(in != null) {
          in.close
        }
      }
    }
  }

  private case class CLOSE()

  private object streaming extends Actor {
    def act = {
      val source = Source.fromInputStream(in)

      def readState = {
        val length = source.getLine(1).replaceAll("\\s", "").toInt
        source.take(length) mkString
      }

      loop {
        receiveWithin(0) {
          case TIMEOUT => {
            try {
              val state = readState
              handlers foreach { handler => handler(state) }
            }
            catch {
              case e => Log.error(e.getMessage, e); exit
            }
          }
          case CLOSE => {
            reply()
            exit
          }
        }
      }
    }
  }

}

object Streaming {
  def main(args : Array[String]) {
    val streaming = new Streaming(args(0), args(1))
    val handler = new HTableHandler
    streaming.handlers += handler
    try {
      streaming.start
      Stream.continually(System.in.read) takeWhile(_ != -1)
      streaming.close
    }
    finally {
      handler.close
    }
  }
}
