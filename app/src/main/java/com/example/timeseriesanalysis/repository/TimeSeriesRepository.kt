package com.example.timeseriesanalysis.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.timeseriesanalysis.api.RetrofitInstance
import com.example.timeseriesanalysis.db.database.TimeSeriesRoomDatabase
import com.example.timeseriesanalysis.db.entities.TimeSeriesTable
import com.example.timeseriesanalysis.model.TimeSeriesModel
import com.example.timeseriesanalysis.util.ProjectUtils
import com.google.gson.JsonElement
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Callback
import retrofit2.Response

class TimeSeriesRepository :ViewModel(){
    companion object{
        var TAG="TimeSeriesRepository"
        var timerSeriesDb:TimeSeriesRoomDatabase?=null

        private fun getTimeServiceDb(context: Context):TimeSeriesRoomDatabase{
            if (timerSeriesDb==null)
                timerSeriesDb= TimeSeriesRoomDatabase.getInstance(context)

            return timerSeriesDb!!
        }

        //insert data in db
        fun insertAllTimerServiceInDb(context: Context, alTimeSeries:ArrayList<TimeSeriesModel>){
            CoroutineScope(Dispatchers.IO).launch {
                Log.e(TAG, "Delete all data in db")
                //delete all rows
                getTimeServiceDb(context).timerDao().deleteAllTimeService()
                //insert data in db
                for (i in 0 until alTimeSeries.size-1){
                    var date=alTimeSeries[i].date
                    var open=alTimeSeries[i].open
                    var high=alTimeSeries[i].high
                    var low=alTimeSeries[i].low
                    var close=alTimeSeries[i].close
                    var timeSeriesTable= TimeSeriesTable(date, open, high, low, close)
                    getTimeServiceDb(context).timerDao().insertTimeService(timeSeriesTable)
                }
                Log.e(TAG, "All data added in db")
            }
        }

        //get time series list
        fun fetchTimeSeriesData(context:Context):MutableLiveData<ArrayList<TimeSeriesModel>> {
            ProjectUtils.getInstance(context).showProjectDialog("Please wait! Fetching data from API.")
            var alTimeSeries=ArrayList<TimeSeriesModel>()
            var mlTimeSeries=MutableLiveData<ArrayList<TimeSeriesModel>>()

            val api = RetrofitInstance.api
            //async call
            var call = api.getTimeSeriesDaily()
            call.enqueue(object : Callback<JsonElement> {
                override fun onResponse(call: retrofit2.Call<JsonElement>, response: Response<JsonElement>) {
                    Log.e(TAG, "Success")

                    if (response.isSuccessful && response.body() != null) {
                        ProjectUtils.getInstance(context).showProjectDialog("Processing Data.")

                        //response
                        val timeSeriesResponse = response.body().toString()
                        var jsonResponse = JSONObject(timeSeriesResponse)
                        val timeSeriesDailyJsonObj = jsonResponse.getJSONObject("Time Series (Daily)")

                        var jsonLength = 0

                        //response from dynamic key
                        val keys: Iterator<*> = timeSeriesDailyJsonObj.keys()
                        while (keys.hasNext()) {
                            jsonLength++
                            //date
                            val key = keys.next() as String
                            var timeSeriesCurrentJsonObj = timeSeriesDailyJsonObj.getJSONObject(key)

                            val open = timeSeriesCurrentJsonObj.getString("1. open")
                            val high = timeSeriesCurrentJsonObj.getString("2. high")
                            val low = timeSeriesCurrentJsonObj.getString("3. low")
                            val close = timeSeriesCurrentJsonObj.getString("4. close")


                            alTimeSeries.add(TimeSeriesModel(key, open, high, low, close))

                            //display first 7 data
                            if (jsonLength == 7)
                                break
                        }
                        insertAllTimerServiceInDb(context,alTimeSeries)
                        mlTimeSeries.value=alTimeSeries
                        Log.e(TAG, mlTimeSeries.value.toString())
                    }
                }

                override fun onFailure(call: retrofit2.Call<JsonElement>, t: Throwable) {
                    Log.e(TAG, t.message)
                }
            })
            return mlTimeSeries
        }


    }
}