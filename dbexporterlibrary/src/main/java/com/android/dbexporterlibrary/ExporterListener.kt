package com.android.dbexporterlibrary

interface ExporterListener {
    fun success(s: String)

    fun fail(message: String,exception:String)
}