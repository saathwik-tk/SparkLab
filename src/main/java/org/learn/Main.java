package org.learn;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");

        SparkSession spark = SparkSession.builder().appName("Hudi Read").master("local[*]")
                .config("fs.s3a.access.key","minioadmin")
                .config("fs.s3a.secret.key","minioadmin")
                .config("fs.s3a.path.style.access","true")
                .config("fs.s3a.impl","org.apache.hadoop.fs.s3a.S3AFileSystem")
                .config("fs.s3a.connection.ssl.enabled","false")
                .config("fs.s3a.endpoint","http://localhost:9000")
                .config("spark.sql.extensions", "org.apache.spark.sql.hudi.HoodieSparkSessionExtension")
                .config("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
                .config("spark.sql.catalog.spark_catalog", "org.apache.spark.sql.hudi.catalog.HoodieCatalog")
                .getOrCreate();

        Dataset<Row> dataFrame = spark.read().format("hudi").load("s3a://beambucket/BeamFolder/");
        dataFrame.show(false);


        spark.stop();
    }
}