package com.example.android.databaseexpoter.db.repository

import android.app.Application
import android.arch.lifecycle.LiveData
import android.os.AsyncTask
import com.example.android.databaseexpoter.db.dao.FirstTableDao
import com.example.android.databaseexpoter.db.entity.FirstTableEntity
import com.tanzanite.blubox.db.AppDatabase


class FirstTableRepo(application: Application) {

    private val mFirstTableDao: FirstTableDao?
    private val mAllData: LiveData<List<FirstTableEntity>>?

    init {
        val db = AppDatabase.getInstance(application)
        mFirstTableDao = db!!.firstTableDao()
        mAllData = mFirstTableDao.getAllData()
    }

    fun insert(systemFeatureEntity: FirstTableEntity) {
        InsertAsyncTask(mFirstTableDao).execute(systemFeatureEntity)
    }

    fun insertData(data: List<FirstTableEntity>) {
        mFirstTableDao?.insertList(data)
    }

    fun getAllSystemData(): LiveData<List<FirstTableEntity>>? {
        return mAllData
    }


    private class InsertAsyncTask(private val mAsyncTaskDao: FirstTableDao?) : AsyncTask<FirstTableEntity, Void, Void>() {

        override fun doInBackground(vararg params: FirstTableEntity): Void? {
            mAsyncTaskDao?.insert(params[0])
            return null
        }
    }


}