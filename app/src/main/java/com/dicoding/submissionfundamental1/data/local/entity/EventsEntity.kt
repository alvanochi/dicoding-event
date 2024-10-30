package com.dicoding.submissionfundamental1.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "events")
class EventsEntity(
    @field:ColumnInfo(name = "idEvent")
    @field:PrimaryKey(autoGenerate = false)
    val idEvent: Int,

    @field:ColumnInfo(name = "name")
    val name: String,

    @field:ColumnInfo(name = "mediaCover")
    val mediaCover: String,


)