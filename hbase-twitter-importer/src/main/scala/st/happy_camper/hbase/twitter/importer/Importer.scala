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

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.conf.Configured
import org.apache.hadoop.fs.Path
import org.apache.hadoop.hbase.HBaseConfiguration
import org.apache.hadoop.hbase.client.Put
import org.apache.hadoop.hbase.io.ImmutableBytesWritable
import org.apache.hadoop.hbase.mapreduce.HRegionPartitioner
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil
import org.apache.hadoop.mapreduce.Job
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat
import org.apache.hadoop.util.Tool
import org.apache.hadoop.util.ToolRunner

import st.happy_camper.hbase.twitter.entity.Status
import st.happy_camper.hbase.twitter.importer.mapreduce.ImporterMapper

/**
 * @author ueshin
 *
 */
class Importer(conf: Configuration = HBaseConfiguration.create) extends Configured(conf) with Tool {

  val TableName = "timeline"

  override def run(args: Array[String]): Int = {
    val input = args(0)

    val job = new Job(getConf, "Importer: " + input)
    job.setJarByClass(getClass)

    job.setInputFormatClass(classOf[TextInputFormat])
    FileInputFormat.setInputPaths(job, new Path(input))

    job.setMapperClass(classOf[ImporterMapper])
    job.setMapOutputKeyClass(classOf[ImmutableBytesWritable])
    job.setMapOutputValueClass(classOf[Put])

    TableMapReduceUtil.setNumReduceTasks(TableName, job)
    TableMapReduceUtil.initTableReducerJob(TableName, null, job, classOf[HRegionPartitioner[_, _]])

    TableMapReduceUtil.addDependencyJars(job.getConfiguration, classOf[Status])

    if (job.waitForCompletion(true)) 0 else 1
  }
}

/**
 *
 */
object Importer {

  def main(args: Array[String]) {
    exit(ToolRunner.run(new Importer, args))
  }
}
