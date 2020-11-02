package com.example.timeseriesanalysis.db.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.timeseriesanalysis.db.doa.TimeSeriesDao
import com.example.timeseriesanalysis.db.entities.TimeSeriesTable
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@Database(entities = [TimeSeriesTable::class], version = 1, exportSchema = false)
abstract class TimeSeriesRoomDatabase : RoomDatabase(){

    abstract fun timerDao(): TimeSeriesDao

    companion object{
        var instance: TimeSeriesRoomDatabase?=null
        private const val DATABASE_NAME="timer_series_database"

        //static function to return instance of db
        fun getInstance(context: Context): TimeSeriesRoomDatabase {
            if(instance ==null){
                instance =Room
                    .databaseBuilder(context, TimeSeriesRoomDatabase::class.java, DATABASE_NAME)
                    .build()
            }
            return instance!!
        }
    }

}