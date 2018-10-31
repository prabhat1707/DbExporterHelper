package com.example.android.databaseexpoter.db.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "firstTabel")
class FirstTableEntity {

    @PrimaryKey
    @ColumnInfo(name = "id")
    @SerializedName("id")
    var id: Int = 0

    @ColumnInfo(name = "name")
    @SerializedName("name")
    var name: String = ""

    @ColumnInfo(name = "type")
    @SerializedName("type")
    var type: String = ""


}