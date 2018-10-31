package com.example.android.databaseexpoter.common

import android.app.Application
import com.tanzanite.blubox.db.AppDatabase
import android.arch.persistence.room.Room



class SampleApplication : Application() {

    companion object {
        @JvmStatic
        private lateinit var mInstance: SampleApplication

        @JvmStatic
        fun getInstance(): SampleApplication {
            return mInstance
        }
    }

    override fun onCreate() {
        super.onCreate()
        mInstance = this
        AppDatabase.getInstance(this)?.openHelper?.writableDatabase
    }
}