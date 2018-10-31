package com.tanzanite.blubox.db

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import android.os.AsyncTask
import com.example.android.databaseexpoter.common.DataBeanClass
import com.example.android.databaseexpoter.common.SampleApplication
import com.example.android.databaseexpoter.db.dao.FirstTableDao
import com.example.android.databaseexpoter.db.dao.SecondTableDao
import com.example.android.databaseexpoter.db.entity.FirstTableEntity
import com.example.android.databaseexpoter.db.entity.SecondTableEntity
import com.example.android.databaseexpoter.db.repository.FirstTableRepo
import com.example.android.databaseexpoter.db.repository.SecondTableRepo
import com.example.android.databaseexpoter.common.loadSystemFeatureDataFromAsset
import com.google.gson.Gson


@Database(entities = [FirstTableEntity::class, SecondTableEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun firstTableDao(): FirstTableDao
    abstract fun secondTableDao(): SecondTableDao

    companion object {
        private var INSTANCE: AppDatabase? = null

        @JvmStatic
        fun getInstance(context: Context): AppDatabase? {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                            AppDatabase::class.java, "sampleDbExporter")
                            .addCallback(object : Callback() {
                                override fun onCreate(db: SupportSQLiteDatabase) {
                                    super.onCreate(db)

                                    InsertDataAsyncTask(context).execute()
                                }
                            })
                            .build()
                }
            }
            return INSTANCE
        }

        @JvmStatic
        fun destroyInstance() {
            if (INSTANCE?.isOpen!!) INSTANCE?.close();
            INSTANCE = null
        }
    }

    private class InsertDataAsyncTask(val context: Context) : AsyncTask<Void, Void, Void>() {

        private val mFirstTableRepo: FirstTableRepo = FirstTableRepo(SampleApplication.getInstance())
        private val mSecondTableRepo: SecondTableRepo = SecondTableRepo(SampleApplication.getInstance())

        override fun doInBackground(vararg params: Void?): Void? {
            val mGson = Gson()
            val systemJsonData = mGson.fromJson(loadSystemFeatureDataFromAsset(context), DataBeanClass::class.java)

            val firstTableData = systemJsonData.systemFeatures
            val secondTableData = systemJsonData.preferenceData

            mFirstTableRepo.insertData(firstTableData)
            mSecondTableRepo.insertData(secondTableData)

            return null
        }

    }

}