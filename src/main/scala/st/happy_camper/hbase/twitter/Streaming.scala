package st.happy_camper.hbase.twitter

import handler.HTableHandler

import _root_.scala.actors.{ Actor, DaemonActor, TIMEOUT }
import _root_.scala.collection.mutable.ListBuffer
import _root_.scala.io.Source
import _root_.scala.util.Random

import _root_.org.apache.commons.httpclient.{ HttpClient, UsernamePasswordCredentials, ConnectTimeoutException }
import _root_.org.apache.commons.httpclient.auth.AuthScope
import _root_.org.apache.commons.httpclient.methods.GetMethod

import _root_.org.apache.commons.logging.LogFactory

class Streaming(id: String, pw: String) {

  val Log = LogFactory.getLog(getClass)

  val url = "http://stream.twitter.com/1/statuses/sample.xml?delimited=length"

  val handlers = ListBuffer[String => Unit]()

  private val rnd = new Random

  private val httpClient = new HttpClient
  httpClient.getHttpConnectionManager.getParams.setConnectionTimeout(5*1000)
  httpClient.getHttpConnectionManager.getParams.setSoTimeout(90*1000)
  httpClient.getParams.setAuthenticationPreemptive(true)
  httpClient.getState.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(id, pw))

  private var get: GetMethod = null

  def start {
    streaming.getState match {
      case Actor.State.New => {
        get = connect
        if(get != null) {
          streaming.start
        }
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
      }
    }
  }

  @scala.annotation.tailrec
  private def connect : GetMethod = {
    val get = new GetMethod(url)
    try {
      return httpClient.executeMethod(get) match {
        case 200 => {
          Log.info("Connected.")
          get
        }
        case 401 => {
          Log.warn("401 Unauthorized.")
          null
        }
        case 403 => {
          Log.warn("403 Forbidden.")
          null
        }
        case 404 => {
          Log.warn("404 Unknown.")
          null
        }
        case 406 => {
          Log.warn("406 Not Acceptable.")
          null
        }
        case 413 => {
          Log.warn("413 Too Long.")
          null
        }
        case 416 => {
          Log.warn("416 Range Unacceptable.")
          null
        }
        case 420 => {
          Log.warn("420 Rate Limited.")
          val wait = rnd.nextInt(60) + 240
          Log.info("Retry after %d seconds.".format(wait))
          Thread.sleep(wait * 1000)
          throw Continue
        }
        case 500 => {
          Log.warn("500 Server Internal Error.")
          Log.info("Retry after 60 seconds.")
          Thread.sleep(60 * 1000)
          throw Continue
        }
        case 503 => {
          Log.warn("503 Service Overloaded.")
          Log.info("Retry after 60 seconds.")
          Thread.sleep(60 * 1000)
          throw Continue
        }
        case sc => throw new IllegalStateException("Unknown status code: " + sc + ".")
      }
    }
    catch {
      case Continue => 
      case e => {
        Log.error(e.getMessage, e)
        val wait = rnd.nextInt(20) + 20
        Log.info("Retry after %d seconds.".format(wait))
        Thread.sleep(wait * 1000)
      }
    }
    get.releaseConnection
    connect
  }

  private case class CLOSE()

  private object streaming extends DaemonActor {
    def act = {
      val source = Source.fromInputStream(get.getResponseBodyAsStream)

      def readState : String = {
        val length = source.getLine(1).replaceAll("\\s", "")
        if(length != "") {
          source.take(length.toInt) mkString
        }
        else {
          readState
        }
      }

      loop {
        receiveWithin(0) {
          case TIMEOUT => {
            try {
              val state = readState
              handlers foreach { handler => handler(state) }
            }
            catch {
              case e => {
                Log.error(e.getMessage, e);
                get.releaseConnection
                get = connect
                act
              }
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

  private object Continue extends Exception
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
