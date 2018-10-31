package com.example.android.databaseexpoter.db.repository

import android.app.Application
import android.arch.lifecycle.LiveData
import android.os.AsyncTask
import com.example.android.databaseexpoter.db.dao.FirstTableDao
import com.example.android.databaseexpoter.db.dao.SecondTableDao
import com.example.android.databaseexpoter.db.entity.FirstTableEntity
import com.example.android.databaseexpoter.db.entity.SecondTableEntity
import com.tanzanite.blubox.db.AppDatabase

class SecondTableRepo(application: Application) {
    private val mSecondTableDao: SecondTableDao?
    private val mAllData: LiveData<List<SecondTableEntity>>?

    init {
        val db = AppDatabase.getInstance(application)
        mSecondTableDao = db!!.secondTableDao()
        mAllData = mSecondTableDao.getAllData()
    }

    fun insert(secondTableEntity: SecondTableEntity) {
        InsertAsyncTask(mSecondTableDao).execute(secondTableEntity)
    }

    fun insertData(data: List<SecondTableEntity>) {
        mSecondTableDao?.insertList(data)
    }

    fun getAllSystemData(): LiveData<List<SecondTableEntity>>? {
        return mAllData
    }


    private class InsertAsyncTask(private val mAsyncTaskDao: SecondTableDao?) : AsyncTask<SecondTableEntity, Void, Void>() {

        override fun doInBackground(vararg params: SecondTableEntity): Void? {
            mAsyncTaskDao?.insert(params[0])
            return null
        }
    }
}