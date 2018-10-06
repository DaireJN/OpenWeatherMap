package com.daire.openweather.models

import com.google.gson.annotations.SerializedName

data class CurrentWeatherResponse(@SerializedName("main") var mainDetails: Main,
                                  @SerializedName("wind") var windDetails: Wind,
                                  @SerializedName("sys") var sunInfo: Sys,
                                  @SerializedName("list") var forecast: List<ForecastDetail>
)