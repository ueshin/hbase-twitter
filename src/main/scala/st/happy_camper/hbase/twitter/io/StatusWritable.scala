package st.happy_camper.hbase.twitter
package io

import entity.Status
import template.StatusTemplate

import _root_.java.io.{ DataInput, DataOutput }
import _root_.java.util.Date

import _root_.org.apache.hadoop.hbase.util.Writables

import _root_.org.apache.hadoop.io.{ Text, Writable, WritableUtils }

import _root_.org.msgpack.MessagePack

private class StatusWritable(var status: Status = null) extends Writable {

  def write(out: DataOutput) {
    StatusWritable.write(out, status)
  }

  def readFields(in: DataInput) {
    status = StatusWritable.read(in)
  }
}

object StatusWritable {

  def apply(status: Status) = {
    Writables.getBytes(new StatusWritable(status))
  }

  def unapply(b: Array[Byte]) = {
    try {
      val writable = new StatusWritable
      Writables.getWritable(b, writable)
      Option(writable.status)
    }
    catch {
      case _ => None
    }
  }

  def write(out: DataOutput, status: Status) {
    val bytes = MessagePack.pack(status, StatusTemplate)
    WritableUtils.writeVInt(out, bytes.length)
    out.write(bytes)
  }

  def read(in: DataInput) = {
    val length = WritableUtils.readVInt(in)
    val bytes = new Array[Byte](length)
    in.readFully(bytes)
    MessagePack.unpack(bytes, StatusTemplate).asInstanceOf[Status]
  }
}
