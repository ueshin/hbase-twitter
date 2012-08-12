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
package st.happy_camper.hbase

import org.apache.hadoop.hbase.util.Bytes

/**
 * @author ueshin
 *
 */
package object twitter {

  implicit def stringToBytes(s: String) = Bytes.toBytes(s)
  implicit def booleanToBytes(b: Boolean) = Bytes.toBytes(b)
  implicit def byteToBytes(b: Byte) = Array(b)
  implicit def intToBytes(i: Int) = Bytes.toBytes(i)
  implicit def longToBytes(l: Long) = Bytes.toBytes(l)
  implicit def floatToBytes(f: Float) = Bytes.toBytes(f)
  implicit def doubleToBytes(d: Double) = Bytes.toBytes(d)
}
