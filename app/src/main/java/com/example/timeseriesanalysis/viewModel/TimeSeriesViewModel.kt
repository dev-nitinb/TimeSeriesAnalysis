package com.example.timeseriesanalysis.viewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.timeseriesanalysis.model.TimeSeriesModel
import com.example.timeseriesanalysis.repository.TimeSeriesRepository

class TimeSeriesViewModel :ViewModel(){
    fun insertAllTimerServiceInDb(context: Context, alTimeSeries:ArrayList<TimeSeriesModel>){
        TimeSeriesRepository.insertAllTimerServiceInDb(context,alTimeSeries)
    }

    fun fetchTimeSeriesData(context:Context): LiveData<ArrayList<TimeSeriesModel>> {
        return TimeSeriesRepository.fetchTimeSeriesData(context)
    }
}