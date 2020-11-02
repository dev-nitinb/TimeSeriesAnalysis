package com.example.timeseriesanalysis.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitInstance {
    private const val BASE_URL="https://www.alphavantage.co/"

    //check log in debug under okhttp
    private val httpLoggingInterceptor: HttpLoggingInterceptor by lazy{
        val httpLoggingInterceptor1 = HttpLoggingInterceptor()
        httpLoggingInterceptor1.apply {
            httpLoggingInterceptor1.level = HttpLoggingInterceptor.Level.BODY
        }
    }

    //http client
    private val httpClient:OkHttpClient by lazy{
        OkHttpClient.Builder()
            .connectTimeout(15,TimeUnit.SECONDS)
            .readTimeout(12,TimeUnit.SECONDS)
            .writeTimeout(15,TimeUnit.SECONDS)
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    //retrofit object
    private val retrofit: Retrofit by lazy{
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .build()
    }

    //service object
    val api: TimeSeriesApi by lazy{
        retrofit.create(TimeSeriesApi::class.java)
    }
}