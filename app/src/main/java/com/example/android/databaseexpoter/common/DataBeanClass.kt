package com.example.android.databaseexpoter.common

import com.example.android.databaseexpoter.db.entity.FirstTableEntity
import com.example.android.databaseexpoter.db.entity.SecondTableEntity
import com.google.gson.annotations.SerializedName

class DataBeanClass {
    @SerializedName("first_table")
    lateinit var systemFeatures: List<FirstTableEntity>

    @SerializedName("second_table")
    lateinit var preferenceData: List<SecondTableEntity>
}