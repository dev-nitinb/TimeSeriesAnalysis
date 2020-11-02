package com.example.timeseriesanalysis.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "time_series")
class TimeSeriesTable(

    @ColumnInfo(name = "date")
    var date: String,

    @ColumnInfo(name = "open")
    var open: String,

    @ColumnInfo(name = "high")
    var high: String,

    @ColumnInfo(name = "low")
    var low: String,

    @ColumnInfo(name = "close")
    var close: String){

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0

}

