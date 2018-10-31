package com.example.android.databaseexpoter.db.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.example.android.databaseexpoter.db.entity.FirstTableEntity
import com.example.android.databaseexpoter.db.entity.SecondTableEntity

@Dao
interface SecondTableDao {
    @Insert
    fun insertList(data: List<SecondTableEntity>)

    @Query("SELECT * FROM firstTabel")
    fun getAllData(): LiveData<List<SecondTableEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(secondTableEntity: SecondTableEntity)
}