package com.example.android.databaseexpoter.db.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.example.android.databaseexpoter.db.entity.FirstTableEntity

@Dao
interface FirstTableDao {
    @Insert
    fun insertList(data: List<FirstTableEntity>)

    @Query("SELECT * FROM firstTabel")
    fun getAllData(): LiveData<List<FirstTableEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(systemFeatureEntity: FirstTableEntity)

}