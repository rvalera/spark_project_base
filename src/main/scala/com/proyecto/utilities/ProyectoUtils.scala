package com.proyecto.utilities

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.Path
import org.apache.spark.sql.functions._
import org.apache.spark.sql.{Column, DataFrame, SaveMode, SparkSession}
import org.joda.time.format.{DateTimeFormat, DateTimeFormatter}

import java.io.File
import java.time.LocalDate

object ProyectoUtils {

  implicit class When[A](a: A) {
    def when(f: Boolean)(g: A => A)(h: A => A): A = if (f) g(a) else h(a)
  }
  def createSparkSession(confs: Array[(String, String)] = Array.empty): SparkSession = {
    val key = "spark_prop."
    val sparkConfs = confs.filter(_._1 startsWith key).map(prop => (prop._1.substring(key.length), prop._2))
    SparkSession.builder
      .config("hive.exec.dynamic.partition", value = true)
      .config("hive.exec.dynamic.partition.mode", "nonstrict")
      .config("hive.exec.max.dynamic.partitions", 2048)
      .config("spark.sql.crossJoin.enabled", "true")
      .when(!sparkConfs.isEmpty)(ssb => sparkConfs.foldLeft(ssb)((acm, prop) => acm.config(prop._1, prop._2)))(ssb => ssb)
      .enableHiveSupport
      .getOrCreate
  }

}
