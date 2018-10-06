package com.daire.openweather.networking

import com.daire.openweather.models.CurrentWeatherResponse
import com.daire.openweather.models.ForecastResponse
import com.daire.openweather.networking.OpenWeatherMapConstants.Companion.API_KEY
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiInterface{
    @GET("data/2.5/weather?&units=metric&appid=$API_KEY")
    fun getCityData(@Query("q")city: String): Call<CurrentWeatherResponse>

    @GET("data/2.5/forecast?&units=metric&appid=$API_KEY")
    fun getForecastData(@Query("q")city: String): Call<ForecastResponse>
}