package com.proyecto

import org.apache.spark.SparkConf

object Main {

def main(args:Array[String]): Unit = {

    val sparkConf = new SparkConf()
    val trabajo = TrabajoETL.TrabajoETL(sparkConf)
    trabajo.execution()

  }
}
