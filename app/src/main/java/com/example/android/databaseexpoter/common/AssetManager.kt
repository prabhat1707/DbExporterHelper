@file:JvmName("AssetManager")

package com.example.android.databaseexpoter.common

import android.content.Context
import java.io.IOException
import java.nio.charset.Charset

fun loadSystemFeatureDataFromAsset(context: Context): String? {
    val json: String

    try {
        val assetManager = context.assets
        val inputStream = assetManager.open("dbExporter.json")
        val size = inputStream.available()
        val buffer = ByteArray(size)
        inputStream.read(buffer)
        inputStream.close()
        json = String(buffer, Charset.forName("UTF-8"))
    } catch (ex: IOException) {
        ex.printStackTrace()
        return null
    }

    return json
}