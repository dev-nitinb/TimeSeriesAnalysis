package com.example.timeseriesanalysis.db.doa

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.timeseriesanalysis.db.entities.TimeSeriesTable
import com.example.timeseriesanalysis.model.TimeSeriesModel

@Dao
interface TimeSeriesDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTimeService(timer: TimeSeriesTable?)

    @Query("delete from time_series")
    suspend fun deleteAllTimeService()
}
