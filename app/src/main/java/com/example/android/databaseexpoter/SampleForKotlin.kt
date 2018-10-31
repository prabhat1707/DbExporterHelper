package com.example.android.databaseexpoter

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.android.dbexporterlibrary.ExportDbUtil
import com.android.dbexporterlibrary.ExporterListener

class SampleForKotlin : AppCompatActivity(),ExporterListener {
    override fun fail(message: String, exception: String) {

    }

    override fun success(s: String) {

    }

    lateinit var dbUtil:ExportDbUtil
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val dbToCsv = ExportDbUtil(this, "sampleDbExporter", "library Test",this)
        dbToCsv.exportAllTables("test.csv")
    }
}