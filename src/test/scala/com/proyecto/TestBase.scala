package com.proyecto

import com.holdenkarau.spark.testing._
import org.apache.spark._
import org.apache.spark.sql.{SQLContext, SparkSession}
import org.scalatest.{BeforeAndAfterAll, Suite}


trait TestBase extends BeforeAndAfterAll with SparkContextProvider {
  self: Suite =>

  @transient var _sparkSql: SparkSession = _
  @transient protected var _sc: SparkContext = _
  @transient protected var _sqlContext: SQLContext = _

  val loader = getClass.getClassLoader

  override def sc: SparkContext = _sc
  def conf: SparkConf

  def sparkSql: SparkSession = _sparkSql
  protected implicit def reuseContextIfPossible: Boolean = true
  override def beforeAll(): Unit = {
    // This is kind of a hack, but if we've got an existing Spark Context
    // hanging around we need to kill it.
    if (!reuseContextIfPossible) {
      EvilSparkContext.stopActiveSparkContext()
    }

//    conf.set("hive.exec.dynamic.partition.mode", "nonstrict")
//    conf.set("spark.sql.catalogImplementation", "hive")
//    conf.set("hive.metastore.uris", "thrift://127.0.0.1:9083")

    _sparkSql = SparkSession
      .builder()
      .config(conf)
      .enableHiveSupport()
      .getOrCreate()
    _sc = _sparkSql.sparkContext
    _sqlContext = _sparkSql.sqlContext
    setup(_sc)
    super.beforeAll()
  }


  override def afterAll(): Unit = {
    try {
      if (!reuseContextIfPossible) {
        LocalSparkContext.stop(_sc)
        _sc = null
      }
    } finally {
      super.afterAll()
    }
  }
}