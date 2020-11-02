package com.example.timeseriesanalysis.api

import com.google.gson.JsonElement
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface TimeSeriesApi{

    @GET("query")
    fun getTimeSeriesDaily(
        @Query("function") function:String ="TIME_SERIES_DAILY",
        @Query("symbol") symbol:String="IBM",
        @Query("apikey") apikey:String="demo"
    ):Call<JsonElement>
}