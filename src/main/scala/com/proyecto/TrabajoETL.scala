package com.proyecto

import com.proyecto.utilities.ProyectoUtils.createSparkSession
import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.Path
import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession.setDefaultSession
import org.apache.spark.sql.expressions.Window
import org.apache.spark.sql.{SaveMode, SparkSession}
import org.apache.spark.sql.functions.{array_distinct, col, collect_list, concat, concat_ws, explode, length, lit, max, regexp_replace, row_number, sha1, split, trim, upper, when}

class TrabajoETL(properties: Map[String, String])(implicit spark: SparkSession) {

  lazy val parametro1 : String = properties("parametro1")

  def execution(): Unit = {
    val empty_dataframe = spark.emptyDataFrame
    empty_dataframe.show(false)
  }

}

object TrabajoETL {
  def TrabajoETL(conf: SparkConf): TrabajoETL = {
        val properties = conf.getAllWithPrefix(s"spark.proyecto.")
        implicit val spark: SparkSession = createSparkSession(properties)
        new TrabajoETL(properties.toMap)
  }
}
